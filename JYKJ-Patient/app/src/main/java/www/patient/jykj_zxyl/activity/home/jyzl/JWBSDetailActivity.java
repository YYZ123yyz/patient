package www.patient.jykj_zxyl.activity.home.jyzl;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.liyi.grid.adapter.SimpleAutoGridAdapter;

import entity.HZIfno;
import entity.ProvideBasicsImg;
import entity.mySelf.conditions.QueryBasicImgCond;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import entity.patientapp.Photo_Info;
import indi.liyi.viewer.ImageViewer;
import indi.liyi.viewer.ViewerStatus;
import indi.liyi.viewer.listener.OnBrowseStatusListener;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.*;
import www.patient.jykj_zxyl.adapter.myself.JwbsBrtxAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_utils.DateUtils;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.bean.HistoryDetBean;
import www.patient.jykj_zxyl.liyi.PhotoLoader;
import www.patient.jykj_zxyl.liyi.glide.GlideUtil;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.ParameUtil;
import www.patient.jykj_zxyl.util.StrUtils;
import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.patient.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.patient.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.Util;

import com.liyi.grid.*;

/**
 * 就诊总览==》个人总览==>个人状况==>既往病史==详情
 */
public class JWBSDetailActivity extends AppCompatActivity {


    private Context mContext;
    private JWBSDetailActivity mActivity;
    private JYKJApplication mApp;
    private RecyclerView mRecycleView;
    public ProgressDialog mDialogProgress = null;
    private List<String> imgslist = new ArrayList();
    private List<Photo_Info> mPhotoInfos = new ArrayList<>();
    private ProvidePatientConditionDiseaseRecord mProvidePatientConditionDiseaseRecords = new ProvidePatientConditionDiseaseRecord();
    List<ProvidePatientConditionDiseaseRecord> mdatalist = new ArrayList();
    private LoadImgTask loadImgTask;


    private JwbsBrtxAdapter jwbsBrtxAdapter;
    private LinearLayoutManager layoutManager;
    private TextView nickName;
    private TextView date;
    private TextView des;
    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private FullyGridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwbs_brtx_detail);
        mProvidePatientConditionDiseaseRecords = (ProvidePatientConditionDiseaseRecord) getIntent().getSerializableExtra("patientInfo");
        mContext = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        mActivity = this;
    }

    private void initLayout() {

        mRecycleView = findViewById(R.id.rv_activityPublish_Image);
        mGridLayoutManager = new FullyGridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);

        nickName = findViewById(R.id.tv_activityHZZL_userSG);
        date = findViewById(R.id.tv_activityHZZL_userYW);
        des = findViewById(R.id.tv_activityHZZL_region);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nickName.setText(mProvidePatientConditionDiseaseRecords.getRecordName());
        date.setText(Util.dateToStrDate(mProvidePatientConditionDiseaseRecords.getTreatmentDate()));
        des.setText(mProvidePatientConditionDiseaseRecords.getRecordContent());

        loadData();
