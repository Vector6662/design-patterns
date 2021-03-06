package StrategyPattern;

import StrategyPattern.impl.ZJCouponDiscount;

import java.math.BigDecimal;

/**
 * 策略控制类
 * @param <T>
 */
public class Context<T> {
    private ICouponDiscount couponDiscount = new ZJCouponDiscount();//默认策略为直减

    public void setStrategy(ICouponDiscount couponDiscount){
        this.couponDiscount = couponDiscount;
    }

    public BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice) {
        //其实可能实际使用策略模式的时候这里会有一些业务代码，比如处理输入数据，然后（甚至是最后）在调用（回调）策略进行输出
        return couponDiscount.discountAmount(couponInfo,skuPrice);
    }

}
