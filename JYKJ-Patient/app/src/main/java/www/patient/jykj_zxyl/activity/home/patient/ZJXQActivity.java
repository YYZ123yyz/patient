package www.patient.jykj_zxyl.activity.home.patient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.ProvideViewInteractPatientComment;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.activity.home.twjz.WZXXActivity;
import www.patient.jykj_zxyl.activity.home.twjz.WZXX_YSPBActivity;
import www.patient.jykj_zxyl.adapter.DoctorCommentsRecycleAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.twjz.WZXXActivity;
import www.patient.jykj_zxyl.activity.home.twjz.WZXX_YSPBActivity;
import www.patient.jykj_zxyl.adapter.DoctorCommentsRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ProvincePicker;

/**
 * 专家详情
 */
public class ZJXQActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DoctorCommentsRecycleAdapter mAdapter;
    private LinearLayout llBack;

    public          ProgressDialog              mDialogProgress =null;

    private         Context                     mContext;                                       //
    private ZJXQActivity mActivity;
    private         JYKJApplication             mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private         String                      mNetRetPLStr;                 //返回字符串
    private         Handler                     mHandler;

    private         LinearLayout                mChoiceRegion;              //选择区域
    private         TextView                    mChoiceRegionText;                  //选择的地区

    private         LinearLayout                mChoiceHospital;                //选择医院
    private         TextView                    mChoiceHospitalText;                  //选择的地区

    private         EditText                    mSearchEditText;                        //输入框

    private         ProvincePicker              mPicker;                                            //省市县三级联动选择框
    public          Map<String,ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                  //用户选择的省市区map
    private                 int                           mChoiceRegionLevel;                                       //选择的区域级别
    private                 String                      mChoiceRegionID;                                       //选择的区域ID

    private                 List<ProvideHospitalInfo>   mProvideHospitalInfos = new ArrayList<>();              //获取到的医院列表
    private                 String[]                    mProvideHospitalNameInfos;                              //医院对应的名称列表

    private                 int                             mChoiceHospitalIndex;           //选择的医院下标

    private                 TextView                    mSearchTextView;                    //搜索

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条
    private                 int                         mSearchModel;                   //搜索模式  1=按条件搜索  其他=扫码搜索
    private                 List<ProvideViewUnionDoctorDetailInfo>          mUnionList = new ArrayList<>();

    private                 boolean                     loadDate = true;               //是否加载数据

    private             List<ProvideViewDoctorExpertRecommend> provideViewDoctorExpertRecommendList = new ArrayList<>();            //获取到的专家数据

    private                 ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend;
    private                 List<ProvideViewInteractPatientComment> provideViewInteractPatientComments = new ArrayList<>();

    private                 ImageView               mUserHead;                  //用户头像
    private                 TextView                mUserName;                  //用户名
    private                 TextView                mTitleName;                 //用户职称、科室
    private                 TextView                mHospital;                  //医院
    private                 TextView                mCollectNum;                //收藏
    private                 TextView                mThumbsUpNum;               //点赞
    private                 LinearLayout            mTWJZLayout;                //图文就诊布局
    private                 LinearLayout            mDHJZLayout;                //电话就诊布局
    private                 LinearLayout            mYPJZLayout;                //音频就诊布局
    private                 LinearLayout            mSPJZLayout;                //视频就诊布局
    private                 LinearLayout            mQYJZLayout;                //签约就诊布局
