package MutiThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 学习多线程
 * 参考的系列文章：https://www.yuque.com/books/share/2b434c74-ed3a-470e-b148-b4c94ba14535/vq2ti4#kDgxl
 *
 * 记录一个重要的发现：Thread的构造函数中是没有Callable参数的，只有Runnable，这意味着要执行Callable任务就不得不使用适配器模式
 * 同时，Runnable和Callable都是FunctionalInterface哦，可以用lambda表达式
 * 紧接着，我发现了两者的终极适配器：FutureTask，本质上该类和Runnable、Callable地位一样，都是任务类，但是它可以接受Runnable和Callable作为参数。
 *
 * 《多线程基础》这篇文章中提到了FutureTask的继承体系请注意学习并记忆（很不幸的是这里不能粘贴图片）
 *
 * FutureTask应该是一个划时代的类，将结果（Furture）和任务（Task）封装到一个类中
 */
public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * 三种多线程的实现方式
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /*
        * 方式一：直接重写Thread的run方法。
        * 看Thread的源码可以发现，run()方法体中有个target.run()，你懂我意思吧
        * */
        new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "========>正在执行");
            }
        }.start();

        /*
        * 方式二：传递Runnable。同时注意这是一个函数，可以使用lambda表达式
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "========>正在执行");
            }
        }).start();


        /*
        * 方式三：FutureTask
        * 这种方式其实是方式二的变体，因为FutureTask实现了Runnable接口
        * */
        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "========>正在执行");
                try {
                    Thread.sleep(3*1000L);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                return "success";
            }
        });
        System.out.println(Thread.currentThread().getName() + "========>正在执行");
        new Thread(task).start();
        String result = task.get();
        System.out.println("任务执行结果，result======>" + result);

        /*
        * 方式四：线程池
        * */
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> submit = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "========>正在执行");
                Thread.sleep(3 * 1000L);
                return "方式四返回结果";
            }
        });
        String result1 = submit.get();
        System.out.println("result=======>" + result1);
        // 关闭线程池
        executorService.shutdown();




        /*
        * 自己实现了一个简易版FutureTask，但没有返回值和异步
        * */
        MyFutureTask<String> f = new MyFutureTask<>(new Runnable() {
            @Override
            public void run() {
                System.out.println("NB!!!");
            }
        },"success");
        new Thread(f).start();

    }

}
