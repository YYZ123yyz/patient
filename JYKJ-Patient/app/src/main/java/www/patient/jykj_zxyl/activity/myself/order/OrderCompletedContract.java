package www.patient.jykj_zxyl.activity.myself.order;

import android.app.Activity;

import java.util.List;

import entity.mySelf.CommentInfo;
import entity.mySelf.MyOrderProcess;
import entity.mySelf.ZhlyDetailInfo;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Description:订单详情契约类
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:23
 */
public class OrderCompletedContract {

    public interface View extends BaseView {
        /**
         * 获取已完成订单列表
         * @param myOrderProcesses 订单列表
         */
        void getSearchPatientMyOrderCompleteResult(List<MyOrderProcess>
                                                           myOrderProcesses);

        /**
         * 获取订单信息
         * @param zhlyDetailInfo 订单信息
         */
        void getSearchPatientOrderByCodeResult(ZhlyDetailInfo zhlyDetailInfo);

        /**
         * 搜索评论结果
         * @param commentInfo
         */
        void searchPatientCommentResult(CommentInfo commentInfo);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 发送患者已完成订单列表请求
         * @param rowNum 每页大小
         * @param pageNum 开始位置
         */
        void sendSearchPatientMyOrderResCompletedRequest(String rowNum,
                                                         String pageNum,
                                                         Activity activity);

        /**
         * 发送获取订单请求
         * @param orderCode 订单code
         */
        void sendSearchPatientOrderByCodeRequest(String orderCode, Activity activity);

        /**
         * 发送订单评论请求
         * @param orderCode 订单code
         * @param activity activity
         */
        void searchPatientMyOrderResCommentRequest(String orderCode, Activity activity);
    }
}
