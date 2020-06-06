package www.patient.jykj_zxyl.fragment.myself.jwbs;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.jyzl.GRXX_GRZK_JWBSActivity;
import www.patient.jykj_zxyl.activity.home.jyzl.JWBSDetailActivity;
import www.patient.jykj_zxyl.activity.home.myself.JWBSActivity;
import www.patient.jykj_zxyl.activity.home.myself.JWBSLRBSActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.patient.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.patient.jykj_zxyl.adapter.myself.JDDA_JWBS_BRTXAdapter;
import www.patient.jykj_zxyl.adapter.myself.MyOrderAlRecycleAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 建档档案 == 》 既往病史 ==》 本人填写
 * Created by admin on 2016/6/1.
 */
public class FragmentJWBS_BRTX extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private JWBSActivity mActivity;
    private             JYKJApplication                     mApp;

    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;

    private         String                  mPatientCode;
    private         int                     mRowNum = 10;                            //每页行数
    private         int                     mPageNum = 1;                           //页数
    private          int                    recordTypeId = 0;                           //搜索的记录类型
    private TextView mMore;

    private         LinearLayout            mQB;                                //选择全部
    private         LinearLayout            mJWBS;                                //选择既往病史
    private         LinearLayout            mBCJL;                                //选择病程记录
    private RelativeLayout mBack;
    private         List<ProvidePatientConditionDiseaseRecord> mProvidePatientConditionDiseaseRecords = new ArrayList<>();

    private JDDA_JWBS_BRTXAdapter mJDDA_JWBS_BRTXAdapter;

    private TextView    tv_xzbs;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_jdda_jwbs_brtx, container, false);
        mContext = getContext();
        mActivity = (JWBSActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        setData();
        return v;
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        tv_xzbs = (TextView)view.findViewById(R.id.tv_szbs);
        tv_xzbs.setOnClickListener(new ButtonClick());
        mRecycleView = (RecyclerView)view.findViewById(R.id.rv_activityPatientLaber_patientLaber);

        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJDDA_JWBS_BRTXAdapter = new JDDA_JWBS_BRTXAdapter(mProvidePatientConditionDiseaseRecords,mContext);
        mRecycleView.setAdapter(mJDDA_JWBS_BRTXAdapter);
        mJDDA_JWBS_BRTXAdapter.setOnItemClickListener(new JDDA_JWBS_BRTXAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
//                startActivity(new Intent(mContext,JWBSDetailActivity.class).putExtra("patientInfo",mProvidePatientConditionDiseaseRecords.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                      break;
                }
            }
        };
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_szbs:
                    startActivity(new Intent(mContext,JWBSLRBSActivity.class));
                    break;

            }
        }
    }
    /**
      * 设置数据
     */
    private void setData() {

    }

}
