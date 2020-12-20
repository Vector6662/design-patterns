package FlyweightPattern.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不是很懂，但这不是关键，选择性忽视一波
 */
public class RedisUtils {
    private ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(1);
    private AtomicInteger stock = new AtomicInteger(0);
    public RedisUtils(){
        scheduledExecutorService.scheduleAtFixedRate(()->{
            stock.addAndGet(1);
        },0, 100000, TimeUnit.MICROSECONDS);
    }
    public int getStockUsed(){
        return stock.get();
    }
}
