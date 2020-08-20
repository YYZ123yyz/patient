package www.patient.jykj_zxyl.base.http;

import com.allen.library.bean.BaseData;
import com.allen.library.observer.StringObserver;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;

/**
 * Description:解析公共参数
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 19:44
 */
public abstract class CommonDataObserver extends StringObserver {
    @Override
    protected void onError(String s) {

    }

    protected abstract void onSuccessResult(BaseBean baseBean);

    @Override
    protected void onSuccess(String s) {
        BaseBean baseBean = GsonUtils.fromJson(s, BaseBean.class);
        if (baseBean!=null) {
//            if (baseBean.getResCode()==1) {
//                onSuccessResult(baseBean);
//            }else{
//                onError(baseBean.getResMsg());
//            }
            onSuccessResult(baseBean);
        }

    }


}
