package org.lx.framework.threadpool.mod;

import org.lx.framework.reflect.MethodInfo;
import org.lx.framework.threadpool.AbsLogicExecutor;
import org.lx.framework.threadpool.AbsTask;
import org.lx.framework.threadpool.LogicExecutor;
import org.lx.framework.threadpool.Task;
import org.lx.framework.util.ChannelUtil;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ModLogicExecutor extends AbsLogicExecutor {

    private static final int SYS_CORE = Runtime.getRuntime().availableProcessors();

    private ExecutorService[] pool = new ExecutorService[SYS_CORE];

    public ModLogicExecutor() {
        for (int i=0; i<pool.length; i++) {
            pool[i] = Executors.newSingleThreadExecutor();
        }
    }

    @Override
    public void doExec(AbsTask task) {
        if (!(task instanceof ModTask)) {
            return;
        }
        ModTask modTask = (ModTask) task;
        int index = (int)(modTask.getSessionId() % SYS_CORE);
        //            Object resp = task.getMethod().invoke(ta.getHandler(), params);
        //            ChannelUtil.writeAndFlush(modTask.getSession().getChannel(), resp);
        pool[index].execute(task::run);

    }

//    @Override
//    public void exec(AbsTask task, Object[] params) {
//    }



}
