package MutiThread.ThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal工具类
 * 每个线程只用一个ThreadLocal对象来存数据
 *
 * 这个工具类的核心就只有一个：THREAD_CONTEXT属性，作为所有线程私有数据的唯一“钩子”，搞垄断。它不变就行，反正不同的线程通过它拿到的value不一样
 */

public class ThreadLocalUtil {
    private ThreadLocalUtil(){}
    private final static ThreadLocal<Map<String,Object>> THREAD_CONTEXT = new ThreadLocal<>();

    public static void put(String key,Object value){
        Map<String,Object> map = THREAD_CONTEXT.get();
        if(map==null){
            map = new HashMap<>();
            THREAD_CONTEXT.set(map);
        }
        map.put(key,value);
    }
    public static Object get(String key){
        Map<String,Object> map = THREAD_CONTEXT.get();
        return map==null ? null:map.get(key);
    }
    public static void remove(String key){
        THREAD_CONTEXT.get().remove(key);
    }
    public static void clear(){
        THREAD_CONTEXT.remove();
    }

}
