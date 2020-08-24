package www.patient.jykj_zxyl.activity.myself.order;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import entity.mySelf.CommentInfo;
import entity.mySelf.MyOrderProcess;
import entity.mySelf.ZhlyDetailInfo;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
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
public class OrderCompletedPresenter extends BasePresenterImpl<OrderCompletedContract.View>
        implements OrderCompletedContract.Presenter {
    private static final String SEND_SEARCH_PATIENT_MYORDER_COMPLATE_REQUEST_TAG="send_search_patient_myorder_complate_request_tag";

    private static final String SEND_SEARCH_PATIENT_ORDER_BY_CODE_REQUEST_TAG="send_search_patient_order_by_code_request_tag";

    private static final String SEND_SEARCH_PATIENT_ORDER_COMMENT_REQUEST_TAG="send_search_patient_order_by_code_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_PATIENT_MYORDER_COMPLATE_REQUEST_TAG,
                SEND_SEARCH_PATIENT_ORDER_BY_CODE_REQUEST_TAG,SEND_SEARCH_PATIENT_ORDER_COMMENT_REQUEST_TAG};
    }

    @Override
    public void sendSearchPatientMyOrderResCompletedRequest(String rowNum, String pageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBasePatientParam(activity);
        hashMap.put("rowNum", rowNum);
        hashMap.put("pageNum", pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchPatientMyOrderResCompleted(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        List<MyOrderProcess> myOrderProcesses =
                                JSON.parseArray(baseBean.getResJsonData(), MyOrderProcess.class);
                        mView.getSearchPatientMyOrderCompleteResult(myOrderProcesses);

                    }else{
                        mView.showRetry();
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
                return SEND_SEARCH_PATIENT_MYORDER_COMPLATE_REQUEST_TAG;
            }
        });

    }

    @Override
    public void sendSearchPatientOrderByCodeRequest(String orderCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBasePatientParam(activity);
        hashMap.put("orderCode", orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchPatientMyOrderResMessageContent(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView!=null) {
                            mView.showLoading(101);
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

                        ZhlyDetailInfo zhlyDetailInfo =
                                JSON.parseObject(baseBean.getResJsonData(), ZhlyDetailInfo.class);
                        mView.getSearchPatientOrderByCodeResult(zhlyDetailInfo);

                    }
                }
            }


            @Override
            protected void onError(String s) {
                super.onError(s);

            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_PATIENT_MYORDER_COMPLATE_REQUEST_TAG;
            }
        });
    }

    @Override
    public void searchPatientMyOrderResCommentRequest(String orderCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBasePatientParam(activity);
        hashMap.put("orderCode", orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchPatientMyOrderResComment(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView!=null) {
                            mView.showLoading(101);
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
                        CommentInfo commentInfo =
                                JSON.parseObject(baseBean.getResJsonData(), CommentInfo.class);
                        mView.searchPatientCommentResult(commentInfo);

                    }
                }
            }


            @Override
            protected void onError(String s) {
                super.onError(s);

            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_PATIENT_MYORDER_COMPLATE_REQUEST_TAG;
            }
        });
    }

}
