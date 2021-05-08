package MutiThread;

/**
 * ThreadLocal,ThreadLocalMap,Thread三者之间的关系很容易搞混，
 * 实际上每个Thread对象，也就是每个线程内部都会维护一个ThreadLocalMap实例，用来存储这个线程的所有专属数据，
 * 这个map的Key是，且只能是ThreadLocal对象，而value是泛型。
 *
 * 我觉得ThreadLocal最神奇的地方在于实现了线程之间数据的隔离，也就是说**同一个**ThreadLocal变量，
 * 但在不同的线程中使用.get()，的得到的结果是不一样的。这也很好的实现了单例模式！
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
        threadLocal1.set("123");

        System.out.println("线程"+Thread.currentThread().getName()+"=====>"+threadLocal1.get());
        //下面这种方式这个新的线程应该会查找自己的ThreadLocalMap，看看有没有threadlocal1实例，如果没有就创建这个实例，然后返回null（因为这个时候该实例是没有赋值的）
        new Thread(()-> System.out.println("线程"+Thread.currentThread().getName()+"=====>"+threadLocal1.get())).start();
    }
}
