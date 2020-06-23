package www.patient.jykj_zxyl.activity.home.jyzl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import entity.HZIfno;
import entity.ProvideBasicsImg;
import entity.mySelf.conditions.QueryBasicImgCond;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;
import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.Util;

/**
 * 就诊总览==》个人总览==>个人状况==>既往病史==详情
 */
public class JWBSDetailActivity extends AppCompatActivity {


    private         Context                 mContext;
    private         TWJZ_CFQActivity        mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private         List<HZIfno>            mHZEntyties = new ArrayList<>();            //所有数据
    private         LinearLayout            mJBXX;                                  //基本信息
    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串
    private         String                  mPatientCode;
    private         int                     mRowNum = 10;                            //每页行数
    private         int                     mPageNum = 1;                           //页数
    private          int                    recordTypeId = 0;                           //搜索的记录类型
    private         TextView                mMore;

    private         LinearLayout            mQB;                                //选择全部
    private         LinearLayout            mJWBS;                                //选择既往病史
    private         LinearLayout            mBCJL;                                //选择病程记录
    private RelativeLayout                  mBack;
    private         ProvidePatientConditionDiseaseRecord mProvidePatientConditionDiseaseRecords = new ProvidePatientConditionDiseaseRecord();

    private PatientJWBSAdapter mPatientJWBSAdapter;

    private PopupWindow mPopuWindow;

    private         TextView                mBCBM;                      //病程别名
    private         TextView                mJZRQ;                      //就诊日期
    private         TextView                mBCFL;                      //病程分类
    private         TextView                mBQZS;                      //病情自述

    private         ImageView               mImageView01;
    private         ImageView               mImageView02;
    private         ImageView               mImageView03;
    private         ImageView               mImageView04;
    private         ImageView               mImageView05;

    private LoadImgTask loadImgTask;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl_grzl_grzk_jwbsdetail);
        mProvidePatientConditionDiseaseRecords = (ProvidePatientConditionDiseaseRecord) getIntent().getSerializableExtra("patientInfo");
        mContext = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();

    }

    private void initLayout()  {
        mBack = (RelativeLayout)this.findViewById(R.id.back);
        mBack.setOnClickListener(new ButtonClick());
        mBCBM = (TextView)this.findViewById(R.id.tv_activityHZZL_userSG);
        mJZRQ = (TextView)this.findViewById(R.id.tv_activityHZZL_userYW);
        mBCFL = (TextView)this.findViewById(R.id.tv_activityHZZL_userTZ);
        mBQZS = (TextView)this.findViewById(R.id.tv_activityHZZL_region);
        mBCBM.setText(mProvidePatientConditionDiseaseRecords.getRecordName());
        mJZRQ.setText(Util.dateToStrDate(mProvidePatientConditionDiseaseRecords.getTreatmentDate()));
        mBCFL.setText(mProvidePatientConditionDiseaseRecords.getRecordTypeName());
        mBQZS.setText(mProvidePatientConditionDiseaseRecords.getRecordContent());

        mImageView01 = (ImageView)this.findViewById(R.id.image01);
        mImageView02 = (ImageView)this.findViewById(R.id.image02);
        mImageView03 = (ImageView)this.findViewById(R.id.image03);
        mImageView04 = (ImageView)this.findViewById(R.id.image04);
        mImageView05 = (ImageView)this.findViewById(R.id.image05);
        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl1() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl1()))
        {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl1());
                Glide.with(mContext).load(avatarResId).into(mImageView01);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl1())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView01);
            }
        }

        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl2() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl2()))
        {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl2());
                Glide.with(mContext).load(avatarResId).into(mImageView02);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl2())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView02);
            }
        }

        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl3() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl3()))
        {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl3());
                Glide.with(mContext).load(avatarResId).into(mImageView03);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl3())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView03);
            }
        }

        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl4() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl4()))
        {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl4());
                Glide.with(mContext).load(avatarResId).into(mImageView04);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl4())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView04);
            }
        }

        if (mProvidePatientConditionDiseaseRecords.getRecordImgUrl5() != null && !"".equals(mProvidePatientConditionDiseaseRecords.getRecordImgUrl5()))
        {
            try {
                int avatarResId = Integer.parseInt(mProvidePatientConditionDiseaseRecords.getRecordImgUrl5());
                Glide.with(mContext).load(avatarResId).into(mImageView05);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvidePatientConditionDiseaseRecords.getRecordImgUrl5())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mImageView05);
            }
        }
        loadimgs();
    }

    void loadimgs(){
        if(StrUtils.defaultStr(mProvidePatientConditionDiseaseRecords.getImgCode()).length()>0) {
            getProgressBar("加载数据","数据加载中...");
            QueryBasicImgCond queyCond = new QueryBasicImgCond();
            queyCond.setLoginPatientPosition(mApp.loginDoctorPosition);
            queyCond.setRequestClientType("1");
            queyCond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            queyCond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
            queyCond.setImgCode(mProvidePatientConditionDiseaseRecords.getImgCode());
            loadImgTask = new LoadImgTask(queyCond);
            loadImgTask.execute();
        }
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back:
                    finish();
                    break;
            }
        }
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

    class LoadImgTask extends AsyncTask<Void,Void, List<ProvideBasicsImg>>{
        QueryBasicImgCond queryBasicImgCond;
        LoadImgTask(QueryBasicImgCond queryBasicImgCond){
            this.queryBasicImgCond = queryBasicImgCond;
        }
        @Override
        protected List<ProvideBasicsImg> doInBackground(Void... voids) {
            List<ProvideBasicsImg> retlist = new ArrayList();
            try {
                String querimgstr = new Gson().toJson(queryBasicImgCond);
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+querimgstr, Constant.SERVICEURL+ INetAddress.QUERY_PATIENTHISTIMG_URL);
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),ProvideBasicsImg.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retlist;
        }

        @Override
        protected void onPostExecute(List<ProvideBasicsImg> provideBasicsImgs) {
            if(provideBasicsImgs.size()>0){
                ProvideBasicsImg imgbean = provideBasicsImgs.get(0);
                try {
                    //Glide.with(mContext).load(new URI(imgbean.getImgUrl())).into(mImageView01);
                    Glide.with(mContext).load(imgbean.getImgUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(mImageView01);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(provideBasicsImgs.size()>1){
                ProvideBasicsImg imgbean = provideBasicsImgs.get(1);
                try {
                    Glide.with(mContext).load(imgbean.getImgUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(mImageView02);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(provideBasicsImgs.size()>2){
                ProvideBasicsImg imgbean = provideBasicsImgs.get(2);
                try {
                    //Glide.with(mContext).load(new URI(imgbean.getImgUrl())).into(mImageView03);
                    Glide.with(mContext).load(imgbean.getImgUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(mImageView03);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(provideBasicsImgs.size()>3){
                ProvideBasicsImg imgbean = provideBasicsImgs.get(3);
                try {
                    //Glide.with(mContext).load(new URI(imgbean.getImgUrl())).into(mImageView04);
                    Glide.with(mContext).load(imgbean.getImgUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(mImageView04);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(provideBasicsImgs.size()>4){
                ProvideBasicsImg imgbean = provideBasicsImgs.get(4);
                try {
                    //Glide.with(mContext).load(new URI(imgbean.getImgUrl())).into(mImageView05);
                    Glide.with(mContext).load(imgbean.getImgUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(mImageView05);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cacerProgress();
        }
    }
}
