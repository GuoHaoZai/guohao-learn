package guohao.learn.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * @author guohao
 * @since 2023/12/2
 * @see <a href="https://it-blog-cn.com/blogs/spring/reactor.html#%E4%B8%80%E3%80%81reactor-%E6%A6%82%E5%BF%B5-pom">...</a>
 */
public class A2_TransformSequenceTest {

    @Test
    public void test1() {
        Flux.just(null, "foo", "bar")
                .map(String::trim)
                .index()
                .flatMap(tuple -> Flux.just(null, tuple.getT2()))
                .handle((i, sink) -> {
                    sink.next(i);
                });



    }

}