//
//    private                 ImageView               mTWJZImageView;             //图文就诊
//    private                 ImageView               mDHJZImageView;             //电话就诊
//    private                 ImageView               mYPJZImageView;             //音频就诊
//    private                 ImageView               mSPJZImageView;             //视频就诊
//    private                 ImageView               mQYJZImageView;             //签约就诊
//
//    private                 TextView                mTWJZTitleText;                  //图文就诊
//    private                 TextView                mDHJZTitleText;                  //电话就诊
//    private                 TextView                mYPJZTitleText;                  //音频就诊
//    private                 TextView                mSPJZTitleText;                  //视频就诊
//    private                 TextView                mQYJZTitleText;                  //签约就诊
//
//    private                 TextView                mTWJZPriceText;                  //图文就诊价格
//    private                 TextView                mDHJZPriceText;                  //电话就诊价格
//    private                 TextView                mYPJZPriceText;                  //音频就诊价格
//    private                 TextView                mSPJZPriceText;                  //视频就诊价格
//    private                 TextView                mQYJZPriceText;                  //签约就诊价格

    private                 TextView                mSynopsis;                         //医生介绍

    private                 TextView                mSchedulingInfo;                    //排版信息

    public TextView         imgTextPrice;                          //
    public TextView         imgTextSumNum;                          //
    public TextView         phonePriceStr;                          //
    public TextView         phoneSumNum;                          //
    public TextView         audioPrice;                          //
    public TextView         audioSumNum;                          //
    public TextView         videoPrice;                          //
    public TextView         videoSumNum;                          //
    public TextView         signingPrice;                          //
    public TextView         signingSumNum;                          //

    public ImageView        mImgTWJZ;                         //图文就诊图标
    public ImageView        mImgDHJZ;                         //电话就诊图标
    public ImageView        mImgYYJZ;                         //语音就诊图标
    public ImageView        mImgSPJZ;                         //视频就诊图标
    public ImageView        mImgQYJZ;                         //签约就诊图标

    private                 TextView                            tv_nopl;                //暂无评论
    private                 TextView                               tv_bd   ;                //绑定
    private                 TextView                            tv_ybd;                 //已绑定

    private                 ImageView                           iv_dz;          //点赞
    private                 ImageView                           iv_sc;          //收藏

    private void initView() {
        iv_dz = (ImageView)this.findViewById(R.id.iv_dz);
        iv_sc = (ImageView)this.findViewById(R.id.iv_sc);
        iv_dz.setOnClickListener(new ButtonClick());
        iv_sc.setOnClickListener(new ButtonClick());

        tv_nopl = (TextView)this.findViewById(R.id.tv_nopl);
        tv_bd = (TextView)this.findViewById(R.id.tv_bd);
        tv_bd.setOnClickListener(new ButtonClick());
        tv_ybd = (TextView)this.findViewById(R.id.tv_ybd);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_pj);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setNestedScrollingEnabled(false); //禁止滑动
        mAdapter = new DoctorCommentsRecycleAdapter(provideViewInteractPatientComments,mContext);
        mRecyclerView.setAdapter(mAdapter);
        llBack.setOnClickListener(new ButtonClick());

        mUserHead = (ImageView)this.findViewById(R.id.imageView);
        mUserName = (TextView)this.findViewById(R.id.userName);
         mTitleName = (TextView)this.findViewById(R.id.userTitle);
         mHospital = (TextView)this.findViewById(R.id.hospatal);
         mCollectNum = (TextView)this.findViewById(R.id.collectNum);
        mThumbsUpNum = (TextView)this.findViewById(R.id.thumbsUpNum);

        mTWJZLayout = (LinearLayout)this.findViewById(R.id.twjz_layout);
        mDHJZLayout = (LinearLayout)this.findViewById(R.id.dhjz_layout);
        mYPJZLayout = (LinearLayout)this.findViewById(R.id.ypjz_layout);
        mSPJZLayout = (LinearLayout)this.findViewById(R.id.spjz_layout);
        mQYJZLayout = (LinearLayout)this.findViewById(R.id.qyjz_layout);


        mSynopsis = (TextView)this.findViewById(R.id.synopsis);
        mSchedulingInfo = (TextView)this.findViewById(R.id.schedulingInfo);

        mTWJZLayout.setOnClickListener(new ButtonClick());
        mDHJZLayout.setOnClickListener(new ButtonClick());
        mYPJZLayout.setOnClickListener(new ButtonClick());
        mSPJZLayout.setOnClickListener(new ButtonClick());
        mQYJZLayout.setOnClickListener(new ButtonClick());

        imgTextPrice = (TextView)this.findViewById(R.id.imgTextPrice);
        imgTextSumNum = (TextView)this.findViewById(R.id.imgTextSumNum);
        phonePriceStr = (TextView)this.findViewById(R.id.phonePriceStr);
        phoneSumNum = (TextView)this.findViewById(R.id.phoneSumNum);
        audioPrice = (TextView)this.findViewById(R.id.audioPrice);
        audioSumNum = (TextView)this.findViewById(R.id.audioSumNum);
        videoPrice = (TextView)this.findViewById(R.id.videoPrice);
        videoSumNum = (TextView)this.findViewById(R.id.videoSumNum);
        signingPrice = (TextView)this.findViewById(R.id.signingPrice);
        signingSumNum = (TextView)this.findViewById(R.id.signingSumNum);

        mImgTWJZ = (ImageView)this.findViewById(R.id.img_tvjz);
        mImgDHJZ = (ImageView)this.findViewById(R.id.img_dhjz);
        mImgYYJZ = (ImageView)this.findViewById(R.id.img_yyjz);
        mImgSPJZ = (ImageView)this.findViewById(R.id.img_spjz);
        mImgQYJZ = (ImageView)this.findViewById(R.id.img_qyjz);

    }

    /**
     * 展示推荐专家信息
     */
    private void showDoctorExpertRecommend() {
        try {
            int avatarResId = Integer.parseInt(provideViewDoctorExpertRecommend.getUserLogoUrl());
            Glide.with(mContext).load(avatarResId).into(mUserHead);
        } catch (Exception e) {
            //use default avatar
            Glide.with(mContext).load(provideViewDoctorExpertRecommend.getUserLogoUrl())
                    .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mUserHead);
        }

        if (provideViewDoctorExpertRecommend.getUserName() == null || "".equals(provideViewDoctorExpertRecommend.getUserName()))
            mUserName.setText("未设置");
        else
            mUserName.setText(provideViewDoctorExpertRecommend.getUserName());
        if (provideViewDoctorExpertRecommend.getDoctorTitleName() ==null || "".equals(provideViewDoctorExpertRecommend.getDoctorTitle()))
            mTitleName.setText("未设置");
        else
            mTitleName.setText(provideViewDoctorExpertRecommend.getDoctorTitleName()+"|"+provideViewDoctorExpertRecommend.getDepartmentSecondName());
        if (provideViewDoctorExpertRecommend.getHospitalInfoName() ==null || "".equals(provideViewDoctorExpertRecommend.getHospitalInfoName()))
            mHospital.setText("未设置");
        else
            mHospital.setText(provideViewDoctorExpertRecommend.getHospitalInfoName());
        if (provideViewDoctorExpertRecommend.getCollectNum() == null)
            mCollectNum.setText("0");
        else
            mCollectNum.setText(provideViewDoctorExpertRecommend.getCollectNum()+"");
        if (provideViewDoctorExpertRecommend.getThumbsUpNum() == null)
            mThumbsUpNum.setText("0");
        else
            mThumbsUpNum.setText(provideViewDoctorExpertRecommend.getThumbsUpNum()+"");

        if (provideViewDoctorExpertRecommend.getImgTextPriceShow() == null  || "".equals(provideViewDoctorExpertRecommend.getImgTextPriceShow()))
            imgTextPrice.setText("未设置");
        else
            imgTextPrice.setText("￥"+provideViewDoctorExpertRecommend.getImgTextPriceShow());
        if (provideViewDoctorExpertRecommend.getImgTextSumNum() == null  || "".equals(provideViewDoctorExpertRecommend.getImgTextSumNum()))
            imgTextSumNum.setText("未设置");
        else
            imgTextSumNum.setText(provideViewDoctorExpertRecommend.getImgTextSumNum()+"人咨询");
        if (provideViewDoctorExpertRecommend.getPhonePriceShow() == null  || "".equals(provideViewDoctorExpertRecommend.getPhonePriceShow()))
            phonePriceStr.setText("未设置");
        else
            phonePriceStr.setText("￥"+provideViewDoctorExpertRecommend.getPhonePriceShow());
        if (provideViewDoctorExpertRecommend.getPhonePrice() == null  || "".equals(provideViewDoctorExpertRecommend.getPhonePrice()))
            phoneSumNum.setText("未设置");
        else
            phoneSumNum.setText(provideViewDoctorExpertRecommend.getPhonePrice()+"人咨询");
        if (provideViewDoctorExpertRecommend.getAudioPriceShow() == null  || "".equals(provideViewDoctorExpertRecommend.getAudioPriceShow()))
            audioPrice.setText("未设置");
        else
            audioPrice.setText("￥"+provideViewDoctorExpertRecommend.getAudioPriceShow());
        if (provideViewDoctorExpertRecommend.getAudioSumNum() == null  || "".equals(provideViewDoctorExpertRecommend.getAudioSumNum()))
            audioSumNum.setText("未设置");
        else
            audioSumNum.setText(provideViewDoctorExpertRecommend.getAudioSumNum()+"人咨询");
        if (provideViewDoctorExpertRecommend.getVideoPriceShow() == null  || "".equals(provideViewDoctorExpertRecommend.getVideoPriceShow()))
            videoPrice.setText("未设置");
        else
            videoPrice.setText("￥"+provideViewDoctorExpertRecommend.getVideoPriceShow());
        if (provideViewDoctorExpertRecommend.getVideoSumNum() == null  || "".equals(provideViewDoctorExpertRecommend.getVideoSumNum()))
            videoSumNum.setText("未设置");
        else
            videoSumNum.setText(provideViewDoctorExpertRecommend.getVideoSumNum()+"人咨询");
        if (provideViewDoctorExpertRecommend.getSigningPriceShow() == null  || "".equals(provideViewDoctorExpertRecommend.getSigningPriceShow()))
            signingPrice.setText("未设置");
        else
            signingPrice.setText("￥"+provideViewDoctorExpertRecommend.getSigningPriceShow());
        if (provideViewDoctorExpertRecommend.getSigningSumNum() == null  || "".equals(provideViewDoctorExpertRecommend.getSigningSumNum()))
            signingSumNum.setText("未设置");
        else
            signingSumNum.setText(provideViewDoctorExpertRecommend.getSigningSumNum()+"人咨询");
        if (provideViewDoctorExpertRecommend.getFlagBindingState() == 1)
        {
            tv_ybd.setVisibility(View.VISIBLE);
            tv_bd.setVisibility(View.GONE);
        }
        else
        {
            tv_ybd.setVisibility(View.GONE);
            tv_bd.setVisibility(View.VISIBLE);
        }

        if (provideViewDoctorExpertRecommend.getFlagThumbsUpState() == 1)
        {
            iv_dz.setBackgroundResource(R.mipmap.dianzan);
        }
        else
        {
            iv_dz.setBackgroundResource(R.mipmap.dz);
        }

        if (provideViewDoctorExpertRecommend.getFlagCollectState() == 1)
        {
            iv_sc.setBackgroundResource(R.mipmap.sc);
        }
        else
        {
            iv_sc.setBackgroundResource(R.mipmap.sc_no);
        }


        if (provideViewDoctorExpertRecommend.getFlagImgText() != null && provideViewDoctorExpertRecommend.getFlagImgText() == 1)
            mImgTWJZ.setBackgroundResource(R.mipmap.tw_p);
        else
            mImgTWJZ.setBackgroundResource(R.mipmap.tw_d);

        if (provideViewDoctorExpertRecommend.getFlagPhone() != null && provideViewDoctorExpertRecommend.getFlagPhone() == 1)
            mImgDHJZ.setBackgroundResource(R.mipmap.dh_p);
        else
            mImgDHJZ.setBackgroundResource(R.mipmap.dh_d);

        if (provideViewDoctorExpertRecommend.getFlagAudio() != null && provideViewDoctorExpertRecommend.getFlagAudio() == 1)
            mImgYYJZ.setBackgroundResource(R.mipmap.yp_p);
        else
            mImgYYJZ.setBackgroundResource(R.mipmap.yp_d);

        if (provideViewDoctorExpertRecommend.getFlagVideo() != null && provideViewDoctorExpertRecommend.getFlagVideo() == 1)
            mImgSPJZ.setBackgroundResource(R.mipmap.sp_p);
        else
            mImgSPJZ.setBackgroundResource(R.mipmap.sp_d);

        if (provideViewDoctorExpertRecommend.getFlagSigning() != null && provideViewDoctorExpertRecommend.getFlagSigning() == 1)
            mImgQYJZ.setBackgroundResource(R.mipmap.qy_p);
        else
            mImgQYJZ.setBackgroundResource(R.mipmap.qy_d);


        if (provideViewDoctorExpertRecommend.getSynopsis() == null || "".equals(provideViewDoctorExpertRecommend.getSynopsis()))
            mSynopsis.setText("未设置");
        else
            mSynopsis.setText(provideViewDoctorExpertRecommend.getSynopsis());

        if (provideViewDoctorExpertRecommend.getSchedulingInfo() == null || "".equals(provideViewDoctorExpertRecommend.getSchedulingInfo()))
            mSchedulingInfo.setText("未设置");
        else
            mSchedulingInfo.setText(provideViewDoctorExpertRecommend.getSchedulingInfo());

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjzj_zjxq);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initView();
        initHandler();
        provideViewDoctorExpertRecommend = (ProvideViewDoctorExpertRecommend) getIntent().getSerializableExtra("provideViewDoctorExpertRecommend");
