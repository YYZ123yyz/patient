package com.hyphenate.easeui.enumtype;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description:订单状态枚举值
 *
 * @author: qiuxinhai
 * @date: 2020-07-29 17:39
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({OrderStatus.ORDER_AGREE_CODE,
        OrderStatus.ORDER_UPDATE_CODE,
        OrderStatus.ORDER_REFUSE_CODE})
public @interface OrderStatus {
    /**
     * 同意
     */
    int ORDER_AGREE_CODE=100;
    /**
     * 修改
     */
    int ORDER_UPDATE_CODE=101;
    /**
     * 拒绝
     */
    int ORDER_REFUSE_CODE=102;

}
