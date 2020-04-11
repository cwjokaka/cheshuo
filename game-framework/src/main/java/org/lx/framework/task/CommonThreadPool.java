package org.lx.framework.task;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 公共线程池
 */
@Component
public class CommonThreadPool {

    private ExecutorService pool = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            32,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(48));

    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }

    public void shutdown() {
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }

}
