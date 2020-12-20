package StrategyPattern;

import java.math.BigDecimal;

/**
 * 策略控制类
 * @param <T>
 */
public class Context<T> {
    private ICouponDiscount<T> couponDiscount;
    public Context(ICouponDiscount<T> couponDiscount){
        this.couponDiscount = couponDiscount;
    }
    public BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice) {
        return couponDiscount.discountAmount(couponInfo,skuPrice);
    }

}
