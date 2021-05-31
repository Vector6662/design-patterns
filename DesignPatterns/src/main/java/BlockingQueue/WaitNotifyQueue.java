package BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * 参考文章：https://www.yuque.com/books/share/2b434c74-ed3a-470e-b148-b4c94ba14535/gertlc#g6CMS
 */
public class WaitNotifyQueue<T> {
    private static final Logger logger = LoggerFactory.getLogger(WaitNotifyQueue.class);
    private LinkedList<T> queue = new LinkedList<>();
    private int capacity;

    public synchronized void setCapacity(int capacity){
        if (capacity<=0)
             throw new IllegalArgumentException("容量最小值为0");
        this.capacity = capacity;
    }

    public synchronized void put(T resource) throws InterruptedException {
        // 注意一个关键点：此时生产者线程是拿到了对象的锁的！拿到锁之后才有资格执行这个while循环，然后试探能否插入数据到队列中
        while (queue.size()>= capacity) {
            logger.warn("队列已满，生产者不能再放入资源");
            // 这里应该是阻塞整个生产者队列了，释放了CPU使用权，线程挂起。
            // 但是要注意的是当它再次被唤醒它不会从头开始执行，而是接着这里，也就是挂起的地方继续执行！！！
            // 这就是为什么要用while循环的原因了！还是那个如果这个线程接着此处继续执行，还是需要判断一下队列是否装满，如果仍然装满又需要再次挂起。
            this.wait();//队列已满，将当前线程休眠，重新回到阻塞队列中LockSupport.park(this);效果一样
        }
        logger.info("生产者插入：{}",resource);
        queue.addFirst(resource);
        // 唤醒所有线程。但是请注意，阻塞队列中有两种线程：生产者和消费者线程。如果采用的是notify()，那么只会随机唤醒一个线程，这个线程极有可能又
        // 是生产者线程，然后看到队列满了，再次休眠，再次随机唤醒一个线程...然后又有可能是生产者线程😅。这就可能造成一种”软死锁“（自己创造的名词），
        // 即一直唤醒生产者线程。这种概率还是有的哦！所以这里采用的是唤醒所有的线程，让他们竞争锁资源。但是感觉还是会造成notify()那种情况...
        this.notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (queue.size()<=0){
            logger.warn("队列空了，消费者不能再取资源");
            this.wait();
        }
        T resource = queue.removeLast();
        logger.info("消费者得到：{}",resource);
        this.notifyAll();
        return resource;
    }
}
