package www.patient.jykj_zxyl.base.base_activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;


import com.gyf.immersionbar.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.patient.jykj_zxyl.base.R;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;

/**
 * Description:基础类
 *
 * @author: qiuxinhai
 * @date: 2020-07-14 11:38
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**上下文*/
    protected Context context;
    /**ButterKnife*/
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        onBeforeSetContentLayout();
        context = this;
        Resources res = getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        //将Activity添加到队列方便管理
        ActivityStackManager.getInstance().add(this);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(setLayoutId());

        unbinder = ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
            setStatusBarColor();
        }
        //初始化view
        initView();
        //初始化数据
        initData();
        //设置监听
        setListener();
    }


    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this).titleBar(R.id.toolbar).init();
    }

    protected void setStatusBarColor(){
        //设置共同沉浸式样式
        ImmersionBar.with(this)
                .navigationBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }


    protected abstract int setLayoutId();

    protected void initData() {

    }

    protected void initView() {
    }

    protected void setListener() {
    }

    protected void onBeforeSetContentLayout() {

    }
    /**
     * 跳转Activity
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     */
    @SuppressWarnings("rawtypes")
    protected void startActivity(Class paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this, paramClass);
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
    protected void startActivity(Class paramClass, Bundle paramBundle, int requestCode) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this, paramClass);
        this.startActivityForResult(localIntent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
