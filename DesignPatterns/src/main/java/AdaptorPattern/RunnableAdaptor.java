package AdaptorPattern;

import java.util.concurrent.Callable;

public class RunnableAdaptor<T> implements Callable<T> {
    private Runnable runnable;
    private T result;
    public RunnableAdaptor(Runnable task,T result){
        this.runnable=task;
        this.result=result;
    }
    @Override
    public T call() {
        /*
        * new Thread(target).start()会调用run()/call()方法
        * */
        runnable.run();
        return result;
    }
}
