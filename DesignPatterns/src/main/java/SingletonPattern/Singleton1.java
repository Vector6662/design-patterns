package SingletonPattern;

/**
 * 双重检查的单例模式
 * 参考：https://www.cnblogs.com/damonhuang/p/5431866.html
 */
public class Singleton1 {
    //关键之一，用volatile修饰INSTANCE属性，保证线程可见性的同时防止重排序
    private volatile static Singleton1 INSTANCE;
    private Singleton1(){

    }
    public static Singleton1 getInstance(){
        /*
        * 为什么synchronized不加在这个方法的签名上？主要是性能的考量，如果synchronized加载方法签名上，
        * 那么每次要获得这个单例都得抢synchronized锁，即使INSTANCE不为null，可以直接拿到实例的情况下都得抢锁，
        * 这样严重影响了并发性能。因此，最好的方式是单例为null的时候才需要抢锁，不如果不为空就直接返回该单例即可。
        * */
        if (INSTANCE == null){
            /*
            * 即使加了synchronized，还是有并发风险。譬如，thread1和thread2都通过判断INSTANCE为空来到了这里，不用说此时需要抢锁。
            * 假如thread1抢到了锁，初始化Singleton对象(new...)、INSTANCE对象引用它，并刷入主内存中，此时INSTANCE就不为空了。
            * 接下来，因为操作执行完毕，thread1需要释放锁，此时加入thread2抢到了锁，由于Java内存模型，
            * thread2的本地内存中有INSTANCE的拷贝，此时的INSTANCE为null。如果INSTANCE变量没有volatile修饰，
            * 那下面这个`if (INSTANCE == null)`中访问INSTANCE变量的语句，在thread2执行的时候只会查询自己本地内存中的INSTANCE的情况！！！
            * 而不会关心主内存中INSTANCE的情况。那此时if (INSTANCE == null)的判断结果就为true了！于是INSTANCE又被实例化了一次。
            * 这就是为什么需要用volatile修饰INSTANCE，这样thread2`if (INSTANCE == null)`访问INSTANCE时就会刷新一下本地内存中的INSTANCE，
            * 这时INSTANCE明显不为空了，于是就不再次实例化。
            * （上面说的这种情况可以删除INSTANCE申明上的volatile后测试，但是很难模拟出这种情况）
            * */
            synchronized (Singleton1.class){
                if (INSTANCE == null){
                    INSTANCE = new Singleton1();
                }
            }
        }
        return INSTANCE;
    }
}
