package www.patient.jykj_zxyl.activity.myself.order;

import android.app.Activity;
import android.drm.ProcessedData;

import com.alibaba.fastjson.JSON;
import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allin.commlibrary.CollectionUtils;

import java.util.HashMap;
import java.util.List;

import entity.mySelf.MyOrderProcess;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;

/**
 * Description:进行中订单列表Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:25
 */
public class OrderOnGoingPresenter extends BasePresenterImpl<OrderOnGoingContract.View>
        implements OrderOnGoingContract.Presenter {
    private static String GET_SEARCH_PATIENT_ORDER_ONGOING_REQUEST_TAG = "get_search_patient_order" +
            "_onging_request_tag";
    /**获取医生或者患者信息Tag*/
    private static final String SEND_GET_USERINFO_REQUEST_TAG="send_get_userinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{GET_SEARCH_PATIENT_ORDER_ONGOING_REQUEST_TAG,SEND_GET_USERINFO_REQUEST_TAG};
    }

    @Override
    public void sendSearchPatientMyOrderResOngoingRequest(String rowNum,
                                                          String pageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBasePatientParam(activity);
        hashMap.put("rowNum", rowNum);
        hashMap.put("pageNum", pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchPatientMyOrderResOngoing(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(100);
                }
            }

            @Override
            public void hideLoadingView() {
                if (mView!=null) {
                    mView.hideLoading();
                }
            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            List<MyOrderProcess> myOrderProcesses =
                                    JSON.parseArray(resJsonData, MyOrderProcess.class);
                            mView.getSearchPatientMyOrderOngoingResult(myOrderProcesses);
                        }else{
                            mView.showEmpty();
                        }

                    }else{
                        mView.showEmpty();
                    }
                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return GET_SEARCH_PATIENT_ORDER_ONGOING_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendGetUserListRequest(String userCodeList) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("userCodeList",userCodeList);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getUserInfoList(s).
                compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        List<UserInfoBaseBean> userInfoBaseBeans = GsonUtils.jsonToList(resJsonData,
                                UserInfoBaseBean.class);
                        if (!CollectionUtils.isEmpty(userInfoBaseBeans)) {
                            mView.getUserInfoResult(userInfoBaseBeans.get(0));
                        }

                    }
                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_GET_USERINFO_REQUEST_TAG;
            }
        });
    }
}
