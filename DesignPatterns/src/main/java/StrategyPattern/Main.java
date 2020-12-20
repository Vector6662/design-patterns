package StrategyPattern;

import StrategyPattern.impl.ZJCouponDiscount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * 策略模式
 * 和visitor模式的区别在于{@link Context}类中有回调，但是被回调的类是以类属性存在的，
 * 而visitor模式的被回调类是以方法属性的方式存在的
 *
 * 我目前发现，访问者模式、策略模式、观察者模式基本都是回调机制的应用！
 *
 * 意外收获：BigDecimal这个类挺好用的！
 */
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Context<Double> context = new Context<>(new ZJCouponDiscount());
        BigDecimal discountAmount = context.discountAmount(10D,new BigDecimal(100));
        logger.info("测试结果：直接优惠后金额{}",discountAmount);

    }
}
