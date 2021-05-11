package MutiThread;

import AdaptorPattern.RunnableAdaptor;
import java.util.concurrent.Callable;

/**
 * 感觉这个很牛逼啊，同时适配了Runnable和Callable。
 * 具体思路是
 * 1.一定要实现Runnable接口（这是最关键的，否则不可能成为Thread的执行类）；
 * 2.两个构造器，分别接收Runnable和Callable实参；
 * 3.将这两个实参都转换为该类的Callable私有属性
 * @param <T>
 */
public class MyFutureTask<T> implements Runnable{
    private Callable<T> callable;
    public MyFutureTask(Runnable task,T result){
        this.callable= new RunnableAdaptor<>(task, result);//这段代码很精髓了，将Runnable也转换为Callable

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
