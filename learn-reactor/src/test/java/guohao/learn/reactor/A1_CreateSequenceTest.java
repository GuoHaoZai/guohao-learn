package guohao.learn.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author guohao
 * @since 2023/12/2
 *
 * @see <a href="https://projectreactor.io/docs/core/release/reference/index.html#which.create">create</a>
 */
public class A1_CreateSequenceTest {

    /**
     * <li>{@link Flux#generate(Consumer)}方法一次只能生成一个对象 </li>
     * <li>{@link Flux#create(Consumer)}方法一次可生成多个对象 </li>
     * @see <a href="https://skyao.io/learning-reactor/docs/concept/flux/create.html">generate VS create </a>
     */
    @Test
    public void testCreateFlux() {
        // 1. `just`枚举所有元素
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        // 2. `from` 集合
        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);
        // 3. 对已有的进行包装
        Flux<String> seq3 = Flux.from(Flux.just("foo"));
        // 4.
        Flux<Integer> range = Flux.range(1, 3);

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(5));
        Flux.error(new RuntimeException());
    }

    /**
     * 创建分为`懒汉型`和`饿汉型（lazy）`
     * <li>懒汉型：每次调用{@link Mono#subscribe()}方法时，都新生成一个对象</li>
     * <li>饿汉型：每次调用{@link Mono#subscribe()}方法时，返回相同的对象</li>
     *
     * @see <a href="https://blog.csdn.net/john1337/article/details/104205774">defer vs just</a>
     */
    @Test
    public void testCreateMono() {
        // 饿汉型
        Mono.empty();
        Mono.just("foo");
        Mono.justOrEmpty(null);
        Mono.justOrEmpty(Optional.empty());
        // from系列方法...
        Mono.from(Mono.empty());

        // 懒汉型
        Mono.defer(() -> Mono.empty());
        Mono.fromSupplier(() -> "foo");
    }

}
