package org.lx.framework.threadpool;

import java.util.concurrent.Executor;

/**
 * 业务线程池
 */
public interface LogicExecutor {

    void exec(AbsTask task);

}
