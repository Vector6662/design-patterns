package BlockingQueue;


import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// TODO: 2022/3/28 如何用数组来实现一个队列？
public class ArrayBlockingQueue {
    private final Queue<Object> queue;
    private final ReentrantLock lock=new ReentrantLock();
    private final Condition producerCondition;
    private final Condition consumerCondition;
    private int capacity;

    public ArrayBlockingQueue(int capacity){
        this.producerCondition = lock.newCondition();
        this.consumerCondition =lock.newCondition();
        queue=new ArrayDeque<>(capacity);
        this.capacity =capacity;
    }



    public boolean put(Object t,long timeout){
        try{
            if (!lock.tryLock(timeout,TimeUnit.MILLISECONDS)) return false;

            while(capacity == queue.size()){
                producerCondition.await();
            }
            queue.add(t);
            consumerCondition.signal();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            lock.unlock();
        }
        return true;
    }

    public Object take(long timeout){
        Object o=null;
        try {
            if(!lock.tryLock(timeout,TimeUnit.MILLISECONDS)) return false;

            while(capacity ==0){
                consumerCondition.await();
            }
            o=queue.remove();
            producerCondition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return o;
    }

}
