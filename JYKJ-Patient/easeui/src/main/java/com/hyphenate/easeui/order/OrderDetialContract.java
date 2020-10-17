package com.hyphenate.easeui.order;

import android.app.Activity;

import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.PaymentBean;
import www.patient.jykj_zxyl.base.base_bean.UpdateOrderResultBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Description:订单详情契约类
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:23
 */
public class OrderDetialContract {

    public interface View extends BaseView {

        /**
         * 获取订单详情返回结果
         * @param orderDetialBean 订单详情返回实体类
         */
        void getSearchOrderDetialResult(OrderDetialBean orderDetialBean);

        /**
         * 操作订单是否成功
         * @param isSucess true or false
         */
        void getOrderOperResult(boolean isSucess, String type);


        void UpdataSucess(UpdateOrderResultBean data);

        /**
         * 获取支付信息
         * @param paymentBean 支付信息
         */
        void getWeixPaymentResult(PaymentBean paymentBean);

        /**
         * 获取阿里支付返回结果
         * @param payInfo 支付信息
         */
        void getAliPaymentResult(String payInfo);


        /**
         * 获取支付信息失败
         * @param errorMsg 失败信息
         */
        void getPaymentResultError(String errorMsg);

        /**
         * 获取用户信息返回结果
         * @param userInfoBaseBean 用户信息
         */
        void getUserInfoResult(UserInfoBaseBean userInfoBaseBean);


    }

    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送查询订单详情请求
         * @param signOrderCode 订单Id
         * @param operDoctorCode 医生code
         * @param operDoctorName 医生名称
         */
        void sendSearchOrderDetialRequest(String signOrderCode, String operDoctorCode, String operDoctorName);

        /**
         * 订单操作接口
         * @param mainDoctorCode 医生编码
         * @param mainDoctorName 医生姓名
         * @param signCode 签约订单编码
         * @param signNo 签约订单编号
         * @param mainPatientCode 患者编码
         * @param mainUserName 患者名称
         * @param confimresult 	操作： 0拒绝，1同意，2需修改
         */
        void sendOrderOperRequest(String mainDoctorCode, String mainDoctorName
                , String signCode, String signNo, String mainPatientCode
                , String mainUserName, String confimresult, Activity activity);

        /**
         * 发送订单支付请求
         * @param orderCode 订单code
         * @param flagPayType 支付类型
         * @param activity activity
         */
        void sendOperPatientOrderPayRequest(String orderCode, String flagPayType, Activity activity);

        /**
         * 获取医生患者信息
         * @param userCodeList 医生或者患者Id列表
         */
        void sendGetUserListRequest(String userCodeList);

    }
}