//        loadimgs();
    }

    private void loadData() {
        HashMap<String, Object> paramsHashMap = ParameUtil.buildBaseParam(this);
        paramsHashMap.put("recordId", mProvidePatientConditionDiseaseRecords.getRecordId());

        ApiHelper.getPatientTestApi().getHisDet(RetrofitUtil.encodeParam(paramsHashMap)).compose(
                Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        getProgressBar("加载中", "正在加载，请稍后...");
                    }

                    @Override
                    public void hideLoadingView() {
                        cacerProgress();
                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {

                int resCode = baseBean.getResCode();
                if (resCode == 1) {
                    String resJsonData = baseBean.getResJsonData();
                    LogUtils.e("详情  " + resJsonData);
                    HistoryDetBean historyDetBean = GsonUtils.fromJson(resJsonData, HistoryDetBean.class);

                    String recordImgArray = historyDetBean.getRecordImgArray();
                    String recordImgIdArray = historyDetBean.getRecordImgIdArray();

                    if (recordImgArray != null && !TextUtils.isEmpty(recordImgArray)) {
                        if (recordImgArray.contains(",")) {
                            String[] split = recordImgArray.split(",");
                            String[] idSplit = recordImgIdArray.split(",");
                            for (int i = 0; i < split.length; i++) {
                                Photo_Info photo_info = new Photo_Info();
                                photo_info.setPhotoUrl(split[i]);
                                photo_info.setPhotoID(idSplit[i]);
                                mPhotoInfos.add(photo_info);
                            }
                        } else {
                            Photo_Info photo_info = new Photo_Info();
                            photo_info.setPhotoUrl(recordImgArray);
                            photo_info.setPhotoID(recordImgIdArray);
                            mPhotoInfos.add(photo_info);
                        }
                    }
                    mImageViewRecycleAdapter = new ImageViewRecycleAdapter(mPhotoInfos, mApp);
                    mRecycleView.setAdapter(mImageViewRecycleAdapter);

                } else {
                    ToastUtils.showShort("获取失败");
                    LogUtils.e("错误信息xxxxxxxxxxxxxxxxxxxxxxxxxxx" + baseBean.getResMsg());

                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                ToastUtils.showShort(s);
            }

            @Override
            protected String setTag() {
                return "history_det";
            }
        });


    }

    void loadimgs() {
        if (StrUtils.defaultStr(mProvidePatientConditionDiseaseRecords.getImgCode()).length() > 0) {
            getProgressBar("加载数据", "数据加载中...");
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


    class ButtonClick implements View.OnClickListener {
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
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */


    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

    class LoadImgTask extends AsyncTask<Void, Void, List<ProvideBasicsImg>> {
        QueryBasicImgCond queryBasicImgCond;

        LoadImgTask(QueryBasicImgCond queryBasicImgCond) {
            this.queryBasicImgCond = queryBasicImgCond;
        }

        @Override
        protected List<ProvideBasicsImg> doInBackground(Void... voids) {
            List<ProvideBasicsImg> retlist = new ArrayList();
            try {
                String querimgstr = new Gson().toJson(queryBasicImgCond);
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo=" + querimgstr, Constant.SERVICEURL + INetAddress.QUERY_PATIENTHISTIMG_URL);
                NetRetEntity retEntity = JSON.parseObject(retstr, NetRetEntity.class);
                if (1 == retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length() > 3) {
                    retlist = JSON.parseArray(retEntity.getResJsonData(), ProvideBasicsImg.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retlist;
        }

        void changeStatusBarColor(int colorId) {
            // 5.0及以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                // After LOLLIPOP not translucent status bar
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // Then call setStatusBarColor.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(colorId));
            }
        }

        @Override
        protected void onPostExecute(List<ProvideBasicsImg> provideBasicsImgs) {
            imgslist = new ArrayList();
            for (int i = 0; i < provideBasicsImgs.size(); i++) {
                ProvideBasicsImg parimgbean = provideBasicsImgs.get(i);
                imgslist.add(parimgbean.getImgUrl());
            }
            mdatalist.add(mProvidePatientConditionDiseaseRecords);
            if (imgslist.size() > 0) {
               /* imageViewer.setOnBrowseStatusListener(new OnBrowseStatusListener() {
                    @Override
                    public void onBrowseStatus(int status) {
                        if (status == ViewerStatus.STATUS_BEGIN_OPEN) {
                            changeStatusBarColor(R.color.colorBlack);
                        } else if (status == ViewerStatus.STATUS_SILENCE) {
                            changeStatusBarColor(R.color.colorPrimaryDark);
                        }
                    }
                });*/
            }
            jwbsBrtxAdapter.setDatas(mdatalist, imgslist);
            jwbsBrtxAdapter.notifyDataSetChanged();
            cacerProgress();
        }
    }
}
