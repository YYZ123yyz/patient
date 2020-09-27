package www.patient.jykj_zxyl.activity.home.twjz;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import entity.patientapp.Photo_Info;
import entity.wdzs.ProvideInteractPatientInterrogationParment;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.InquiryContract;
import www.patient.jykj_zxyl.myappointment.Contract.MedicalRecordContract;
import www.patient.jykj_zxyl.presenter.InquiryPresenter;
import www.patient.jykj_zxyl.presenter.MedicalRecordPresenter;
import www.patient.jykj_zxyl.util.BitmapUtil;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.ToastUtils;
import www.patient.jykj_zxyl.util.Util;

/**
 * Created by G on 2020/9/27 10:57
 */
public class InquiryDataActivity extends AbstractMvpBaseActivity<InquiryContract.View, InquiryPresenter>
        implements InquiryContract.View {

    @BindView(R.id.iv_back_left)
    LinearLayout back;
    @BindView(R.id.tv_activityHZZL_MZ)
    TextView findTimeTv;//时间
    @BindView(R.id.tv_activityHZZL_gxybs)
    TextView hyperTv;//高血压病史
    @BindView(R.id.tv_activityHZZL_userName)
    TextView familyTv;//家族史
    @BindView(R.id.tv_activityHZZL_clyq)
    TextView meainstruTv;//测量仪器
    @BindView(R.id.tv_activityHZZL_clfs)
    TextView meaType;//测量方式
    @BindView(R.id.rv_activityPublish_Image)
    RecyclerView myRecycleview;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_activityHZZL_userTZ)
    EditText patientName;//填写名字
    @BindView(R.id.tv_activityHZZL_userSFXJ)
    EditText patientNum;//电话
    @BindView(R.id.radio_yes)
    RadioButton manChoose;
    @BindView(R.id.radio_no)
    RadioButton womenChoose;
    @BindView(R.id.tv_activityHZZL_userAuthState)
    EditText birthday;//年龄

    @BindView(R.id.tv_activityHZZL_sex)
    EditText highPressureNum;//收缩压
    @BindView(R.id.tv_activityHZZL_szy)
    EditText lowPressureNum;//舒张压
    @BindView(R.id.tv_activityHZZL_xl)
    EditText heartRateNum;//心率

    @BindView(R.id.tv_activityHZZL_idCardNum)
    EditText chiefComplaint;//主诉
    @BindView(R.id.tv_activityHZZL_BirthDay)
    EditText historyNew;//现病史
    @BindView(R.id.tv_activityHZZL_history)
    EditText historyPast;//既往史
    @BindView(R.id.tv_guomin_history)
    EditText historyAllergy;//过敏史

    private JYKJApplication mApp;
    private FullyGridLayoutManager mGridLayoutManager;
    private List<Photo_Info> mPhotoInfos = new ArrayList<>();
    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private File mTempFile;
    private String orderCode;
    private String operPatientCode;
    private String operPatientName;
    private ProvideInteractPatientInterrogationParment mProvideInteractPatientInterrogationParment = new ProvideInteractPatientInterrogationParment();
    private boolean isUpdata = false;
    private String treatmentType;
    private String doctorCode;
    private String doctorName;
    private Integer measureInstrumentStr;
    private Integer measureMode;

    @Override
    protected int setLayoutId() {
        return R.layout.layout_inquirydata;
    }

    @Override
    public void showLoading(int code) {

    }


    private void initDir() {
        // 声明目录
        File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/_tempphoto");
        if (!tempDir.exists()) {
            tempDir.mkdirs();// 创建目录
        }
        mTempFile = new File(tempDir, BitmapUtil.getPhotoFileName());// 生成临时文件
    }


    @Override
    protected void initView() {
        super.initView();
        initDir();
        mGridLayoutManager = new FullyGridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        myRecycleview.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        myRecycleview.setHasFixedSize(true);
        final Photo_Info photo_info = new Photo_Info();
        photo_info.setPhotoID("ADDPHOTO");
        mPhotoInfos.add(photo_info);
        mImageViewRecycleAdapter = new ImageViewRecycleAdapter(mPhotoInfos, mApp);
        myRecycleview.setAdapter(mImageViewRecycleAdapter);
        mImageViewRecycleAdapter.setOnItemClickListener(new ImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(final int position) {
                if (mPhotoInfos.size() >= 4) {
                    Toast.makeText(InquiryDataActivity.this, "照片不超过三张", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("ADDPHOTO".equals(mPhotoInfos.get(position).getPhotoID())) {
                    String[] items = {"拍照", "从相册选择"};
                    Dialog dialog = new AlertDialog.Builder(InquiryDataActivity.this)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                            StrictMode.setVmPolicy(builder.build());
                                            builder.detectFileUriExposure();
                                            // 添加Action类型：MediaStore.ACTION_IMAGE_CAPTURE
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            // 指定调用相机拍照后照片(结果)的储存路径
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
                                            // 等待返回结果
                                            startActivityForResult(intent, Constant.SELECT_PIC_BY_TACK_PHOTO);
                                            break;
                                        case 1:
                                            BitmapUtil.selectAlbum(InquiryDataActivity.this);//从相册选择
                                            break;
                                    }
                                }
                            }).show();
                } else {
                    Dialog dialog = new AlertDialog.Builder(InquiryDataActivity.this)
                            .setMessage("删除该照片")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mPhotoInfos.remove(position);
                                    if (mPhotoInfos.size() == 0) {
                                        Photo_Info photo_info1 = new Photo_Info();
                                        photo_info1.setPhotoID("ADDPHOTO");
                                        mPhotoInfos.add(photo_info1);
                                    }
                                    mImageViewRecycleAdapter.setDate(mPhotoInfos);
                                    mImageViewRecycleAdapter.notifyDataSetChanged();
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mApp.gPayCloseActivity.add(this);
        orderCode = getIntent().getStringExtra("order");
        operPatientCode = getIntent().getStringExtra("operPatientCode");
        operPatientName = getIntent().getStringExtra("operPatientName");
        treatmentType = getIntent().getStringExtra("treatmentType");
        doctorCode = getIntent().getStringExtra("doctorCode");
        doctorName = getIntent().getStringExtra("doctorName");


        if (mApp.gBasicDate.get(10006) == null)
            Util.getBasicDate(10006, mApp);
        if (mApp.gBasicDate.get(10007) == null)
            Util.getBasicDate(10007, mApp);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginPatientPosition", "108.93425^34.23053");
        paramMap.put("requestClientType", "1");
        paramMap.put("operPatientCode", operPatientCode);// mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode()
        paramMap.put("operPatientName", operPatientName);//mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName()
        paramMap.put("orderCode", orderCode);
        String s = RetrofitUtil.encodeParam(paramMap);
        mPresenter.getDataDet(s);


    }

    @Override
    protected void onActivityResult(
            int requestCode,  // 请求码 自定义
            int resultCode,  // 结果码 成功 -1 == OK
            Intent data) { // 数据 ? 可以没有
        try {

            // 如果是直接从相册获取
            if (requestCode == Constant.SELECT_PIC_FROM_ALBUM
                    && resultCode == RESULT_OK
                    && data != null) {

                final Uri uri = data.getData();//返回相册图片的Uri
                String path = uri.getPath();
                String encodedPath = uri.getEncodedPath();

//                BitmapUtil.startPhotoZoom(InquiryDataActivity.this, uri, 450);
                setPicToView(data);
            }

            // 处理拍照返回
            if (requestCode == Constant.SELECT_PIC_BY_TACK_PHOTO
                    && resultCode == RESULT_OK) {// 拍照成功 RESULT_OK= -1
                // 剪裁图片
//                BitmapUtil.startPhotoZoom(InquiryDataActivity.this, Uri.fromFile(mTempFile), 450);
                setPicToView(data);
            }
            // 接收剪裁回来的结果
            if (requestCode == Constant.REQUEST_PHOTO_CUT
                    && resultCode == RESULT_OK) {// 剪裁加工成功
                //让剪裁结果显示到图片框
                setPicToView(data);
            }
        } catch (Exception e) {
            Log.i("yi", "yichahahaha");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPicToView(Intent data) {
        Bitmap photo;
        try {
            Uri u = data.getData();
            if (u != null) {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));//将imageUri对象的图片加载到内存
            } else {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"))));//将imageUri对象的图片加载到内存
            }

            Photo_Info photo_info = new Photo_Info();
            photo_info.setPhoto(BitmapUtil.bitmaptoString(photo));
            mPhotoInfos.add(photo_info);
            mImageViewRecycleAdapter.setDate(mPhotoInfos);
            mImageViewRecycleAdapter.notifyDataSetChanged();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_back_left, R.id.tv_activityHZZL_MZ, R.id.tv_activityHZZL_gxybs, R.id.tv_activityHZZL_userName,
            R.id.tv_activityHZZL_clyq, R.id.tv_commit, R.id.tv_activityHZZL_clfs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_left:
                finish();
                break;
            case R.id.tv_activityHZZL_MZ:
                Calendar nowdate = Calendar.getInstance();
                int mYear = nowdate.get(Calendar.YEAR);
                int mMonth = nowdate.get(Calendar.MONTH);
                int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(InquiryDataActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.tv_activityHZZL_gxybs:
                showDoctorTitleDialog(0);
                break;
            case R.id.tv_activityHZZL_userName:
                showDoctorTitleDialog(1);
                break;
            case R.id.tv_activityHZZL_clyq:
                showCLYQDialog();
                break;
            case R.id.tv_commit:
                commitData();
                break;
            case R.id.tv_activityHZZL_clfs:
                showCLFSDialog();
                break;
        }
    }

    private void commitData() {
       /* if (TextUtils.isEmpty(patientName.getText().toString().trim())) {
            ToastUtils.showToast("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(patientNum.getText().toString().trim())) {
            ToastUtils.showToast("请填写电话号码");
            return;
        }
        if (TextUtils.isEmpty(birthday.getText().toString().trim())) {
            ToastUtils.showToast("请填写年龄");
            return;
        }
        if (!manChoose.isChecked() && !womenChoose.isChecked()) {
            ToastUtils.showToast("请选择性别");
            return;
        }
        if (TextUtils.isEmpty(findTimeTv.getText().toString().trim())) {
            ToastUtils.showToast("请填写时间");
            return;
        }
        if (TextUtils.isEmpty(hyperTv.getText().toString().trim())) {
            ToastUtils.showToast("请选择是否有高血压病史");
            return;
        }
        if (TextUtils.isEmpty(familyTv.getText().toString().trim())) {
            ToastUtils.showToast("请选择是否有家族史");
            return;
        }
        if (TextUtils.isEmpty(highPressureNum.getText().toString().trim())) {
            ToastUtils.showToast("填写收缩压");
            return;
        }
        if (TextUtils.isEmpty(lowPressureNum.getText().toString().trim())) {
            ToastUtils.showToast("填写舒张压");
            return;
        }
        if (TextUtils.isEmpty(heartRateNum.getText().toString().trim())) {
            ToastUtils.showToast("填写心率");
            return;
        }
        if (TextUtils.isEmpty(meainstruTv.getText().toString().trim())) {
            ToastUtils.showToast("请选择测量设备");
            return;
        }
        if (TextUtils.isEmpty(meainstruTv.getText().toString().trim())) {
            ToastUtils.showToast("请选择测量方式");
            return;
        }
        if (TextUtils.isEmpty(chiefComplaint.getText().toString().trim())) {
            ToastUtils.showToast("请填写主诉");
            return;
        }
        if (TextUtils.isEmpty(historyNew.getText().toString().trim())) {
            ToastUtils.showToast("请填写现病史");
            return;
        }
        if (TextUtils.isEmpty(historyPast.getText().toString().trim())) {
            ToastUtils.showToast("请填写既往史");
            return;
        }
        if (TextUtils.isEmpty(historyAllergy.getText().toString().trim())) {
            ToastUtils.showToast("请填写既往史");
            return;
        }*/

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginPatientPosition", "108.93425^34.23053");
        paramMap.put("requestClientType", "1");
        paramMap.put("operPatientCode", operPatientCode);// mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode()
        paramMap.put("operPatientName", operPatientName);//mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName()
        paramMap.put("orderCode", orderCode);
        paramMap.put("imgCode", isUpdata ? "imagecode" : "");
        paramMap.put("treatmentType", treatmentType);
        paramMap.put("doctorCode", doctorCode);
        paramMap.put("doctorName", doctorName);
        paramMap.put("patientCode", operPatientCode);
        paramMap.put("patientName", operPatientName);

        paramMap.put("patientLinkPhone", patientNum.getText().toString().trim());
        paramMap.put("gender", manChoose.isChecked() ? "男" : "女");
        paramMap.put("birthday", birthday.getText().toString().trim());
        paramMap.put("bloodPressureAbnormalDate", findTimeTv.getText().toString().trim());
        paramMap.put("flagHtnHistory", hyperTv.getText().toString().trim());
        paramMap.put("flagFamilyHtn", familyTv.getText().toString().trim());
        paramMap.put("highPressureNum", highPressureNum.getText().toString().trim());
        paramMap.put("lowPressureNum", lowPressureNum.getText().toString().trim());
        paramMap.put("heartRateNum", heartRateNum.getText().toString().trim());
        paramMap.put("measureInstrument", measureInstrumentStr);
        paramMap.put("measureInstrumentName", meainstruTv.getText().toString().trim());
        paramMap.put("measureMode", measureMode);
        paramMap.put("measureModeName", meainstruTv.getText().toString().trim());
        paramMap.put("chiefComplaint", chiefComplaint.getText().toString().trim());
        paramMap.put("historyNew", historyNew.getText().toString().trim());
        paramMap.put("historyPast", historyPast.getText().toString().trim());
        paramMap.put("historyAllergy", historyAllergy.getText().toString().trim());
        StringBuilder photoUrl = new StringBuilder();
        photoUrl.append("data:image/jpg;base64,");
        if (mPhotoInfos.size() > 1) {
            for (int i = 1; i < mPhotoInfos.size(); i++) {
                String photo = mPhotoInfos.get(i).getPhoto();
                photoUrl.append(photo).append("^");
                LogUtils.e("图片path  44  " + photoUrl.toString());
            }
        }

        paramMap.put("imgBase64Array", photoUrl.toString());
        String s = RetrofitUtil.encodeParam(paramMap);
        mPresenter.submitData(s);


    }


    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear + 1;
            int mDay = dayOfMonth;
            String month = "";
            String day = "";
            if (mMonth < 10)
                month = "0" + mMonth;
            else
                month = mMonth + "";
            if (mDay < 10)
                day = "0" + mDay;
            else
                day = mDay + "";
            findTimeTv.setText(String.format("%d-%s-%s", mYear, month, day));
        }
    };


    private void showDoctorTitleDialog(int type) {
        String[] doctorTitleName = new String[]{"否", "是"};// 家族内是否有高血压病史
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(doctorTitleName, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                switch (type) {
                    case 0:
                        hyperTv.setText(doctorTitleName[which]);
                        break;
                    case 1:
                        familyTv.setText(doctorTitleName[which]);
                        break;
                }
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


    private void showCLYQDialog() {
        String[] items = new String[mApp.gBasicDate.get(10006).size()];
        for (int i = 0; i < mApp.gBasicDate.get(10006).size(); i++) {
            items[i] = mApp.gBasicDate.get(10006).get(i).getAttrName();
        }
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                meainstruTv.setText(mApp.gBasicDate.get(10006).get(which).getAttrName());
                measureInstrumentStr = mApp.gBasicDate.get(10006).get(which).getAttrCode();
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


    private void showCLFSDialog() {
        String[] items = new String[mApp.gBasicDate.get(10007).size()];
        for (int i = 0; i < mApp.gBasicDate.get(10007).size(); i++) {
            items[i] = mApp.gBasicDate.get(10007).get(i).getAttrName();
        }
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                meaType.setText(mApp.gBasicDate.get(10007).get(which).getAttrName());
                measureMode = mApp.gBasicDate.get(10007).get(which).getAttrCode();
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    @Override
    public void hasNoData() {
        isUpdata = false;
    }

    @Override
    public void hasData() {
//        patientNum.setText();
//        manChoose.setText();
//        birthday.setText();
//        findTimeTv.setText();
//        hyperTv.setText();
//        familyTv.setText();
//        highPressureNum.setText();
//        lowPressureNum.setText();
//        heartRateNum.setText();
//        meainstruTv.setText();
//        meainstruTv.setText();
//        chiefComplaint.setText();
//        historyNew.setText();
//        historyPast.setText();
//        historyAllergy.setText();
//
//        mImageViewRecycleAdapter.setDate(mPhotoInfos);
//        mImageViewRecycleAdapter.notifyDataSetChanged();
    }
}
