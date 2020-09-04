package www.patient.jykj_zxyl.myappointment.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.order.RefundApplyContract;

/*
 * 信息录入入口
 * */
public class HealthActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayoutManager layoutManager;
    private String mNetRetStr;
 private Handler mHandler;
    private ImageView ivBack;
    private RecyclerView healthRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        ButterKnife.bind(this);
        Window window = getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        initView();
        data();
        initHandler();
    }

    private void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
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

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        healthRv = findViewById(R.id.health_rv);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        healthRv.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
        }
    }
}