package com.hyphenate.easeui.order;

import android.app.Activity;

import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.RefusedOrderBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-07 16:51
 */
public class RefusedOrderContract {

    public interface View extends BaseView {

        /**
         * 获取解约原因列表
         *
         * @param cancelContractBeans 解约原因列表
         */
        void getBasicDomainResult(List<BaseReasonBean> cancelContractBeans);

        /**
         * 获取拒绝订单返回结果
         * @param isSucess true or false
         * @param msg 拒绝订单返回信息
         */
        void getRefusedOrderResult(boolean isSucess, String msg);

        /**
         * 获取订单信息返回结果
         * @param orderDetialBean 订单信息
         */
        void getSearchSignPatientDoctorOrderResult(OrderDetialBean orderDetialBean);
        /**
         * 获取用户信息返回结果
         * @param userInfoBaseBean 用户信息
         */
        void getUserInfoResult(UserInfoBaseBean userInfoBaseBean);

        void getRufusedDet(RefusedOrderBean userInfoBaseBean);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 更具
         *
         * @param baseCode 基础code
         */
        void sendGetBasicsDomainRequest(String baseCode);

        /**
         * 发送拒绝订单请求
         * @param loginDoctorPosition 位置
         * @param mainDoctorCode 医生code
         * @param mainDoctorName 医生名称
         * @param signCode 订单code
         * @param signNo 订单No
         * @param mainPatientCode 患者code
         * @param mainUserName 患者名称
         * @param confimresult 操作： 0拒绝，1同意，2需修改
         * @param refuseReasonClassCode 解约原因分类编码
         * @param refuseReasonClassName 解约原因分类名称
         * @param refuseRemark 解约原因
         */
        void sendRefusedOrderRequest(String loginDoctorPosition,
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
         * 发送获取订单信息接口
         * @param signCode 订单Id
         * @param operDoctorCode 医生code
         * @param operDoctorName 医生name
         */
        void sendSearchSignPatientDoctorOrderRequest(String signCode, String operDoctorCode, String operDoctorName);

        /**
         * 获取医生患者信息
         * @param userCodeList 医生或者患者Id列表
         */
        void sendGetUserListRequest(String userCodeList);

        void getOrderDet(String userCodeList);

    }
}
