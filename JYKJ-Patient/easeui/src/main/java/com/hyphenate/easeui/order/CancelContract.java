package com.hyphenate.easeui.order;

import android.app.Activity;

import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.SignOrderInfoBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Description:解约契约类
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:23
 */
public class CancelContract {

    public interface View extends BaseView {
        /**
         * 获取解约原因列表
         *
         * @param cancelContractBeans 解约原因列表
         */
        void getBasicDomainResult(List<BaseReasonBean> cancelContractBeans);


        /**
         * 获取解约结果
         * @param isSucess true or false
         * @param msg 返回结果信息
         */
        void getCancelContractResult(boolean isSucess, String msg);

        /**
         * 获取签约信息返回结果
         * @param signOrderInfoBean 订单信息
         */
        void getSerchSignInfoByPatientCodeResult(SignOrderInfoBean
                                                         signOrderInfoBean);

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


    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 更具
         *
         * @param baseCode 基础code
         */
        void sendGetBasicsDomainRequest(String baseCode);

        /**
         * 发送解约请求
         * @param loginDoctorPosition 位置
         * @param mainDoctorCode 医生编码
         * @param mainDoctorName 医生姓名
         * @param signCode 签约订单编码
         * @param signNo 签约订单编号
         * @param mainPatientCode 患者编码
         * @param mainUserName 	患者名称
         * @param refuseReasonClassCode 解约原因分类编码
         * @param refuseReasonClassName 解约原因分类名称
         * @param refuseRemark 解约原因
         */
        void setOperTerminationSumbitRequest(String loginDoctorPosition
                , String mainDoctorCode, String mainDoctorName, String signCode,
                                             String signNo,
                                             String mainPatientCode,
                                             String mainUserName,
                                             String refuseReasonClassCode,
                                             String refuseReasonClassName,
                                             String refuseRemark

        );

        /**
         * 查询患者订单信息
         * @param loginDoctorPosition 位置
         * @param mainPatientCode 患者的code
         * @param mainDoctorCode 医生的code
         */
        void sendSerchSignInfoByPatientCodeRequest(String loginDoctorPosition,
                                                   String mainPatientCode, String
                                                           mainDoctorCode);

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
    }
}
