package BlockingQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 尝试无锁的阻塞队列
 * @param <T>
 */
public class CASQueue<T>{
    AtomicInteger capacity = new AtomicInteger(0);
    LinkedList<T> queue = new LinkedList<>();

    public void put(T t){
        while(!capacity.compareAndSet(capacity.get(), capacity.get()+1));
        queue.addFirst(t);
    }
    public T take(){
        while (!capacity.compareAndSet(capacity.get(), capacity.get()-1));
        return queue.removeLast();
    }


}
