package StrategyPattern;

import java.math.BigDecimal;

/**
 * 所有具体策略均需要实现这个接口
 * @param <T>
 */
public interface ICouponDiscount<T> {
    /**
     * 优惠券金额计算
     * @param couponInfo
     * @param skuPrice
     * @return
     */
    BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice);
}
