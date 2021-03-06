package www.patient.jykj_zxyl.activity.home.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.ProvideBasicsImg;
import entity.ProvideInteractOrderInfo;
import entity.ProvideInteractPatientMessage;

import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.patient.jykj_zxyl.adapter.ZHLYImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.PhotoDialog;
import www.patient.jykj_zxyl.util.PhotoDialogHZ;
import www.patient.jykj_zxyl.util.Util;


/**
 * 我的医生 == 》 就诊记录 ==》 诊后留言
 * MessageListAdapter  适配器
 */
public class WDYS_JZJL_ZHLYActivity extends AppCompatActivity {

    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private Handler mHandler;
    private WDYS_JZJL_ZHLYActivity mActivity;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串
    private ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private ProvideInteractPatientMessage mProvideInteractPatientMessage;
    private TextView mNameTitle;               //标题，患者姓名
    private TextView mMessageType;               //消息类型
    private TextView mMessageDate;               //留言日期
    private EditText mMessageContent;               //消息类型
    private TextView mMessageLinkPhone;            //联系电话

    private RecyclerView mImageRecycleView;
    private FullyGridLayoutManager mGridLayoutManager;
    private ZHLYImageViewRecycleAdapter mAdapter;

    private List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
    private String mGetImgNetRetStr;                 //获取图片返回字符串

    private TextView mMessageReply;                  //留言回复内容
    private TextView mCommit;                        //

    private ProvideInteractOrderInfo mProvideInteractOrderInfo;                          //订单信息


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jzjl_zhly);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideInteractOrderInfo = (ProvideInteractOrderInfo) getIntent().getSerializableExtra("provideInteractOrderInfo");
        initListener();
        initHandler();
      //  getDate();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            mProvideInteractPatientMessage = JSON.parseObject(netRetEntity.getResJsonData(), ProvideInteractPatientMessage.class);
                            if (mProvideInteractPatientMessage != null) {
                                showLayoutDate();
                                getImgData();
                            }

                        }
                        break;

                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mGetImgNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {

                            mProvideBasicsImg = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsImg.class);
                            if (mProvideBasicsImg != null && mProvideBasicsImg.size() > 0) {
                                mAdapter.setDate(mProvideBasicsImg);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        break;

                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /**
     * 设置数据
     */
    private void getImgData() {
        ProvideBasicsImg provideBasicsImg = new ProvideBasicsImg();
        provideBasicsImg.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideBasicsImg.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideBasicsImg.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideBasicsImg.setRequestClientType("1");
        provideBasicsImg.setOrderCode(mProvideInteractOrderInfo.getOrderCode());
        provideBasicsImg.setImgCode(mProvideInteractPatientMessage.getImgCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideBasicsImg);
                    mGetImgNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "PatientMyDoctorControlle/searchIndexMyDoctorNotSigningResMessageImg");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mGetImgNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void showLayoutDate() {
        mNameTitle = (TextView) this.findViewById(R.id.tv_patientName);
        mMessageType = (TextView) this.findViewById(R.id.tv_msgType);
        mMessageDate = (TextView) this.findViewById(R.id.tv_msgDate);
        mMessageContent = (EditText) this.findViewById(R.id.content);
        mMessageLinkPhone = (TextView) this.findViewById(R.id.tv_linkPhone);
        mMessageReply = (TextView) this.findViewById(R.id.tv_messageReply);
//        mCommit = (TextView) this.findViewById(R.id.tv_commit);

//        mNameTitle.setText("【"+mProvideInteractPatientMessage.getPatientName()+"】诊后留言");
        mMessageType.setText(mProvideInteractPatientMessage.getTreatmentTypeName());
        if (mProvideInteractPatientMessage.getMessageDate() != null)
            mMessageDate.setText(Util.dateToStr(mProvideInteractPatientMessage.getMessageDate()));
        if (mProvideInteractPatientMessage.getMessageContent() != null)
            mMessageContent.setText(mProvideInteractPatientMessage.getMessageContent());
        if (mProvideInteractPatientMessage.getPatientLinkPhone() != null)
            mMessageLinkPhone.setText("联系电话：" + mProvideInteractPatientMessage.getPatientLinkPhone());
        if (mProvideInteractPatientMessage.getReplyContent() != null)
            mMessageReply.setText(mProvideInteractPatientMessage.getReplyContent());
        mImageRecycleView = (RecyclerView) this.findViewById(R.id.rv_imageView);
        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(mContext, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mImageRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mImageRecycleView.setHasFixedSize(true);

        //创建并设置Adapter
        mAdapter = new ZHLYImageViewRecycleAdapter(mProvideBasicsImg, mContext);
        mImageRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ZHLYImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                PhotoDialogHZ photoDialog = new PhotoDialogHZ(mContext, R.style.PhotoDialog);
                photoDialog.setDate(mContext, mApp, mProvideBasicsImg, position);
                photoDialog.show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    /**
     * 提交
     */
    private void getDate() {
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideInteractPatientMessage provideInteractPatientMessage = new ProvideInteractPatientMessage();
        provideInteractPatientMessage.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractPatientMessage.setRequestClientType("1");
        provideInteractPatientMessage.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractPatientMessage.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractPatientMessage.setOrderCode(mProvideInteractOrderInfo.getOrderCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractPatientMessage);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "PatientMyDoctorControlle/searchIndexMyDoctorNotSigningResMessageText");
                    System.out.println(string + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void initListener() {
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

}
