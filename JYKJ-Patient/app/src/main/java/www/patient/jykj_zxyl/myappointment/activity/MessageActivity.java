package www.patient.jykj_zxyl.myappointment.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import entity.patientapp.Photo_Info;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.twjz.InquiryDataActivity;
import www.patient.jykj_zxyl.adapter.MessageListAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.ImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.MessageContract;
import www.patient.jykj_zxyl.myappointment.Presenter.MessagePrezenter;
import www.patient.jykj_zxyl.myappointment.bean.ViewInteractPatientMessageBean;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.BitmapUtil;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.ToastUtils;

/**
 * 我的医生 == 》 就诊记录 ==》 诊后留言
 * ImageViewRecycleAdapter  适配器
 * MessageListAdapter  医生回复列表适配器
 */
public class MessageActivity extends AbstractMvpBaseActivity<MessageContract.View,
        MessagePrezenter> implements MessageContract.View {

    @BindView(R.id.iv_back_left)
    ImageView ivBackLeft;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_patientName)
    TextView tvPatientName;
    @BindView(R.id.tv_msgType)
    TextView tvMsgType;
    @BindView(R.id.tv_msgDate)
    TextView tvMsgDate;
    @BindView(R.id.tv_linkPhone)
    TextView tvLinkPhone;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.rv_imageView)
    RecyclerView rvImageView;
    @BindView(R.id.message_recy)
    RecyclerView messageRecy;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private File mTempFile;
    private FullyGridLayoutManager mGridLayoutManager;
    private List<Photo_Info> mPhotoInfos = new ArrayList<>();
    private ImageViewRecycleAdapter mImageViewRecycleAdapter;
    private JYKJApplication mApp;
    private ArrayList<String> updataArrList = new ArrayList<String>();
    private String orderCode;
    private List<ViewInteractPatientMessageBean.InteractPatientMessageActiveListBean> list = new ArrayList<>();
    private MessageListAdapter messageListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String treatmentType;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMessageRequest(mApp.loginDoctorPosition, "1", mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(), mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(), orderCode);
    }

    @Override
    protected void initView() {
        super.initView();
        ActivityUtil.setStatusBarMain(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderCode = bundle.getString("orderCode");
            treatmentType = bundle.getString("treatmentType");
        }
        initDir();
        mApp = (JYKJApplication) this.getApplication();
        mGridLayoutManager = new FullyGridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rvImageView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvImageView.setHasFixedSize(true);
        final Photo_Info photo_info = new Photo_Info();
        photo_info.setPhotoID("ADDPHOTO");
        mPhotoInfos.add(photo_info);
        mImageViewRecycleAdapter = new ImageViewRecycleAdapter(mPhotoInfos, mApp);
        rvImageView.setAdapter(mImageViewRecycleAdapter);
        mImageViewRecycleAdapter.setOnItemClickListener(new ImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(final int position) {

                if ("ADDPHOTO".equals(mPhotoInfos.get(position).getPhotoID())) {
                    if (mPhotoInfos.size() >= 4) {
                        Toast.makeText(MessageActivity.this, "照片不超过五张", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String[] items = {"拍照", "从相册选择"};
                    Dialog dialog = new AlertDialog.Builder(MessageActivity.this)
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
                                            BitmapUtil.selectAlbum(MessageActivity.this);//从相册选择
                                            break;
                                    }
                                }
                            }).show();
                } else {
                    Dialog dialog = new AlertDialog.Builder(MessageActivity.this)
                            .setMessage("删除该照片")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String photoID = mPhotoInfos.get(position).getPhotoID();
                                    if (!updataArrList.contains(photoID)) {
                                        updataArrList.add(photoID);
                                    }

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
        //医生回复列表
        linearLayoutManager = new LinearLayoutManager(this);
        messageRecy.setLayoutManager(linearLayoutManager);
    }

    @OnClick({R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                commitData();
                break;
        }
    }

    //提交或者修改
    private void commitData() {
        String s = content.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtils.showToast("请输入留言内容");
            return;
        }
        mPresenter.getMessageCommitRequest(mApp.loginDoctorPosition, "1", mApp.mProvideViewSysUserPatientInfoAndRegion.getOperPatientCode(), mApp.mProvideViewSysUserPatientInfoAndRegion.getOperPatientName(), "0", "", orderCode, treatmentType, mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone(), content.getText().toString(), "", "");
    }
        //诊后留言详情
    @Override
    public void getMessageSucess(ViewInteractPatientMessageBean viewInteractPatientMessageBeans) {
        if (viewInteractPatientMessageBeans != null) {
            //类型
            tvMsgType.setText(viewInteractPatientMessageBeans.getTreatmentTypeName());

            String patientLinkPhone = viewInteractPatientMessageBeans.getPatientLinkPhone();
            if (TextUtils.isEmpty(patientLinkPhone)) {
                tvLinkPhone.setText("联系电话：");
            } else {
                tvLinkPhone.setText("联系电话：" + viewInteractPatientMessageBeans.getPatientLinkPhone());

            }
            //回复内容  content

            //医生回复消息列表
            List<ViewInteractPatientMessageBean.InteractPatientMessageActiveListBean> interactPatientMessageActiveList = viewInteractPatientMessageBeans.getInteractPatientMessageActiveList();
            for (ViewInteractPatientMessageBean.InteractPatientMessageActiveListBean interactPatientMessageActiveListBean : interactPatientMessageActiveList) {
                list.add(interactPatientMessageActiveListBean);
            }
            messageListAdapter = new MessageListAdapter(list);
            messageRecy.setAdapter(messageListAdapter);
        }
    }

    //提交成功
    @Override
    public void getMessageCommitSucess(String msg) {
        ToastUtils.showToast(msg);
    }

    //提交失败
    @Override
    public void getMessageCommitError(String msg) {
        ToastUtils.showToast(msg);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}