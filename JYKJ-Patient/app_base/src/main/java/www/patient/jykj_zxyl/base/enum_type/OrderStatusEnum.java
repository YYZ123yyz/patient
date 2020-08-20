package www.patient.jykj_zxyl.base.enum_type;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description:订单状态
 *
 * @author: qiuxinhai
 * @date: 2020-08-03 09:40
 * private  String signStatus;  //签约状态:10:已提交 20:已同意 30:签约完成 40:到期解约 50:续约 100:已拒绝 110:需修改 120:提前解约 130:付款中 140: 【患者】解约申请中  160: 【医生】解约申请中
 */
@Retention(RetentionPolicy.SOURCE)

@StringDef({OrderStatusEnum.orderSubmitCode, OrderStatusEnum.orderAgreeCode
        , OrderStatusEnum.orderSignCompleteCode,
        OrderStatusEnum.orderExpireCancelContractCode,
        OrderStatusEnum.orderRenewContractCode,
        OrderStatusEnum.orderRejectedCode,
        OrderStatusEnum.orderNeedUpdateCode,
        OrderStatusEnum.orderAdvenceCancelContractCode,
        OrderStatusEnum.orderPayMentCode,
        OrderStatusEnum.orderPatientCancelContractApplyCode,
        OrderStatusEnum.orderDoctorCancelContractCode,
        OrderStatusEnum.orderRefundingCode,OrderStatusEnum.orderRefundFailtureCode})
public @interface OrderStatusEnum {
    /**
     * 已提交
     */
    String orderSubmitCode = "10";
    /**
     * 已同意
     */
    String orderAgreeCode = "20";
    /**
     * 签约完成
     */
    String orderSignCompleteCode = "30";
    /**
     * 到期解约
     */
    String orderExpireCancelContractCode = "40";
    /**
     * 续约
     */
    String orderRenewContractCode = "50";
    /**
     * 已拒绝
     */
    String orderRejectedCode = "100";
    /**
     * 需修改
     */
    String orderNeedUpdateCode = "110";
    /**
     * 提前解约
     */
    String orderAdvenceCancelContractCode = "120";
    /**
     * 付款中
     */
    String orderPayMentCode = "130";
    /**
     * 患者解约申请中
     */
    String orderPatientCancelContractApplyCode = "140";
    /**
     * 医生解约申请中
     */
    String orderDoctorCancelContractCode = "150";

    /**
     * 退款中
     */
    String orderRefundingCode="160";
    /**
     * 退款失败
     */
    String orderRefundFailtureCode="170";


}
