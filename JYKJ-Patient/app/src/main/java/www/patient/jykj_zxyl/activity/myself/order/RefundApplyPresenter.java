package www.patient.jykj_zxyl.activity.myself.order;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;

/**
 * Description:退款申请Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:25
 */
public class RefundApplyPresenter extends BasePresenterImpl<RefundApplyContract.View>
        implements RefundApplyContract.Presenter {
    /**发送退款申请请求文字部分tag*/
    private static final String SEND_OPER_ORDER_RES_TEXT_REFUND_REQUEST_TAG="send_per_order_text_refund_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_OPER_ORDER_RES_TEXT_REFUND_REQUEST_TAG};
    }

    @Override
    public void sendOperPatientMyOrderResRefundRequest(String orderCode,
                                                       String imgCode, String refundTypeCode,
                                                       String refundTypeName, String refundReason, Activity activity) {

        HashMap<String, Object> hashMap = ParameUtil.buildBasePatientParam(activity);
        hashMap.put("orderCode",orderCode);
        hashMap.put("imgCode",imgCode);
        hashMap.put("refundTypeCode",refundTypeCode);
        hashMap.put("refundTypeName",refundTypeName);
        hashMap.put("refundReason",refundReason);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operPatientMyOrderResRefund(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getOperPatientMyOrderResFundResult(true,baseBean.getResMsg());
                    }else{
                        mView.getOperPatientMyOrderResFundResult(false,baseBean.getResMsg());
                    }

                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_OPER_ORDER_RES_TEXT_REFUND_REQUEST_TAG;
            }
        });
    }
}
