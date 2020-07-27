package www.patient.jykj_zxyl.base.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.allen.library.utils.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionFragment;

import www.patient.jykj_zxyl.base.R;


/**
 * 当使用viewpager加载Fragment，沉浸式的使用，原理懒加载
 *
 */
public abstract class BaseLazyFragment extends SimpleImmersionFragment {
    //private Unbinder unbinder;
    protected Activity mActivity;
    protected View mRootView;
    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完onViewCreated方法后即为true
     */
    protected boolean mIsPrepare;
    /**
     * 是否加载完成
     * 当执行完onViewCreated方法后即为true
     */
    protected boolean mIsImmersion;
    /**
     * 进度加载框
     */

    private Dialog dialog;
    private TextView tvMessage;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(setLayoutId(), container, false);
        } else {
            ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mRootView);
            }
        }
        Resources res = getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        //绑定控件
//        unbinder = ButterKnife.bind(this,view);
        initView();
        if (isLazyLoad()) {
            mIsPrepare = true;
            mIsImmersion = true;
            onLazyLoad();
        } else {
            if (isImmersionBarEnabled()){
                initImmersionBar();
            }
            initData();
        }
        setListener();
        //初始化图片缩放过度组件
        testTransferee();
    }
    public boolean statusEvent(){
        return false;
    }
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init();
        //在BaseActivity里初始化
        //ImmersionBar.with(this).titleBar(R.id.toolbar).init();
        //设置共同沉浸式样式
        ImmersionBar.with(this)
                .navigationBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (unbinder != null) {
//            unbinder.unbind();
//        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    /**
     * 是否懒加载
     *
     * @return the boolean
     */
    protected boolean isLazyLoad() {
        return true;
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisible() {
        onLazyLoad();
    }

    private void onLazyLoad() {
        if (mIsVisible && mIsPrepare) {
            mIsPrepare = false;
            initData();
        }
        if (mIsVisible && mIsImmersion && isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    /**
     * Sets layout id.
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {
    }
    /**
     * 设置监听
     */
    protected void setListener() {
    }
    /**
     * 用户不可见执行
     */
    protected void onInvisible() {

    }
    protected  void testTransferee(){

    }




//    public void showSUCCESS(String msg) {
//        if (TextUtils.isEmpty(msg)) {
//            msg = "发送成功";
//        }
//        tipDialog = new QMUITipDialog.Builder(mActivity)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
//                .setTipWord(msg)
//                .create();
//        tipDialog.show();
//        dismissTipDialog();
//    }
//
//    public void showFAIL(String msg) {
//        if (TextUtils.isEmpty(msg)) {
//            msg = "发送失败";
//        }
//        tipDialog = new QMUITipDialog.Builder(mActivity)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
//                .setTipWord(msg)
//                .create();
//        tipDialog.show();
//        dismissTipDialog();
//    }
//
//    public void showINFO(String msg) {
//        if (TextUtils.isEmpty(msg)) {
//            msg = "请勿重复操作";
//        }
//        tipDialog = new QMUITipDialog.Builder(mActivity)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
//                .setTipWord(msg)
//                .create();
//        tipDialog.show();
//        dismissTipDialog();
//    }
//
//    public void showCustom(String msg) {
//        tipDialog = new QMUITipDialog.CustomBuilder(mActivity)
//                .setContent(R.layout.dialog_loading)
//                .create();
//        tipDialog.show();
//        dismissTipDialog();
//    }
//
//    public void dismissTipDialog() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tipDialog.dismiss();
//            }
//        },1500);
//    }

    /**
     * 跳转Activity
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     */
    @SuppressWarnings("rawtypes")
    public void startActivity(Class paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this.getActivity(), paramClass);
        this.startActivity(localIntent);
    }

    /**
     * 跳转Activity,需要回传值
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     * @param requestCode 请求码
     */
    @SuppressWarnings("rawtypes")
    public void startActivity(Class paramClass, Bundle paramBundle, int requestCode) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this.getActivity(), paramClass);
        this.startActivityForResult(localIntent, requestCode);
    }
}
