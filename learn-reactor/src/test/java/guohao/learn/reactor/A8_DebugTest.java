package guohao.learn.reactor;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.tools.agent.ReactorDebugAgent;

/**
 * @author guohao
 * @since 2023/12/2
 * @see <a href="https://projectreactor.io/docs/core/release/reference/index.html#reactor-tools-debug">reactor debug</a>
 */
public class A8_DebugTest {
    public static Logger logger =  LoggerFactory.getLogger(Main.class);

    {
        ReactorDebugAgent.init();
        ReactorDebugAgent.processExistingClasses();
    }

    @Test
    public void test2() throws InterruptedException {
        Flux.just(1, 2, 0)
                .single()
                .map((i)-> 10/i)
                .subscribeWith(new Subscriber<>(){
                    Subscription subscription;
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                        this.subscription = s;
                    }

                    @Override
                    public void onNext(Integer string) {
                        System.out.println("onNext: " + string);
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logger.error("onError",t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }
}
