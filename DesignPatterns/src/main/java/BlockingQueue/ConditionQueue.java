package BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionQueue<T> {
    private final static Logger logger = LoggerFactory.getLogger(ConditionQueue.class);

    private LinkedList<T> queue = new LinkedList<>();

    private ReentrantLock lock = new ReentrantLock();// 默认非公平锁
    private Condition consumerCondition = lock.newCondition();
    private Condition producerCondition = lock.newCondition();

    private int capacity;

    public ConditionQueue(int capacity){
        if (capacity<=0)
            throw new IllegalArgumentException("容量最小值为1");
        this.capacity = capacity;
    }

    /**
     * 生产者调用这个方法，因为这是个有状态的方法（方法内部会改变对象的私有属性，保有数据）
     * 所以生产者线程要先获取这个方法的锁（有状态方法都应该这么处理），然后再判断队列是否已满，如果满了就挂起
     *
     * 这是相比于WaitNotifyQueue更加优秀的阻塞队列实现方式，它将消费者和生产者线程分开为两个不同的阻塞队列，
     * 通过调用producerCondition.await()来阻塞整个生产者线程的阻塞队列，这个时候只有消费者线程阻塞队列被唤醒，
     * 于是达到的效果是：同一**时刻**，抢夺资源的线程只可能有一种类型，要么是生产者线程，要么是消费者线程！
     * @param resource
     */
    public void put(T resource) throws InterruptedException {
        //相较于WaitNotifyQueue中方法签名上的synchronized，这里是显式锁
        lock.lock();
        try {
            while (queue.size()>=capacity){
                logger.warn("队列已满，生产者不能再放入资源");
                // 将当前线程挂起在生产者队列中，并释放CPU使用权。但是要注意的是当它再次被唤醒它不会从头开始执行，而是接着这里，也就是挂起的地方继续执行！！！
                // 这就是为什么要用while循环的原因了！如果这个线程接着此处继续执行，还是需要判断一下队列是否装满，如果仍然装满又需要再次挂起。
                // 翻译翻译这个await()的文档：Causes the current thread to wait until it is signalled or interrupted. 让当前线程等待，直到被signal或interrupted。
                // 其实就是将当前线程（关键词：当前）注册到这个condition中，这样之后就可以实现选择性地signal一个condition中的线程。
                // 如果和synchronized统一起来看，synchronized相当于只有一个condition的lock，所有的挂起线程都在一个condition里边。
                producerCondition.await();
            }
            logger.info("生产者插入:【{}】",resource);
            queue.addFirst(resource);
            // 生产完毕，唤醒消费者
            // 我考虑过将这个代码写在finally里头，因为觉得如果就按照现在的写法，唤醒一个消费者阻塞队列中的一个线程。
            // 但是finally中的是执行完这个方法后无论如何都要执行的东西，而signal()只是生产者插入资源后提醒消费者消费时才调用
            consumerCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size()<=0){
                logger.warn("队列没有资源，挂起消费者");
                consumerCondition.await();
            }
            T resource = queue.getFirst();
            logger.info("消费者获取:【{}】，唤醒生产者",resource);
            producerCondition.signal();
            return resource;
        } finally {
            lock.unlock();
        }
    }
}
