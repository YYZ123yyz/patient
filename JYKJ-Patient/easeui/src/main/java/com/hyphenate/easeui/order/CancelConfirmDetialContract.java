package com.hyphenate.easeui.order;

import android.app.Activity;

import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.SignOrderInfoBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Description:解约详情契约类
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:23
 */
public class CancelConfirmDetialContract {

    public interface View extends BaseView {
        /**
         * 获取订单详情返回结果
         *
         * @param orderDetialBean 订单详情返回实体类
         */
        void getSearchOrderDetialResult(OrderDetialBean orderDetialBean);

        /**
         * 获取解约确认返回结果
         *
         * @param isSucess true or false
         * @param msg      信息
         */
        void getCancelContractConfirmResult(boolean isSucess, String msg);

        /**
         * 获取用户信息返回结果
         *
         * @param userInfoBaseBean 用户信息
         */
        void getUserInfoResult(UserInfoBaseBean userInfoBaseBean);

        void getDetSucess(OrderDetialBean orderDetialBean);
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         *
         * @param signOrderCode 订单Id
         */
        /**
         * 发送查询订单详情请求
         *
         * @param signOrderCode  订单code
         * @param operDoctorCode 医生ID、
         * @param operDoctorName 医生name
         */
        void sendSearchOrderDetialRequest(String signOrderCode, String operDoctorCode, String operDoctorName);

        void sendCancelContractConConfirmRequest(String loginDoctorPosition,
                                                 String mainDoctorCode,
                                                 String mainDoctorName,
                                                 String signCode,
                                                 String signNo,
                                                 String mainPatientCode,
                                                 String mainUserName,
                                                 String confimresult,
                                                 String refuseReasonClassCode,
                                                 String refuseReasonClassName,
                                                 String refuseRemark);

        /**
         * 获取医生患者信息
         *
         * @param userCodeList 医生或者患者Id列表
         */
        void sendGetUserListRequest(String userCodeList);

        void getOrderDet(String params);
    }
}
