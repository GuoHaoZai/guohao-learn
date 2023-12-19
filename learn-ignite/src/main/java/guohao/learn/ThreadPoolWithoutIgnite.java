package guohao.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolWithoutIgnite {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(16);

        long start = System.currentTimeMillis();

        for (int i = 1; i <= 50; i++) {
            service.submit(new MyRunnable(i));
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        System.out.printf("Time taken %dms.%n", (System.currentTimeMillis() - start));
    }
}