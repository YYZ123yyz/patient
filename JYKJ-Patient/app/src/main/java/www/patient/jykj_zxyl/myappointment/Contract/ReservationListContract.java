package www.patient.jykj_zxyl.myappointment.Contract;

import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.MyReservationListBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientDoctorInfoBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.base.mvp.BaseView;

public class ReservationListContract extends BasePresenterImpl<ReservationListContract.View> {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    public interface View extends BaseView {
        /**
         * 我的预约列表
         * @param myReservationListBeans
         */
        void getMyReservationListResult(List<MyReservationListBean> myReservationListBeans);
    }
    public interface Presenter extends BasePresenter<ReservationListContract.View> {
        //类型选择
        void sendMyReservationListRequest(String loginPatientPosition,
                                          String mainPatientCode,
                                          String mainPatientName,
                                          String reserveStatusType,
                                          String rowNum,
                                          String pageNum
                                          );
    }
}
