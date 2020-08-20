package www.patient.jykj_zxyl.activity.myself.order.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allin.commlibrary.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.ProvideBasicsImg;
import entity.patientapp.Photo_Info;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.order.RefundApplyContract;
import www.patient.jykj_zxyl.activity.myself.order.RefundApplyPresenter;
import www.patient.jykj_zxyl.adapter.JZJL_WZZLImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_dialog.BaseReasonDialog;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_view.BaseToolBar;
import www.patient.jykj_zxyl.base.enum_type.UploadStatusEnum;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.util.BitmapUtil;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.MyId;
import www.patient.jykj_zxyl.util.ToastUtils;

/**
 * Description:退款
 *
 * @author: qiuxinhai
 * @date: 2020-08-06 15:13
 */
public class RefundActivity extends AbstractMvpBaseActivity<RefundApplyContract.View,
        RefundApplyPresenter> implements RefundApplyContract.View {

    private BaseToolBar mToolBar;//顶部toolBar
    private EditText edRefundDesc;
    private LinearLayout rlChooseRoot;
    private ImageView ivAddImgBtn;
    private TextView tvSubmitBtn;
    private TextView tvRefundReason;
    private TextView tvPriceValue;
    private TextView tvRefundMsg;
    private List<BaseReasonBean> mCancelContractBeans;
    private BaseReasonDialog mCancelContractDialog;
    private BaseReasonBean mCancelContractBean;
    private ImageView ivImgOne;
    private ImageView ivImgTwo;
    private ImageView ivImgThree;
    private RecyclerView mImageRecycleView;
    private FullyGridLayoutManager mGridLayoutManager;
    private JZJL_WZZLImageViewRecycleAdapter mAdapter;
    private List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
    private JYKJApplication mApp;
    private List<Photo_Info> mPhotoInfos = new ArrayList<>();
    private File mTempFile;
    private String actualPayment;
    private String orderId;
    private String mImgcode;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_apply_refund;
    }


    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            actualPayment = extras.getString("actualPayment");
            orderId=extras.getString("orderId");
        }


    }

    @Override
    protected void initView() {
        super.initView();
        initDir();
        mApp = (JYKJApplication) getApplication();
        mCancelContractDialog = new BaseReasonDialog(this,"退款原因");
        mCancelContractBeans = new ArrayList<>();
        mToolBar=findViewById(R.id.toolbar);
        edRefundDesc=findViewById(R.id.ed_refund_desc);
        rlChooseRoot=findViewById(R.id.rl_choose_root);
        ivAddImgBtn=findViewById(R.id.iv_add_img_btn);
        tvSubmitBtn=findViewById(R.id.tv_submit_btn);
        tvRefundReason=findViewById(R.id.tv_refund_reason);
        ivImgOne=findViewById(R.id.iv_img_one);
        ivImgTwo=findViewById(R.id.iv_img_two);
        ivImgThree=findViewById(R.id.iv_img_three);
        tvPriceValue=findViewById(R.id.tv_price_value);
        tvRefundMsg=findViewById(R.id.tv_refund_msg);
        mImageRecycleView =  this.findViewById(R.id.rv_activityPublish_Image);
        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mImageRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mImageRecycleView.setHasFixedSize(true);
        mAdapter = new JZJL_WZZLImageViewRecycleAdapter(mProvideBasicsImg, mApp);
        mImageRecycleView.setAdapter(mAdapter);
        setToolBar();
        addListener();
    }


    @Override
    protected void initData() {
        super.initData();
        mImgcode = MyId.createUUID();
        tvPriceValue.setText(String.format("¥%s", actualPayment));
        tvRefundMsg.setText(String.format("不可修改，最多¥%s", actualPayment));
        getData("10110");

    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        mToolBar.setMainTitle("申请退款");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 添加监听
     */
    private void addListener(){
        rlChooseRoot.setOnClickListener(v -> {
            if (!CollectionUtils.isEmpty(mCancelContractBeans)) {
                mCancelContractDialog.show();
                mCancelContractDialog.setData(mCancelContractBeans);
            }
        });
        ivAddImgBtn.setOnClickListener(v -> {
            if (mPhotoInfos.size()<3) {
                String[] items = {"拍照", "从相册选择"};
                new AlertDialog.Builder(RefundActivity.this)
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
                                        BitmapUtil.selectAlbum(RefundActivity.this);//从相册选择
                                        break;
                                }
                            }
                        }).show();
            }else{
                ToastUtils.showToast("最多可以选择3张");
            }

        });
        tvSubmitBtn.setOnClickListener(v -> {
            if (mCancelContractBean==null) {
                ToastUtils.showToast("请选择退款原因");
                return;
            }
            String refundDesc = edRefundDesc.getText().toString();
            mPresenter.sendOperPatientMyOrderResRefundRequest(orderId,mImgcode,
                    mCancelContractBean.getAttrCode()+"",mCancelContractBean.getAttrName()
                    ,refundDesc,RefundActivity.this);

        });

        mCancelContractDialog.setOnClickItemListener(new BaseReasonDialog.OnClickItemListener() {
            @Override
            public void onClickItem(BaseReasonBean cancelContractBean) {
                tvRefundReason.setText(cancelContractBean.getAttrName());
                mCancelContractBean = cancelContractBean;
            }
        });
    }

    /**
     * 获取订单
     * @param baseCode 基础编码
     */
    private void getData(String baseCode){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("baseCode",baseCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {

                    }

                    @Override
                    public void hideLoadingView() {

                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    String resJsonData = baseBean.getResJsonData();
                    mCancelContractBeans
                            = GsonUtils.jsonToList(resJsonData, BaseReasonBean.class);
                }

            }

        });
    }

    /**
     * 创建临时文件夹 _tempphoto
     */
    private void initDir() {
        // 声明目录
        File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/_tempphoto");
        if (!tempDir.exists()) {
            tempDir.mkdirs();// 创建目录
        }
        mTempFile = new File(tempDir, BitmapUtil.getPhotoFileName());// 生成临时文件
    }

    /**
     * 启动上传任务
     */
    private void startUploadImageTask() {
        for (int i = 0; i < mPhotoInfos.size(); i++) {
            Photo_Info photo_info = mPhotoInfos.get(i);
            if (photo_info.getUploadStatus() != UploadStatusEnum.uploadSucess) {
                executeUploadTask(photo_info);
            }
        }
    }

    /**
     * 发送上传请求任务
     * @param photo_info 图片Img
     */
    private void executeUploadTask(Photo_Info photo_info){
        HashMap<String, Object> hashMap = ParameUtil.buildBasePatientParam(this);
        hashMap.put("imgCode",mImgcode);
        hashMap.put("orderCode",orderId);
        hashMap.put("imgBase64Data",photo_info.getPhoto());
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operPatientMyOrderResRefundImg(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        photo_info.setUploadStatus(UploadStatusEnum.uploadPre);
                    }

                    @Override
                    public void hideLoadingView() {

                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    photo_info.setUploadStatus(UploadStatusEnum.uploadSucess);
                    if(getUploadSucessAll()){
                        RefundActivity.this.finish();
                    }

                }else{
                    photo_info.setUploadStatus(UploadStatusEnum.uploadError);
                    ToastUtils.showToast(baseBean.getResMsg());
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                photo_info.setUploadStatus(UploadStatusEnum.uploadError);
            }
        });
    }

    /**
     * 获取上传成功图片数量
     * @return count
     */
    private boolean getUploadSucessAll(){
        int count=0;
        for (Photo_Info mPhotoInfo : mPhotoInfos) {
            if (mPhotoInfo.getUploadStatus()==
                    UploadStatusEnum.uploadSucess) {
                count++;
            }
        }
        return count==mPhotoInfos.size();
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
                BitmapUtil.startPhotoZoom(this, uri, 450);
            }

            // 处理拍照返回
            if (requestCode == Constant.SELECT_PIC_BY_TACK_PHOTO
                    && resultCode == RESULT_OK) {// 拍照成功 RESULT_OK= -1
                // 剪裁图片
                BitmapUtil.startPhotoZoom(this, Uri.fromFile(mTempFile), 450);
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
                System.out.println("进来了");
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"))));//将imageUri对象的图片加载到内存
            }
            System.out.println("图片：" + photo);
            Photo_Info photo_info = new Photo_Info();
            photo_info.setPhoto(BitmapUtil.bitmaptoString(photo));
            mPhotoInfos.add(photo_info);
            setImgData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setImgData(){
        for (int i = 0; i < mPhotoInfos.size(); i++) {
            String photo = mPhotoInfos.get(i).getPhoto();
            if(i==0){

                ivImgOne.setImageBitmap(BitmapUtil.stringtoBitmap(photo));
            }else if(i==1){

                ivImgTwo.setImageBitmap(BitmapUtil.stringtoBitmap(photo));
            }else if(i==2){
                ivImgThree.setImageBitmap(BitmapUtil.stringtoBitmap(photo));

            }
        }
    }

    @Override
    public void showLoading(int code) {
        if (code==100) {
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();

    }

    @Override
    public void getOperPatientMyOrderResFundResult(boolean isSucess,String msg) {
        if(isSucess){
            if (CollectionUtils.isEmpty(mPhotoInfos)) {
                this.finish();
            }else{
                startUploadImageTask();
            }

        }else{
            ToastUtils.showToast(msg);
        }
    }
}
