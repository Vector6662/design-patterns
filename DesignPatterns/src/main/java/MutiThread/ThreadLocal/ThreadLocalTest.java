package MutiThread.ThreadLocal;


/**
 * 参考文章：https://www.yuque.com/books/share/2b434c74-ed3a-470e-b148-b4c94ba14535/izbsgk
 *
 * ThreadLocal,ThreadLocalMap,Thread三者之间的关系很容易搞混，
 * 实际上每个Thread对象（即每个线程），其内部都会维护一个ThreadLocalMap实例，用来存储这个线程的所有专属数据，
 * 这个map的Key是，且只能是ThreadLocal对象，而value是泛型。
 *
 * 是的，你没看错，ThreadLocal对象本身其实是不存数据的，真正存数据的是ThreadLocalMap。这是让我很惊叹的地方，
 * 一个ThreadLocal对象安静地在堆区中，没有任何线程改变它，但是所有线程都能访问它（堆区嘛，线程共有），但是它在不同线程眼里是不一样的。
 * 就像一位女性W，在A那里是妈妈，B那里是女儿，C那里是老婆，而W本身是没有任何变化的，她还是那个她
 *
 *
 * ThreadLocal的get()的过程：拿到当前的线程t-->拿到t的ThreadLocalMap对象（单例）-->这是一个标准的Map，直接采用get(this)来拿到想要的值
 * set()的过程也是异曲同工，最核心的地方都是要拿到当前线程的ThreadLocalMap
 *
 * Q1：为什么将ThreadLocalMap的Entry对key是弱引用？
 * BTW，注意我的表述，不是说对Entry是弱引用哦！而是Entry对其key是弱引用，可以看看源码
 * 目前对Q1的思考：一旦外界没有引用这个对象的变量，就表明这个对象在程序所实现的业务层面失去了价值，没有任何一个线程正在使用它(强引用)，此时就可以被GC线程回收了。
 *
 * Q2：对Entry实现弱引用能完全解决内存泄漏问题吗？
 * 这只是个治标不治本的方式，因为ThreadLocalMap对Entry的引用并不是弱引用啊！而是实打实的强引用！当GC线程回收了Entry的key，这个Entry就成了无头野鬼。
 * ThreadLocal的设计者其实也想到了这个问题：下一次使用get()或set()时把上一次遗留的key为null的value清除。但这样还不是最保险的，具体为什么看原文。
 * 文章说到最后建议手动清楚ThreadLocal对象，这还是很不智能的。
 * 如果非要谁背锅的话，我觉得应该是GC，它不够智能（\狗头）！它不会回收key为Null的value！
 *
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
