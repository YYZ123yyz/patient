package www.patient.jykj_zxyl.activity.myself.order;

import android.app.Activity;

import java.util.List;

import entity.mySelf.ProvideInteractOrderInfo;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Description:解约契约类
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:23
 */
public class OrderToBeConfirmedContract {

    public interface View extends BaseView {
        /**
         * 获取未完成订单列表返回结果
         * @param provideInteractOrderInfos 列表
         */
        void getSearchPatientMyOrderResInCompleteResult(List<ProvideInteractOrderInfo>
                                                                provideInteractOrderInfos);

        /**
         * 获取用户信息返回结果
         * @param userInfoBaseBean 用户信息
         */
        void getUserInfoResult(UserInfoBaseBean userInfoBaseBean);

        void deleteSucess(String msg);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 发送获取未完成订单请求
         * @param rowNum 每页大小
         * @param pageNum 开始位置
         * @param activity 页面
         */
        void sendSearchPatientMyOrderResIncompleteRequest(String rowNum,
                                                          String pageNum, Activity activity);

        /**
         * 获取医生患者信息
         * @param userCodeList 医生或者患者Id列表
         */
        void sendGetUserListRequest(String userCodeList);


        void deleteRecord(String userCodeList);
    }
}
