package guohao.learn.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;

import java.time.Duration;

/**
 * @author guohao
 * @since 2023/12/3
 */
public class A4_SinkTest {

    @Test
    public void test() {
        Sinks.Many<Integer> replaySink = Sinks.many()
                .replay()
                .all();
        //thread1
        replaySink.emitNext(1, EmitFailureHandler.FAIL_FAST);
        //thread2, later
        replaySink.emitNext(2, EmitFailureHandler.FAIL_FAST);

        // thread3, concurrently with thread 2
        // would retry emitting for 2 seconds and fail with EmissionException if unsuccessful
        replaySink.emitNext(3, EmitFailureHandler.busyLooping(Duration.ofSeconds(2)));

        //thread3, concurrently with thread 2
        // would return FAIL_NON_SERIALIZED
        Sinks.EmitResult result = replaySink.tryEmitNext(4);

    }

}
