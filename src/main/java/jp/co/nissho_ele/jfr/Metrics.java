package jp.co.nissho_ele.jfr;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jdk.jfr.Configuration;
import jdk.jfr.consumer.RecordedFrame;
import jdk.jfr.consumer.RecordedStackTrace;
import jdk.jfr.consumer.RecordingStream;

@ApplicationScoped
public class Metrics {

    private RecordingStream recordingStream;

    private final MeterRegistry registry;

    Metrics(MeterRegistry registry) {
        this.registry = registry;
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

                // eventの処理時間合計をMicrometerのメトリクスとして出力する
                Timer timer = Timer.builder(name).description("a description of what this timer does")
                        .publishPercentiles(0.5, 0.95) // median and 95th percentile
                        .publishPercentileHistogram() // create percentile histogram
                        .minimumExpectedValue(Duration.ofMillis(1)) // 最低1ms
                        .maximumExpectedValue(Duration.ofSeconds(15000)) // 最悪の場合、15秒...
                        .tags("method", method).register(this.registry);
                timer.record(event.getDuration().toNanos(), TimeUnit.NANOSECONDS);
            });
            recordingStream.enable(DatabaseRWInvocationEvent.NAME);
            recordingStream.onEvent(DatabaseRWInvocationEvent.NAME, event -> {
                String method = event.getString("javaMethod");
                String name = method;
                System.out.println("#### Event triggered " + name + ":" + event.getDuration());

                RecordedStackTrace rst = event.getStackTrace();
                if (rst != null) {
                    // エラー発生時のログスタックも出力が可能
                    List<RecordedFrame> frames = rst.getFrames();
                    System.out.println("Number of frames: " + frames.size());
                    for (RecordedFrame rf : frames) {
                        rf.getMethod().getName();
                        rf.getLineNumber();
                    }
                }

                // eventの処理時間合計をMicrometerのメトリクスとして出力する
                Timer timer = Timer.builder(name).description("a description of what this timer does")
                        .publishPercentiles(0.5, 0.95) // median and 95th percentile
                        .publishPercentileHistogram() // create percentile histogram
                        .minimumExpectedValue(Duration.ofMillis(1)) // 最低1ms
                        .maximumExpectedValue(Duration.ofSeconds(15000)) // 最悪の場合、15秒...
                        .tags("method", method).register(this.registry);
                timer.record(event.getDuration().toNanos(), TimeUnit.NANOSECONDS);
            });

            recordingStream.startAsync();
        } catch (IOException |

                ParseException e) {
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