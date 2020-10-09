package www.patient.jykj_zxyl.myappointment.Contract;

import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Created by G on 2020/10/8 16:55
 */
public class AllDepartmentsContract {

    public interface View extends BaseView {

        void getAlldetmentsSucess(List<AllDepartmentBean.HospitalDepartmentListBean> departments);

        void getTittleMentsSucess(List<AllDepartmentBean.TitleHospitalDepartmentBean> tittleDepments);

        void getDataFailure(String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        void getAlldepartments(String params);

    }

}
