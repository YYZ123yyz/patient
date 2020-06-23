package www.patient.jykj_zxyl.activity.home.myself;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import entity.HZIfno;
import entity.ProvideBasicsImg;
import entity.mySelf.JwbsYstxDetail;
import entity.mySelf.JwbsYstxInfo;
import entity.mySelf.conditions.QueryBasicImgCond;
import entity.mySelf.conditions.QueryJwbsYstxDetailCond;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import entity.patientapp.Photo_Info;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.*;

/**
 * 既往病史 ==》 病史详情
 */
public class JWBSBSXQActivity extends AppCompatActivity {


    private         Context                 mContext;
    private JWBSBSXQActivity mActivity;
    private         JYKJApplication         mApp;
    private         RecyclerView            mRecycleView;
    private         LinearLayoutManager     layoutManager;
    private TextView tv_ystx_jllx;
    private TextView tv_ystx_jlys;
    private TextView tv_ystx_jlrq;
    private TextView tv_ystx_zs;
    private TextView tv_ystx_tz;
    private TextView tv_ystx_jws;
    private TextView tv_ystx_fzzd;
    private TextView tv_ystx_cbyd;
    private ImageView image01;
    private ImageView image02;
    private ImageView image03;
    private ImageView image04;
    private ImageView image05;
    private ImageView image06;
    public                  ProgressDialog              mDialogProgress =null;
    private LoadImgTask loadImgTask;
    private JwbsYstxInfo jwbsYstxInfo = null;
    LoadDataTask loadDataTask = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (JYKJApplication)getApplication();
        mActivity = JWBSBSXQActivity.this;
        mContext = JWBSBSXQActivity.this;
        jwbsYstxInfo = (JwbsYstxInfo)getIntent().getSerializableExtra("jwbsYstxInfo");
        setContentView(R.layout.activity_jwbs_bsxq);
        tv_ystx_jllx = findViewById(R.id.tv_ystx_jllx);
        tv_ystx_jlys = findViewById(R.id.tv_ystx_jlys);
        tv_ystx_jlrq = findViewById(R.id.tv_ystx_jlrq);
        tv_ystx_zs = findViewById(R.id.tv_ystx_zs);
        tv_ystx_tz = findViewById(R.id.tv_ystx_tz);
        tv_ystx_jws = findViewById(R.id.tv_ystx_jws);
        tv_ystx_fzzd = findViewById(R.id.tv_ystx_fzzd);
        tv_ystx_cbyd = findViewById(R.id.tv_ystx_cbyd);
        image01 = findViewById(R.id.image01);
        image02 = findViewById(R.id.image02);
        image03 = findViewById(R.id.image03);
        image04 = findViewById(R.id.image04);
        image05 = findViewById(R.id.image05);
        image06 = findViewById(R.id.image06);
        findViewById(R.id.back).setOnClickListener(new ButtonClick());
        setData();
    }

    class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
            }
        }
    }

    void  setData(){
        QueryJwbsYstxDetailCond queryJwbsYstxDetailCond = new QueryJwbsYstxDetailCond();
        queryJwbsYstxDetailCond.setLoginPatientPosition(mApp.loginDoctorPosition);
        queryJwbsYstxDetailCond.setMedicalId(String.valueOf(jwbsYstxInfo.getMedicalId()));
        queryJwbsYstxDetailCond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        queryJwbsYstxDetailCond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        queryJwbsYstxDetailCond.setRequestClientType("1");
        loadDataTask = new LoadDataTask(queryJwbsYstxDetailCond);
        loadDataTask.execute();
    }

    class LoadDataTask extends AsyncTask<Void,Void, JwbsYstxDetail>{
        QueryJwbsYstxDetailCond detailCond;
        LoadDataTask(QueryJwbsYstxDetailCond detailCond){
            this.detailCond = detailCond;
        }
        @Override
        protected JwbsYstxDetail doInBackground(Void... voids) {
            JwbsYstxDetail retbean = null;
            try {
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(detailCond),Constant.SERVICEURL+INetAddress.QUERY_JWBS_YSTX_DETAIL);
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retbean = JSON.parseObject(retEntity.getResJsonData(),JwbsYstxDetail.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retbean;
        }

        @Override
        protected void onPostExecute(JwbsYstxDetail jwbsYstxDetail) {
            if(null!=jwbsYstxDetail){
                tv_ystx_jllx.setText(StrUtils.defaultStr(jwbsYstxDetail.getMedicalTypeName()));
                tv_ystx_jlys.setText(StrUtils.defaultStr(jwbsYstxDetail.getDoctorName()));
                if(null!=jwbsYstxInfo.getCreateDate()) {
                    tv_ystx_jlrq.setText(DateTimeUtils.formatLongDate(jwbsYstxInfo.getCreateDate(),"yyyy/MM/dd"));
                }
                tv_ystx_zs.setText(StrUtils.defaultStr(jwbsYstxDetail.getChiefComplaint()));
                tv_ystx_tz.setText(StrUtils.defaultStr(jwbsYstxDetail.getMedicalExamination()));
                tv_ystx_jws.setText(StrUtils.defaultStr(jwbsYstxDetail.getPresentIllness()));
                tv_ystx_fzzd.setText(StrUtils.defaultStr(jwbsYstxDetail.getAuxiliaryCheck()));
                tv_ystx_cbyd.setText(StrUtils.defaultStr(jwbsYstxDetail.getTreatmentPlanCode()));
            }
        }
    }


    void loadimgs(){
        if(StrUtils.defaultStr("").length()>0) {
            getProgressBar("加载数据","数据加载中...");
            QueryBasicImgCond queyCond = new QueryBasicImgCond();
            queyCond.setLoginPatientPosition(mApp.loginDoctorPosition);
            queyCond.setRequestClientType("1");
            queyCond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            queyCond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
            queyCond.setImgCode("");
            loadImgTask = new LoadImgTask(queyCond);
            loadImgTask.execute();
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

    class LoadImgTask extends AsyncTask<Void,Void, List<ProvideBasicsImg>> {
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
                            .into(image01);
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
                            .into(image01);
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
                            .into(image01);
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
                            .into(image01);
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
                            .into(image01);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cacerProgress();
        }
    }
}
