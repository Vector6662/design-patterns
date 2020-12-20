package FlyweightPattern;

import FlyweightPattern.util.RedisUtils;

public class ActivityController {
    private RedisUtils redisUtils = new RedisUtils();

    public Activity queryActivityInfo(Long id){
        Activity activity = ActivityFactory.getActivity(id);
        //模拟从Redis中获取库存变化的信息
        Stock stock = new Stock(1000,redisUtils.getStockUsed());
        activity.setStock(stock);
        return activity;
    }
}
