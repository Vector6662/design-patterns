package FlyweightPattern;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 * 注意哦，这是一种非常使用的工厂类的实现方式，实例化产品的方法是静态的！
 */
public class ActivityFactory {
    static Map<Long, Activity> activityMap = new HashMap<>();
    public static Activity getActivity(Long id) {
        Activity activity = activityMap.get(id);
        if (activity==null){
            activity = new Activity();
            activity.setId(1000L);
            activity.setName("图书嗨乐");
            activity.setDesc("图书优惠券分享激励第二期");
            activity.setStartTime(new Date());
            activity.setStopTime(new Date());
            activityMap.put(id,activity);
        }
        return activity;
    }
}