//        //获取所有联盟
//        mNumPage = 1;
//        mSearchModel = 1;
//        mUnionList.removeAll(mUnionList);
        getDate();
        getPLDate();
    }



    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 5:
                        cacerProgress();
                        if (mNetRetStr == null || "".equals(mNetRetStr))
                        {

                            Toast.makeText(mContext,"网络故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {

                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                           provideViewDoctorExpertRecommend = JSON.parseObject(netRetEntity.getResJsonData(),ProvideViewDoctorExpertRecommend.class);

                        }
                        showDoctorExpertRecommend();
                        break;

                    case 10:
                        if (mNetRetPLStr == null || "".equals(mNetRetPLStr))
                        {

                            Toast.makeText(mContext,"网络故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        netRetEntity = JSON.parseObject(mNetRetPLStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {

                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            loadDate = false;
                        }
                        else
                        {
                            List<ProvideViewInteractPatientComment> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewInteractPatientComment.class);
                            if (list == null || list.size() < mRowNum)
                                loadDate = false;
                            if(list != null)
                                provideViewInteractPatientComments.addAll(list);

                        }
                        if (provideViewInteractPatientComments.size() == 0)
                            tv_nopl.setVisibility(View.VISIBLE);
                        else
                            tv_nopl.setVisibility(View.GONE);
                        showDoctorCommentsRecommend();
                        break;

                    case 0:
                        cacerProgress();
                        provideViewDoctorExpertRecommendList.clear();
                        getDate();
//                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
//                        if (retEntity.getResCode() == 0)
//                        {
//                            Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        mAdapter.setDate(provideViewDoctorExpertRecommendList);
//                        mAdapter.notifyDataSetChanged();
                        break;
                    case 11:
                        cacerProgress();
                        if (provideViewDoctorExpertRecommend.getFlagThumbsUpState() == 0)
                        {
                            provideViewDoctorExpertRecommend.setFlagThumbsUpState(1);
                            iv_dz.setBackgroundResource(R.mipmap.dianzan);
                            provideViewDoctorExpertRecommend.setThumbsUpNum(provideViewDoctorExpertRecommend.getThumbsUpNum()+1);
                            mThumbsUpNum.setText(provideViewDoctorExpertRecommend.getThumbsUpNum()+"");

                        }

                        else
                        {
                            provideViewDoctorExpertRecommend.setFlagThumbsUpState(0);
                            iv_dz.setBackgroundResource(R.mipmap.dz);
                            provideViewDoctorExpertRecommend.setThumbsUpNum(provideViewDoctorExpertRecommend.getThumbsUpNum()-1);
                            mThumbsUpNum.setText(provideViewDoctorExpertRecommend.getThumbsUpNum()+"");
                        }

                        break;

                    case 12:
                        cacerProgress();
                        if (provideViewDoctorExpertRecommend.getFlagCollectState() == 0)
                        {
                            provideViewDoctorExpertRecommend.setFlagCollectState(1);
                            iv_sc.setBackgroundResource(R.mipmap.sc);
                            provideViewDoctorExpertRecommend.setCollectNum(provideViewDoctorExpertRecommend.getCollectNum()+1);
                            mCollectNum.setText(provideViewDoctorExpertRecommend.getCollectNum()+"");

                        }

                        else
                        {
                            provideViewDoctorExpertRecommend.setFlagCollectState(0);
                            iv_sc.setBackgroundResource(R.mipmap.sc_no);
                            provideViewDoctorExpertRecommend.setCollectNum(provideViewDoctorExpertRecommend.getCollectNum()-1);
                            mCollectNum.setText(provideViewDoctorExpertRecommend.getCollectNum()+"");
                        }

                        break;
                }
            }
        };
    }

    /**
     * 展示评论
     */
    private void showDoctorCommentsRecommend() {
        mAdapter.setDate(provideViewInteractPatientComments);
        mAdapter.notifyDataSetChanged();
    }


    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;

                case R.id.twjz_layout:
                    if (provideViewDoctorExpertRecommend.getFlagImgText() == 0)
                    {
                        Toast.makeText(mContext,"图文就诊未开通",Toast.LENGTH_SHORT).show();
                        return;
                    }
                   //图文就诊
                    startActivity(new Intent(mContext,WZXXActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend).putExtra("operaType","1"));
                    break;
                    //电话就诊
                case R.id.dhjz_layout:
                    if (provideViewDoctorExpertRecommend.getFlagAudio() == 0)
                    {
                        Toast.makeText(mContext,"电话就诊未开通",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(new Intent(mContext,WZXX_YSPBActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend).putExtra("operaType","5"));
                    break;

                case R.id.ypjz_layout:
                    if (provideViewDoctorExpertRecommend.getFlagAudio() == 0)
                    {
                        Toast.makeText(mContext,"音频就诊未开通",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(new Intent(mContext,WZXX_YSPBActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend).putExtra("operaType","2"));

                    break;
                case R.id.spjz_layout:
                    if (provideViewDoctorExpertRecommend.getFlagVideo() == 0)
                    {
                        Toast.makeText(mContext,"视频就诊未开通",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(new Intent(mContext,WZXX_YSPBActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend).putExtra("operaType","3"));

                    break;
                case R.id.qyjz_layout:
                    if (provideViewDoctorExpertRecommend.getFlagSigning() == 0)
                    {
                        Toast.makeText(mContext,"签约就诊未开通",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(new Intent(mContext,WZXXActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend).putExtra("operaType","4"));

                    break;
                case R.id.iv_dz:
                    //点赞
                    operIndexExpertRecommendDoctorThumbsUp();
                    break;
                case R.id.iv_sc:
                    //收藏
                    operIndexExpertRecommendDoctorCollect();
                    break;
                case R.id.tv_bd:
                    startActivity(new Intent(mContext,ZJXQ_ZJBDActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend));
                    break;

            }
        }
    }

    /**
     * 点赞或者取消点赞
     */
    private void operIndexExpertRecommendDoctorThumbsUp() {
        getProgressBar("请稍候","正在点赞");
        ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend1 = new ProvideViewDoctorExpertRecommend();
        provideViewDoctorExpertRecommend1.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewDoctorExpertRecommend1.setRequestClientType("1");
        provideViewDoctorExpertRecommend1.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewDoctorExpertRecommend1.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        if (provideViewDoctorExpertRecommend.getFlagThumbsUpState() == 0)
            provideViewDoctorExpertRecommend1.setOperThumbsUpType("1");
        else
            provideViewDoctorExpertRecommend1.setOperThumbsUpType("2");
        provideViewDoctorExpertRecommend1.setThumbsUpDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideViewDoctorExpertRecommend1);
                    String urlStr = Constant.SERVICEURL+"patientSearchDoctorControlle/operIndexExpertRecommendDoctorThumbsUp";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetPLStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"patientSearchDoctorControlle/operIndexExpertRecommendDoctorThumbsUp");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetPLStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(11);
            }
        }.start();
    }

    /**
     * 收藏或者取消收藏(接口应该是点赞操作，写反了)
     */
    private void operIndexExpertRecommendDoctorCollect() {
        getProgressBar("请稍候","正在收藏");
        ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend1 = new ProvideViewDoctorExpertRecommend();
        provideViewDoctorExpertRecommend1.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewDoctorExpertRecommend1.setRequestClientType("1");
        provideViewDoctorExpertRecommend1.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewDoctorExpertRecommend1.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        if (provideViewDoctorExpertRecommend.getFlagCollectState() == 0)
            provideViewDoctorExpertRecommend1.setOperCollectType("1");
        else
            provideViewDoctorExpertRecommend1.setOperCollectType("2");
        provideViewDoctorExpertRecommend1.setCollectDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideViewDoctorExpertRecommend1);
                    String urlStr = Constant.SERVICEURL+"patientSearchDoctorControlle/operIndexExpertRecommendDoctorCollect";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetPLStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"patientSearchDoctorControlle/operIndexExpertRecommendDoctorCollect");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetPLStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(12);
            }
        }.start();
    }


    /**
     * 获取评论
     */
    private void getPLDate() {
        ProvideViewInteractPatientComment provideViewInteractPatientComment = new ProvideViewInteractPatientComment();
        provideViewInteractPatientComment.setRowNum(mRowNum+"");
        provideViewInteractPatientComment.setPageNum(mNumPage+"");
        provideViewInteractPatientComment.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewInteractPatientComment.setRequestClientType("1");
        provideViewInteractPatientComment.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewInteractPatientComment.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideViewInteractPatientComment.setSearchDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
//        provideViewDoctorExpertRecommend.setShowNum("4");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideViewInteractPatientComment);
                    String urlStr = Constant.SERVICEURL+"patientSearchDoctorControlle/searchIndexExpertRecommendDoctorCommentShow";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetPLStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"patientSearchDoctorControlle/searchIndexExpertRecommendDoctorCommentShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetPLStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(10);
            }
        }.start();
    }


    /**
     * 获取专家详情
     */
    private void getDate() {
        getProgressBar("请稍候","正在获取数据");
        provideViewDoctorExpertRecommend.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewDoctorExpertRecommend.setRequestClientType("1");
        provideViewDoctorExpertRecommend.setSearchDoctorCode(provideViewDoctorExpertRecommend.getPatientCode());
        provideViewDoctorExpertRecommend.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewDoctorExpertRecommend.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
//        provideViewDoctorExpertRecommend.setShowNum("4");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideViewDoctorExpertRecommend);
                    String urlStr = Constant.SERVICEURL+"patientSearchDoctorControlle/searchIndexExpertRecommendDoctorMoreShow";
//                    mNetRetStr = HttpNetService.getUpgradeInfo("jsonDataInfo="+string, urlStr);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"patientSearchDoctorControlle/searchIndexExpertRecommendDoctorDetailShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    /**
     * 显示医院
     */
    private void showChoiceHospitalText(int index) {
        mChoiceHospitalText.setText(mProvideHospitalInfos.get(index).getHospitalName());
        provideViewDoctorExpertRecommend.setSearchHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
        //获取一级科室

    }

    /**
     * 选择医院对话框
     *
     */
    private void showChoiceHospitalView() {
        if (mProvideHospitalInfos != null)
        {
            mProvideHospitalNameInfos = new String[mProvideHospitalInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalInfos.size(); i++)
        {
            mProvideHospitalNameInfos[i] = mProvideHospitalInfos.get(i).getHospitalName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择医院");
        listDialog.setItems(mProvideHospitalNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalText(which);
            }
        });
        listDialog.show();
    }


    /**
     * 设置所在地区显示
     */
    public void setRegionText() {
        provideViewDoctorExpertRecommend.setProvince(mChoiceRegionMap.get("provice").getRegion_id());
        if ("sqb".equals(mChoiceRegionMap.get("city").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name());
            mChoiceRegionLevel = 1;               //市级所有，则是省级
            mChoiceRegionID = mChoiceRegionMap.get("provice").getRegion_id();
            provideViewDoctorExpertRecommend.setCity("");
            provideViewDoctorExpertRecommend.setArea("");
        }
        else if (mChoiceRegionMap.get("dist") == null ||"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
            provideViewDoctorExpertRecommend.setArea("");
        }
        if (!"sqb".equals(mChoiceRegionMap.get("city").getRegion_id()))
        {
            provideViewDoctorExpertRecommend.setCity(mChoiceRegionMap.get("city").getRegion_id());
            mChoiceRegionLevel = 2;               //市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
        }
        if (mChoiceRegionMap.get("dist") != null && !"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name()+mChoiceRegionMap.get("dist").getRegion_name());
            provideViewDoctorExpertRecommend.setArea(mChoiceRegionMap.get("dist").getRegion_id());
            mChoiceRegionLevel = 3;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("dist").getRegion_id();
        }
        //初始化医院
        mChoiceHospitalText.setText("请选择医院");
        provideViewDoctorExpertRecommend.setSearchHospitalInfoCode("");

        //获取区域内医院
        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setRegion_level(mChoiceRegionLevel);
                    provideBasicsRegion.setRegion_id(mChoiceRegionID);
                    String str = new Gson().toJson(provideBasicsRegion);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideBasicsRegion),Constant.SERVICEURL+"hospitalDataController/getHospitalInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取医院信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //医院数据获取成功
                    mProvideHospitalInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalInfo>>(){}.getType());

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }



}
