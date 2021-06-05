package StrategyPattern;

import StrategyPattern.impl.MJCouponDiscount;
import StrategyPattern.impl.ZJCouponDiscount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

/**
 * 策略模式
 * 和visitor模式的区别在于策略模式中的{@link Context}类有回调，但是被回调的类是以**类属性**存在的，
 * 而visitor模式的被回调类是以**方法形参**的方式存在的
 *
 * 我目前发现，访问者模式、策略模式、观察者模式基本都是回调机制的应用！
 *
 * 意外收获：BigDecimal这个类挺好用的！
 *
 * 2021/6/1
 * 上面的收获今天看来持保留意见，但是暂时不删除，直到有更高级意识
 * 我复习策略模式的原因是因为看到了实现spring的AOP用到了策略模式，JDK动态代理和cglib代理根据不同的需求切换，但是这个例子我感觉更像静态代理😂😂😂
 */
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Context<Double> context = new Context<>();

        // 采用直减策略
        BigDecimal discountAmount = context.discountAmount(10D,new BigDecimal(100));
        logger.info("测试结果：直减优惠后金额{}",discountAmount);

        // 采用满减策略
        context.setStrategy(new MJCouponDiscount());
        discountAmount = context.discountAmount(100D,new BigDecimal(123));
        logger.info("测试结果：满减优惠后金额{}",discountAmount);

    }
}
