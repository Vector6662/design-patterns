package MutiThread;

import AdaptorPattern.RunnableAdaptor;
import java.util.concurrent.Callable;

/**
 * 感觉这个很牛逼啊，同时适配了Runnable和Callable
 * @param <T>
 */
public class MyFutureTask<T> implements Runnable{
    private Callable<T> callable;
    public MyFutureTask(Runnable task,T result){
        this.callable= new RunnableAdaptor<T>(task,result);//这段代码很精髓了，将Runnable也转换为Callable

    }
    public MyFutureTask(Callable<T> task){
        this.callable=task;
    }
    @Override
    public void run() {
        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
