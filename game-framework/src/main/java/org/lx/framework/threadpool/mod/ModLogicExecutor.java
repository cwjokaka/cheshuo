package org.lx.framework.threadpool.mod;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.lx.framework.reflect.MethodInfo;
import org.lx.framework.threadpool.*;
import org.lx.framework.util.ChannelUtil;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 根据SessionId与线程池大小进行取模
 */
@Component
public class ModLogicExecutor extends AbsLogicExecutor {

    private static final int SYS_CORE = Runtime.getRuntime().availableProcessors();

    private ExecutorService[] pool = new ExecutorService[SYS_CORE];

    public ModLogicExecutor() {
        for (int i=0; i<pool.length; i++) {
            pool[i] = Executors.newSingleThreadExecutor(new DefaultThreadFactory("ModLogicExecutor-" + i));
        }
    }

    @Override
    public void doExec(AbsTask task) {
        if (!(task instanceof CmdTask)) {
            return;
        }
        CmdTask cmdTask = (CmdTask) task;
        int index = (int)(cmdTask.getSessionId() % SYS_CORE);
        pool[index].execute(task::run);
    }

}
