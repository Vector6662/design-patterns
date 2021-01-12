package FlyweightPattern;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 享元模式
 * 引用廖雪峰老师的定义：运用共享技术有效地支持大量细粒度的对象。
 *
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static ActivityController activityController = new ActivityController();

    public static void main(String[] args) throws InterruptedException {
        List list;
        for (int idx=0;idx<10;idx++){
            Long req = 10001L;
            Activity activity = activityController.queryActivityInfo(req);
            logger.info("测试结果：{} {}",req, JSON.toJSONString(activity));
            Thread.sleep(1200);
        }
    }
}
