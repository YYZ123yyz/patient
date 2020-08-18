package www.patient.jykj_zxyl.activity.myself.order;

import android.app.Activity;

import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Description:退款申请契约类
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:23
 */
public class RefundApplyContract {

    public interface View extends BaseView {
        /**
         * 发送退款申请文字部分返回结果
         * @param isSucess ture or false
         * @param msg 成功失败信息
         */
        void getOperPatientMyOrderResFundResult(boolean isSucess, String msg);

    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送退款申请文字部分
         *
         * @param orderCode      订单Id
         * @param imgCode        图片编码
         * @param refundTypeCode 退款原因编码
         * @param refundTypeName 退款原因名称
         * @param refundReason   申请退款说明
         * @param activity  Activity
         */
        void sendOperPatientMyOrderResRefundRequest(String orderCode,
                                                    String imgCode,
                                                    String refundTypeCode,
                                                    String refundTypeName,
                                                    String refundReason,
                                                    Activity activity);

    }
}
