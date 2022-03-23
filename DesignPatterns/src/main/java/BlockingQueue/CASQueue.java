package BlockingQueue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 尝试无锁的阻塞队列，即自旋锁
 * 参考：http://blog.fnil.net/blog/9e79a8ed3855334b76d924f796be47be/
 * @param <T>
 */
public class CASQueue<T>{
    AtomicInteger state = new AtomicInteger(0);
    LinkedList<T> queue = new LinkedList<>();

    void lock(){
        while(!state.compareAndSet(0,1)){
            while(state.get()==1);//这里是个很巧妙的优化，1表示锁正在被占用，这一行代码只会访问共享副本，这样就避免了总线竞争
        }
    }
    void unlock(){
        state.set(0);
    }

    public void put(T t){
        lock();
        queue.addFirst(t);
        unlock();
    }
    public T take(){
        try {
            lock();
            return queue.removeLast();
        }finally {
            unlock();
        }
    }


}
