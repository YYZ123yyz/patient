package com.hyphenate.easeui.order;

import www.patient.jykj_zxyl.base.base_bean.AncelAppBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

public class AncelAppContract {
    public interface View extends BaseView {
        /**
         *   取消预约详情
         * @param ancelAppBean
         */
        void getAncelAppResult(AncelAppBean ancelAppBean);
    }
    public interface Presenter extends BasePresenter<AncelAppContract.View>{
        void sendAncelAppRequest(String loginPatientPosition,
                                 String mainPatientCode,
                                 String mainPatientName,
                                 String reserveCode);
    }
}
