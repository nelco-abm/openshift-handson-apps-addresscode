package jp.co.nissho_ele.jfr;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
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
        recordingStream = new RecordingStream();
        recordingStream.enable(JaxRsInvocationEvent.NAME);

        recordingStream.onEvent(JaxRsInvocationEvent.NAME, event -> {

            String path = event.getString("path").replaceAll("(\\/)([0-9]+)(\\/?)", "$1{param}$3");
            String method = event.getString("method");
            String name = path + "-" + method;

            System.out.println("#### Event triggered " + name + event.getDuration());

            // 名前 name
            // 時間 event.getDuration().toNanos()
            // 説明 "Metrics for " + path + " (" + method + ")"
            AtomicReference<Long> obj = new AtomicReference<>(event.getDuration().toNanos());
            registry.more().timeGauge(name, Tags.of("method", method), obj, TimeUnit.NANOSECONDS, AtomicReference::get);
        });
        recordingStream.startAsync();
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