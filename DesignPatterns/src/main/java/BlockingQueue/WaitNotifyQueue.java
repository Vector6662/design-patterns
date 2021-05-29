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
    private int size;

    public synchronized void setSize(int size){
        this.size=size;
    }

    public synchronized void put(T resource) throws InterruptedException {
        // 注意一个关键点：此时生产者线程是拿到了对象的锁的！拿到锁之后才有资格执行这个while循环，然后试探能否插入数据到队列中
        while (queue.size()>=size) {
            logger.warn("队列已满，生产者不能再放入资源");
            this.wait();//队列已满，将当前线程休眠，重新回到阻塞队列中
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
