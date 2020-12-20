package StrategyPattern;

import java.math.BigDecimal;

public interface ICouponDiscount<T> {
    /**
     * 优惠券金额计算
     * @param couponInfo
     * @param skuPrice
     * @return
     */
    BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice);
}
