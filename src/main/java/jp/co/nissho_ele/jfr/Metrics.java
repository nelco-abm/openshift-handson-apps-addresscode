package jp.co.nissho_ele.jfr;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jdk.jfr.Configuration;
import jdk.jfr.FlightRecorder;
import jdk.jfr.consumer.RecordingStream;

@ApplicationScoped
public class Metrics {

    private RecordingStream recordingStream;

    private final MeterRegistry registry;

    Metrics(MeterRegistry registry) {
        this.registry = registry;
    }

    public void registerEvent(@Observes StartupEvent se) {
        FlightRecorder.register(JaxRsInvocationEvent.class);
        System.out.println("#### Registered");
    }

    public void onStartup(@Observes StartupEvent se) {
        try {
            Configuration c = Configuration.getConfiguration("default");

            recordingStream = new RecordingStream(c);
            recordingStream.enable(JaxRsInvocationEvent.NAME);
            recordingStream.onEvent(JaxRsInvocationEvent.NAME, event -> {

                String path = event.getString("path").replaceAll("(\\/)([0-9]+)(\\/?)", "$1{param}$3");
                String method = event.getString("method");
                String name = path + "-" + method;

                System.out.println("#### Event triggered " + name + ":" + event.getDuration());

                // eventの処理時間合計をtimeGaugeとして出力する
                AtomicReference<Long> obj = new AtomicReference<>(event.getDuration().toNanos());
                registry.more().timeGauge(name, Tags.of("method", method), obj, TimeUnit.NANOSECONDS,
                        AtomicReference::get);
            });
            recordingStream.enable(DatabaseRWInvocationEvent.NAME);
            recordingStream.onEvent(DatabaseRWInvocationEvent.NAME, event -> {
                String method = event.getString("javaMethod");
                String name = method;
                System.out.println("#### Event triggered " + name + ":" + event.getDuration());

                // eventの処理時間合計をtimeGaugeとして出力する
                AtomicReference<Long> obj = new AtomicReference<>(event.getDuration().toNanos());
                registry.more().timeGauge(name, Tags.of("method", method), obj, TimeUnit.NANOSECONDS,
                        AtomicReference::get);
            });
            recordingStream.startAsync();
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stop(@Observes ShutdownEvent se) {
        recordingStream.close();
        try {
            recordingStream.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}