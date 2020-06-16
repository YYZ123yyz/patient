package www.patient.jykj_zxyl.fragment.shouye;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.ProvidePatientBindingMyDoctorInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.patient.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.patient.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 首页==》我的医生fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe_WDYS extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private MainActivity mActivity;
    private             JYKJApplication                     mApp;
    private             RecyclerView                        mHZInfoRecycleView;              //患者列表
    private             LinearLayoutManager                 layoutManager;
    private HZGLRecycleAdapter mHZGLRecycleAdapter;       //适配器
    private             List<HZIfno>                        mHZEntyties = new ArrayList<>();            //所有数据
    private             List<HZIfno>                        mHZEntytiesClick = new ArrayList<>();            //点击之后的数据

    private             LinearLayout                        mQB;            //全部
    private             LinearLayout                        mQBCut;         //全部下划线
    private             LinearLayout                        mYJ;            //预警
    private             LinearLayout                        mYJCut;         //预警下划线
    private             LinearLayout                        mTX;            //提醒
    private             LinearLayout                        mTXCut;         //提醒下划线
    private             LinearLayout                        mZC;            //正常
    private             LinearLayout                        mZCCut;         //正常下划线
    private             int                                 mState;         //状态

    private             ImageView                           mSearch;        //搜索

    public ProgressDialog mDialogProgress = null;

    private             String                              mNetRetStr;                 //返回字符串
    private                 List<ProvidePatientBindingMyDoctorInfo> providePatientBindingMyDoctorInfos = new ArrayList<>();         //获取到的我的医生数据

    private             LinearLayout                        shouye_layout1;                     //医生1
    private             ImageView                        shouye_heard1;                     //头像1
    private             TextView                        shouye_name1;                     //姓名1
    private             TextView                        shouye_bind1;                     //绑定1
    private             TextView                        shouye_hospatal1;                     //医院1

    private             LinearLayout                        shouye_layout2;                     //医生2
    private             ImageView                        shouye_heard2;                     //头像2
    private             TextView                        shouye_name2;                     //姓名2
    private             TextView                        shouye_bind2;                     //绑定2
    private             TextView                        shouye_hospatal2;                     //医院2

    private             LinearLayout                        shouye_layout3;                     //医生3
    private             ImageView                        shouye_heard3;                     //头像3
    private             TextView                        shouye_name3;                     //姓名3
    private             TextView                        shouye_bind3;                     //绑定3
    private             TextView                        shouye_hospatal3;                     //医院3


    private             LinearLayout                        shouye_layout4;                     //医生4
    private             ImageView                        shouye_heard4;                     //头像4
    private             TextView                        shouye_name4;                     //姓名4
    private             TextView                        shouye_bind4;                     //绑定4
    private             TextView                        shouye_hospatal4;                     //医院4


    private             TextView                        more;                                   //更多

    private             LinearLayout                    noHint;                               //没有数据提示
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye_wdys, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        searchIndexMyDoctorShow();
        return v;
    }


    /**
     * 展示我的医生数据
     * @param
     */
    private void showMyDoctorInfo() {
        switch (providePatientBindingMyDoctorInfos.size())
        {
            case 0:
                shouye_layout1.setVisibility(View.GONE);
                shouye_layout2.setVisibility(View.GONE);
                shouye_layout3.setVisibility(View.GONE);
                shouye_layout4.setVisibility(View.GONE);
                noHint.setVisibility(View.VISIBLE);
                more.setVisibility(View.GONE);
                break;
            case 1:
                noHint.setVisibility(View.GONE);
                more.setVisibility(View.VISIBLE);
                shouye_layout1.setVisibility(View.VISIBLE);
                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(0).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard1);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(0).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard1);
                }
                shouye_name1.setText(providePatientBindingMyDoctorInfos.get(0).getUserName());
                shouye_bind1.setText(providePatientBindingMyDoctorInfos.get(0).getBindingTypeName());
                shouye_hospatal1.setText(providePatientBindingMyDoctorInfos.get(0).getHospitalInfoName());

                shouye_layout2.setVisibility(View.GONE);
                shouye_layout3.setVisibility(View.GONE);
                shouye_layout4.setVisibility(View.GONE);
                break;
            case 2:
                noHint.setVisibility(View.GONE);
                shouye_layout1.setVisibility(View.VISIBLE);
                more.setVisibility(View.VISIBLE);
                shouye_layout2.setVisibility(View.VISIBLE);
                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(0).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard1);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(0).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard1);
                }
                shouye_name1.setText(providePatientBindingMyDoctorInfos.get(0).getUserName());
                shouye_bind1.setText(providePatientBindingMyDoctorInfos.get(0).getBindingTypeName());
                shouye_hospatal1.setText(providePatientBindingMyDoctorInfos.get(0).getHospitalInfoName());

                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(1).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard2);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(1).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard2);
                }
                shouye_name2.setText(providePatientBindingMyDoctorInfos.get(1).getUserName());
                shouye_bind2.setText(providePatientBindingMyDoctorInfos.get(1).getBindingTypeName());
                shouye_hospatal2.setText(providePatientBindingMyDoctorInfos.get(1).getHospitalInfoName());
                shouye_layout3.setVisibility(View.GONE);
                shouye_layout4.setVisibility(View.GONE);
                break;
            case 3:
                noHint.setVisibility(View.GONE);
                more.setVisibility(View.VISIBLE);
                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(0).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard1);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(0).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard1);
                }
                shouye_name1.setText(providePatientBindingMyDoctorInfos.get(0).getUserName());
                shouye_bind1.setText(providePatientBindingMyDoctorInfos.get(0).getBindingTypeName());
                shouye_hospatal1.setText(providePatientBindingMyDoctorInfos.get(0).getHospitalInfoName());

                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(1).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard2);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(1).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard2);
                }
                shouye_name2.setText(providePatientBindingMyDoctorInfos.get(1).getUserName());
                shouye_bind2.setText(providePatientBindingMyDoctorInfos.get(1).getBindingTypeName());
                shouye_hospatal2.setText(providePatientBindingMyDoctorInfos.get(1).getHospitalInfoName());


                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(2).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard3);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(2).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard3);
                }
                shouye_name3.setText(providePatientBindingMyDoctorInfos.get(2).getUserName());
                shouye_bind3.setText(providePatientBindingMyDoctorInfos.get(2).getBindingTypeName());
                shouye_hospatal3.setText(providePatientBindingMyDoctorInfos.get(2).getHospitalInfoName());



                shouye_layout1.setVisibility(View.VISIBLE);
                shouye_layout2.setVisibility(View.VISIBLE);
                shouye_layout3.setVisibility(View.VISIBLE);
                shouye_layout4.setVisibility(View.GONE);
                break;


            case 4:
                noHint.setVisibility(View.GONE);
                more.setVisibility(View.VISIBLE);
                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(0).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard1);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(0).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard1);
                }
                shouye_name1.setText(providePatientBindingMyDoctorInfos.get(0).getUserName());
                shouye_bind1.setText(providePatientBindingMyDoctorInfos.get(0).getBindingTypeName());
                shouye_hospatal1.setText(providePatientBindingMyDoctorInfos.get(0).getHospitalInfoName());

                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(1).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard2);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(1).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard2);
                }
                shouye_name2.setText(providePatientBindingMyDoctorInfos.get(1).getUserName());
                shouye_bind2.setText(providePatientBindingMyDoctorInfos.get(1).getBindingTypeName());
                shouye_hospatal2.setText(providePatientBindingMyDoctorInfos.get(1).getHospitalInfoName());


                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(2).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard3);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(2).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard3);
                }
                shouye_name3.setText(providePatientBindingMyDoctorInfos.get(2).getUserName());
                shouye_bind3.setText(providePatientBindingMyDoctorInfos.get(2).getBindingTypeName());
                shouye_hospatal3.setText(providePatientBindingMyDoctorInfos.get(2).getHospitalInfoName());

                try {
                    int avatarResId = Integer.parseInt(providePatientBindingMyDoctorInfos.get(3).getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(shouye_heard4);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(providePatientBindingMyDoctorInfos.get(3).getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(R.mipmap.nhtx)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(shouye_heard4);
                }
                shouye_name4.setText(providePatientBindingMyDoctorInfos.get(3).getUserName());
                shouye_bind4.setText(providePatientBindingMyDoctorInfos.get(3).getBindingTypeName());
                shouye_hospatal4.setText(providePatientBindingMyDoctorInfos.get(3).getHospitalInfoName());



                shouye_layout1.setVisibility(View.VISIBLE);
                shouye_layout2.setVisibility(View.VISIBLE);
                shouye_layout3.setVisibility(View.VISIBLE);
                shouye_layout4.setVisibility(View.VISIBLE);
                break;

        }
    }

    /**
     * 获取我的医生数据
     */
    private void searchIndexMyDoctorShow() {
        ProvidePatientBindingMyDoctorInfo providePatientBindingMyDoctorInfo = new ProvidePatientBindingMyDoctorInfo();
        providePatientBindingMyDoctorInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        providePatientBindingMyDoctorInfo.setRequestClientType("1");
        providePatientBindingMyDoctorInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        providePatientBindingMyDoctorInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        providePatientBindingMyDoctorInfo.setShowNum("4");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(providePatientBindingMyDoctorInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"PatientMyDoctorControlle/searchIndexMyDoctorShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(6);
            }
        }.start();
    }

    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        noHint = (LinearLayout)view.findViewById(R.id.noHint);
        noHint.setOnClickListener(new ButtonClick());
        shouye_layout1 = (LinearLayout)view.findViewById(R.id.shouye_layout1);
        shouye_layout2 = (LinearLayout)view.findViewById(R.id.shouye_layout2);
        shouye_layout3 = (LinearLayout)view.findViewById(R.id.shouye_layout3);
        shouye_layout4 = (LinearLayout)view.findViewById(R.id.shouye_layout4);

        shouye_heard1 = (ImageView) view.findViewById(R.id.shouye_heard1);
        shouye_heard2 = (ImageView)view.findViewById(R.id.shouye_heard2);
        shouye_heard3 = (ImageView)view.findViewById(R.id.shouye_heard3);
        shouye_heard4 = (ImageView)view.findViewById(R.id.shouye_heard4);

        shouye_name1 = (TextView) view.findViewById(R.id.shouye_name1);
        shouye_name2 = (TextView)view.findViewById(R.id.shouye_name2);
        shouye_name3 = (TextView)view.findViewById(R.id.shouye_name3);
        shouye_name4 = (TextView)view.findViewById(R.id.shouye_name4);

        shouye_bind1 = (TextView) view.findViewById(R.id.shouye_bind1);
        shouye_bind2 = (TextView)view.findViewById(R.id.shouye_bind2);
        shouye_bind3 = (TextView)view.findViewById(R.id.shouye_bind3);
        shouye_bind4 = (TextView)view.findViewById(R.id.shouye_bind4);


        shouye_hospatal1 = (TextView) view.findViewById(R.id.shouye_hospatal1);
        shouye_hospatal2 = (TextView)view.findViewById(R.id.shouye_hospatal2);
        shouye_hospatal3 = (TextView)view.findViewById(R.id.shouye_hospatal3);
        shouye_hospatal4 = (TextView)view.findViewById(R.id.shouye_hospatal4);

        more = (TextView)view.findViewById(R.id.more);
        more.setOnClickListener(new ButtonClick());

    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 6:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1 &&  netRetEntity.getResJsonData() != null && !"".equals(netRetEntity.getResJsonData()))
                            providePatientBindingMyDoctorInfos = JSON.parseArray(netRetEntity.getResJsonData(),ProvidePatientBindingMyDoctorInfo.class);
                        showMyDoctorInfo();

                        break;
                }
            }
        };
    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_fragmentHZGL_qb:
                    cutDefault();
                    mQBCut.setVisibility(View.VISIBLE);
                    mState = 0;
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.li_fragmentHZGL_yj:
                    cutDefault();
                    mYJCut.setVisibility(View.VISIBLE);
                    mState = 1;
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.li_fragmentHZGL_tx:
                    cutDefault();
                    mTXCut.setVisibility(View.VISIBLE);
                    mState = 2;
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.li_fragmentHZGL_zc:
                    cutDefault();
                    mZCCut.setVisibility(View.VISIBLE);
                    mState = 3;
                    mHandler.sendEmptyMessage(1);
                    break;
                case R.id.tv_fragmentHZGL_ss:
                    Intent intent = new Intent();
                    intent.setClass(mContext,HZGLSearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.more:
                    intent = new Intent();
                    intent.setClass(mContext,WDYSActivity.class);
                    startActivity(intent);
                    break;
                case R.id.noHint:
                    intent = new Intent();
                    intent.setClass(mContext,WDYSActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }

    private void cutDefault(){
        mQBCut.setVisibility(View.GONE);
        mYJCut.setVisibility(View.GONE);
        mTXCut.setVisibility(View.GONE);
        mZCCut.setVisibility(View.GONE);
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
                hzIfno.setState(1);
            }
            if (i%3 == 1)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(-1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(2);
            }
            if (i%3 == 2)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(3);
            }
            mHZEntyties.add(hzIfno);
        }
        mHZGLRecycleAdapter.setDate(mHZEntyties);
        mHZGLRecycleAdapter.notifyDataSetChanged();
    }
}
