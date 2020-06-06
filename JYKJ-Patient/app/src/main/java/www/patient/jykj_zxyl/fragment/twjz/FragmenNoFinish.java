package www.patient.jykj_zxyl.fragment.twjz;

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

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.patient.jykj_zxyl.activity.home.TWJZActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_KJCFActivity;
import www.patient.jykj_zxyl.activity.home.twjz.WZXXActivity;
import www.patient.jykj_zxyl.adapter.TWJZNoFinishRecycleAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.TWJZActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_KJCFActivity;
import www.patient.jykj_zxyl.activity.home.twjz.WZXXActivity;
import www.patient.jykj_zxyl.adapter.TWJZNoFinishRecycleAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 图文就诊未完成fragment
 * Created by admin on 2016/6/1.
 */
public class FragmenNoFinish extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private TWJZActivity mActivity;
    private             JYKJApplication                     mApp;
    private             RecyclerView                        mNoFinishRecycleView;              //未完成列表
    private             LinearLayoutManager                 layoutManager;
    private TWJZNoFinishRecycleAdapter mTWJZNoFinishRecycleAdapter;       //适配器
    private             List<HZIfno>                        mHZEntyties = new ArrayList<>();            //所有数据



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitytwjz_nofinish, container, false);
        mContext = getContext();
        mActivity = (TWJZActivity) getActivity();
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
        mNoFinishRecycleView = (RecyclerView) view.findViewById(R.id.rv_fragmentNoFinish_nofinish);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mNoFinishRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mNoFinishRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
//        mTWJZNoFinishRecycleAdapter = new TWJZNoFinishRecycleAdapter(mHZEntyties,mContext);
//        mNoFinishRecycleView.setAdapter(mTWJZNoFinishRecycleAdapter);
        mTWJZNoFinishRecycleAdapter.setOnWZXXItemClickListener(new TWJZNoFinishRecycleAdapter.OnWZXXItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,WZXXActivity.class));
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //开具处方
        mTWJZNoFinishRecycleAdapter.setOnKJCFItemClickListener(new TWJZNoFinishRecycleAdapter.OnKJCFItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,TWJZ_KJCFActivity.class));
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //处方签
        mTWJZNoFinishRecycleAdapter.setOnCFQItemClickListener(new TWJZNoFinishRecycleAdapter.OnCFQItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,TWJZ_CFQActivity.class));
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

                }
            }
        };
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {



            }
        }
    }


    /**
     * 设置数据
     */
    private void setData() {
        for (int i = 0; i < 40; i++)
        {
            HZIfno hzIfno = new HZIfno();
            if (i%3 == 0)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setTwjzZT(1);
            }
            if (i%3 == 1)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(-1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setTwjzZT(2);
            }
            if (i%3 == 2)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setTwjzZT(3);
            }
            mHZEntyties.add(hzIfno);
        }
//        mTWJZNoFinishRecycleAdapter.setDate(mHZEntyties);
//        mTWJZNoFinishRecycleAdapter.notifyDataSetChanged();
    }
}
