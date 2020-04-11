package org.lx.cs.hall.task;

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

    private ExecutorService pool = new ThreadPoolExecutor(4,
            32,
            60 * 1000,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(16));

    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }

    public void shutdown() {
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }

}
