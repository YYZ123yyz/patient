package www.patient.jykj_zxyl.fragment.shouye;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;

public class FragmentShouYe_Graphic extends Fragment {
    private Context mContext;
    private Handler mHandler;
    private MainActivity mActivity;
    private JYKJApplication mApp;
    private LinearLayoutManager layoutManager;
    private String mNetLoginRetStr;
    private RecyclerView rgaphic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye_graphic, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        getData();
        initHandler();
        return v;
    }

    /*
     * 数据请求
     * */
    private void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("rowNum", "10");
        map.put("pageNum", "1");
        map.put("loginDoctorPosition", "108.93425^34.23053");
//            map.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
//            map.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        new Thread() {
            public void run() {
                try {
                    mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorPersonalSetControlle/searchBasicsSystemHelpResList");

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetLoginRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                }
            }
        };
    }

    private void initLayout(View v) {
        rgaphic = v.findViewById(R.id.rgaphic);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        rgaphic.setLayoutManager(layoutManager);
        //   GraPhicAdapter
    }

}
