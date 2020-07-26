package org.lx.framework.threadpool;

import org.lx.framework.reflect.MethodInfo;

public abstract class AbsLogicExecutor implements LogicExecutor {

    @Override
    public void exec(AbsTask task) {
        doExec(task);
    }

    public abstract void doExec(AbsTask task);

}
