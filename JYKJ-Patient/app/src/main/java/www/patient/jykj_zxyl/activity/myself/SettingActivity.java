package www.patient.jykj_zxyl.activity.myself;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.TextView;
import www.patient.jykj_zxyl.activity.myself.setting.AboutActivity;
import www.patient.jykj_zxyl.activity.myself.setting.OpeaPassWordActivity;
import www.patient.jykj_zxyl.activity.myself.setting.ServiceHotlineActivity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.LoginActivity;
import www.patient.jykj_zxyl.activity.myself.setting.AboutActivity;
import www.patient.jykj_zxyl.activity.myself.setting.OpeaPassWordActivity;
import www.patient.jykj_zxyl.activity.myself.setting.ServiceHotlineActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.MyUtil;

/**
 * 设置
 */
public class SettingActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private SettingActivity mActivity;
    private                 Button                      mExitButton;                    //退出当前账号
    private                 JYKJApplication             mApp;
    private                 LinearLayout                mAboutLayout;                   //关于我们
    private                 LinearLayout                mServiceHolting;                //客服热线
    private                 LinearLayout                mOperPassWord;                  //修改密码
    private LinearLayout clear_app_cache;//清除缓存
    private TextView cache_size;
    private TextView version_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_setting);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        setData();
    }



    /**
     * 初始化布局
     */
    private void initLayout() {
        mExitButton = (Button)this.findViewById(R.id.bt_activityMySelfSetting_exitButton);
        mExitButton.setOnClickListener(new ButtonClick());

        mAboutLayout = (LinearLayout)this.findViewById(R.id.li_activitySetting_aboutLayout);
        mAboutLayout.setOnClickListener(new ButtonClick());

        mServiceHolting = (LinearLayout)this.findViewById(R.id.li_activitySetting_serviceHolting);
        mServiceHolting.setOnClickListener(new ButtonClick());

        mOperPassWord = (LinearLayout)this.findViewById(R.id.li_activitySetting_operPassWord);
        mOperPassWord.setOnClickListener(new ButtonClick());

        clear_app_cache = findViewById(R.id.clear_app_cache);
        clear_app_cache.setOnClickListener(new ButtonClick());

        version_name = findViewById(R.id.version_name);
        cache_size = findViewById(R.id.cache_size);
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_activityMySelfSetting_exitButton:
                    //清除缓存
                    mApp.cleanPersistence();
                    startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                    for (int i = 0; i < mApp.gActivityList.size(); i++)
                    {
                        mApp.gActivityList.get(i).finish();
                    }
                    break;
                case R.id.li_activitySetting_aboutLayout:
                    startActivity(new Intent(SettingActivity.this,AboutActivity.class));
                    break;
                case R.id.li_activitySetting_serviceHolting:
                    startActivity(new Intent(SettingActivity.this,ServiceHotlineActivity.class));
                    break;
                case R.id.li_activitySetting_operPassWord:
                    startActivity(new Intent(SettingActivity.this,OpeaPassWordActivity.class));
                    break;
                case R.id.clear_app_cache:
                    new AlertDialog.Builder(mContext).setTitle("确认清楚缓存")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //按下确定键后的事件
                                    MyUtil.cleanExternalCache(mContext);
                                    MyUtil.cleanInternalCache(mContext);
                                    try {
                                        cache_size.setText(MyUtil.getAllCacheSize(mContext));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //Intent theint = new Intent(SettingActivity.this,LoginActivity.class);
                                    //SettingActivity.this.startActivity(theint);
                                }
                            }).setNegativeButton("取消",null).show();
                    break;
            }
        }
    }

    /**
     * 设置数据
     */
    private void setData() {
        try {
            cache_size.setText(MyUtil.getAllCacheSize(mContext));
            version_name.setText(MyUtil.getAppVersionName(mContext));
        }catch (Exception ex){

        }
    }



}
