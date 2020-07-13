package www.patient.jykj_zxyl.fragment.shouye;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.ZJXQActivity;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeTJZJAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.ZJXQActivity;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeTJZJAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 首页==》专家推荐fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe_ZJTJ extends Fragment {
    private Context mContext;
    private Handler mHandler;
    private MainActivity mActivity;
    private JYKJApplication mApp;
    private RecyclerView mRecyclerView;
    private LinearLayout mTJZJMore;                          //推荐专家更多
    private FragmentHomeTJZJAdapter mAdapter;
    private List<ProvideViewDoctorExpertRecommend> provideViewDoctorExpertRecommendList = new ArrayList<>();            //获取到的专家数据
    private String mNetRetStr;                 //返回字符串
    private View mView;

    private TextView more;                           //查看更多

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye_tjzj, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        mView = v;
        //获取推荐专家

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        provideViewDoctorExpertRecommendList.clear();
        getRecommendDoctor();
    }

    /**
     * 获取推荐专家
     */
    private void getRecommendDoctor() {
        ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend = new ProvideViewDoctorExpertRecommend();
        provideViewDoctorExpertRecommend.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewDoctorExpertRecommend.setRequestClientType("1");
        provideViewDoctorExpertRecommend.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewDoctorExpertRecommend.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideViewDoctorExpertRecommend.setShowNum("1");

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideViewDoctorExpertRecommend);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "patientSearchDoctorControlle/searchIndexExpertRecommendDoctorShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        more = (TextView) view.findViewById(R.id.more);
        more.setOnClickListener(new ButtonClick());
        mRecyclerView = view.findViewById(R.id.tjzj_rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new FragmentHomeTJZJAdapter(provideViewDoctorExpertRecommendList, mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new FragmentHomeTJZJAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext, ZJXQActivity.class).putExtra("provideViewDoctorExpertRecommend", provideViewDoctorExpertRecommendList.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        else {
                            provideViewDoctorExpertRecommendList = JSON.parseArray(netRetEntity.getResJsonData(), ProvideViewDoctorExpertRecommend.class);
                            setDoctorExpertRecommend();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 设置推荐专家数据
     *
     * @param
     */
    public void setDoctorExpertRecommend() {
        mAdapter.setDate(provideViewDoctorExpertRecommendList);
        mAdapter.notifyDataSetChanged();
    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.more:
                    startActivity(new Intent(mContext, TJZJActivity.class));
                    break;

            }
        }
    }


}
