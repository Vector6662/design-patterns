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


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 下边是四种多线程的实现方式。
         * 第一种方式并不推荐，它不是一个很好的设计。现在的多线程实现思想中，应该将任务和线程解耦，以更好地利用线程池
         */
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
        * 方式二：传递Runnable。可以使用lambda表达式
        * 较为先进的设计，任务（即这里的Runnable）和线程(Thread)分离开来
        * */
        Runnable runnableTask = () -> System.out.println(Thread.currentThread().getName() + "========>正在执行");
        new Thread(runnableTask).start();


        // FIXME: 2021/10/20 思考一个问题，thread直接调用run不行吗，为啥要调用start？考虑好了可看答案：
        // https://snailclimb.gitee.io/javaguide/#/docs/java/multi-thread/Java%E5%B9%B6%E5%8F%91%E5%9F%BA%E7%A1%80%E5%B8%B8%E8%A7%81%E9%9D%A2%E8%AF%95%E9%A2%98%E6%80%BB%E7%BB%93?id=_10-%e4%b8%ba%e4%bb%80%e4%b9%88%e6%88%91%e4%bb%ac%e8%b0%83%e7%94%a8-start-%e6%96%b9%e6%b3%95%e6%97%b6%e4%bc%9a%e6%89%a7%e8%a1%8c-run-%e6%96%b9%e6%b3%95%ef%bc%8c%e4%b8%ba%e4%bb%80%e4%b9%88%e6%88%91%e4%bb%ac%e4%b8%8d%e8%83%bd%e7%9b%b4%e6%8e%a5%e8%b0%83%e7%94%a8-run-%e6%96%b9%e6%b3%95%ef%bc%9f


        /*
        * 方式三：FutureTask
        * 方式二的优化，同时适配两种类型的任务：Runnable和Callable
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
        Future<String> submit = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "========>正在执行");
            Thread.sleep(3 * 1000L);
            return "方式四返回结果";
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
