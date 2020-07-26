package org.lx.framework.threadpool;

public abstract class AbsLogicExecutor implements LogicExecutor {

    @Override
    public void exec(AbsTask task) {
        doExec(task);
    }

    public abstract void doExec(AbsTask task);

}
