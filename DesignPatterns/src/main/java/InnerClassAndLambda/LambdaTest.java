package InnerClassAndLambda;


import java.util.concurrent.TimeUnit;

public class LambdaTest {

    public void testInnnerClass() throws InterruptedException {
        // 在匿名内部类的外面定义一个String变量
        String str = "hello";
        // 构造一个匿名内部类对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("str = " + str);
                System.out.println("this = " + this);//这里的this不会是LambdaTest，而是LambdaTest$1
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
    }

    public void testLambda() throws InterruptedException {
        String str = "hello";
        new Thread(() -> {
            System.out.println("str = " + str);
            System.out.println("this = " + this);
        }).start();

        TimeUnit.SECONDS.sleep(1);
    }
}