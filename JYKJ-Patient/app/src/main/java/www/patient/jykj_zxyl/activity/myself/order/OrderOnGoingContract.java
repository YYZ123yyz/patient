package www.patient.jykj_zxyl.activity.myself.order;

import android.app.Activity;

import java.util.List;

import entity.mySelf.MyOrderProcess;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Description:订单详情契约类
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:23
 */
public class OrderOnGoingContract {

    public interface View extends BaseView {

        /**
         * 获取进行中订单列表
         * @param myOrderProcesses 订单列表
         */
        void getSearchPatientMyOrderOngoingResult(List<MyOrderProcess> myOrderProcesses);

        /**
         * 获取用户信息返回结果
         * @param userInfoBaseBean 用户信息
         */
        void getUserInfoResult(UserInfoBaseBean userInfoBaseBean);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 发送进行中订单列表请求
         * @param rowNum 每页大小
         * @param pageNum 第几页
         * @param activity 当前activity
         */
        void sendSearchPatientMyOrderResOngoingRequest(String rowNum,
                                                       String pageNum,
                                                       Activity activity);

        /**
         * 获取医生患者信息
         * @param userCodeList 医生或者患者Id列表
         */
        void sendGetUserListRequest(String userCodeList);
    }
}
