package org.lx.framework.task;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 公共线程池
 */
public class CommonThreadPool {

    private static final int SYS_CORE_NUM = Runtime.getRuntime().availableProcessors();

    private static final ExecutorService commonPool;

    static {
        commonPool = new ThreadPoolExecutor(
                SYS_CORE_NUM,
                SYS_CORE_NUM,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8192),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

    public static void execute(Runnable runnable) {
        commonPool.execute(runnable);
    }

}
