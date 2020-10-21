package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import entity.HealthListBean;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.BloodMonitorActivity;
import www.patient.jykj_zxyl.activity.home.patient.BloodEntryActivity;
import www.patient.jykj_zxyl.activity.myself.MedicationSettingsActivity;
import www.patient.jykj_zxyl.activity.myself.order.RefundApplyContract;
import www.patient.jykj_zxyl.adapter.HealthListAdapter;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.adapter.HealthAdapter;
import www.patient.jykj_zxyl.presenter.HelathPresenter;
import www.patient.jykj_zxyl.presenter.MedicalRecordPresenter;
import www.patient.jykj_zxyl.util.ToastUtils;
import www.patient.jykj_zxyl.view.HealthView;
import www.patient.jykj_zxyl.view.MedicalRecordView;

/*
 * 信息录入入口
 * */
public class HealthActivity extends AbstractMvpBaseActivity<HealthView, HelathPresenter> {

    private String mNetRetStr;
    private Handler mHandler;
    private ImageView ivBack;
    private RecyclerView healthRv;
    private String[] types = {"血压", "服药", "脉搏", "血糖", "血脂", "尿糖", "心电图", "肾功能", "睡眠", "体温", "体重"};
    private int[] ivSrc = {R.mipmap.iv_pre, R.mipmap.iv_med, R.mipmap.iv_pulse, R.mipmap.iv_bloodsuger, R.mipmap.iv_lipids,
            R.mipmap.iv_unire, R.mipmap.iv_ecg, R.mipmap.iv_kidney, R.mipmap.iv_sleep, R.mipmap.iv_temper, R.mipmap.iv_weight};
    private String[] unit = {"mmhg", "小时", "小时", "mmol/L", "", "", "", "", "小时", "℃", "kg"};

    @Override
    protected void initData() {
        super.initData();
        data();
        initHandler();

        initRecycle();
    }

    private void initRecycle() {
        ArrayList<HealthListBean> healthListBeans = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            HealthListBean healthListBean = new HealthListBean();
            healthListBean.setType(types[i]);
            healthListBean.setIvSrc(ivSrc[i]);
            healthListBean.setUnit(unit[i]);
            healthListBean.setTime("今天20:00");
            healthListBean.setData("130");
            healthListBeans.add(healthListBean);
        }

        HealthListAdapter healthListAdapter = new HealthListAdapter(R.layout.item_health, healthListBeans);
        healthRv.setAdapter(healthListAdapter);

        healthListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:  //血压录入
//                        startActivity(new Intent(HealthActivity.this, BloodEntryActivity.class));
                        startActivity(new Intent(HealthActivity.this, BloodMonitorActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(HealthActivity.this, MedicationSettingsActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_health;
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        break;
                }
            }
        };
    }

    private void data() {
        HashMap<String, String> map = new HashMap<>();
        map.put("rowNum", "10");
        map.put("pageNum", "1");
        map.put("loginDoctorPosition", "108.93425^34.23053");
//        map.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
//        map.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorPersonalSetControlle/searchBasicsSystemHelpResList");

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        healthRv = (RecyclerView) findViewById(R.id.health_rv);
        healthRv.setLayoutManager(new LinearLayoutManager(this));

    }


    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void showLoading(int code) {

    }
}