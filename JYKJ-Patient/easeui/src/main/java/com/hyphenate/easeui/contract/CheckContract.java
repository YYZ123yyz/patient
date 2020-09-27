package com.hyphenate.easeui.contract;

import android.app.Activity;

import com.hyphenate.easeui.order.OrderDetialContract;

import www.patient.jykj_zxyl.base.base_bean.CheckNumBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.PaymentBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Created by G on 2020/9/23 16:23
 */
public class CheckContract {

    public interface View extends BaseView {
        void getCheckNumSucess(CheckNumBean bean);

        void getDataFiled(String msg);

        void submitDataSucess(String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        void getCheckNum(String params);

        void submitData(String params);
    }
}
