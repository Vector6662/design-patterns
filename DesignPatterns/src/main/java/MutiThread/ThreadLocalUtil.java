package MutiThread;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal工具类
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
