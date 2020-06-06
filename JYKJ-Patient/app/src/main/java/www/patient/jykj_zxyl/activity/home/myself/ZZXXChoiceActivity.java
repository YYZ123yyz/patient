package www.patient.jykj_zxyl.activity.home.myself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.HZIfno;
import entity.basicDate.ProvideBasicsDomain;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.myself.JDDAQBZZRecycleAdapter;
import www.patient.jykj_zxyl.adapter.myself.ZZXXChoiceAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.Util;

/**
 * 个人中心==》建档档案==》==>症状信息==》选择症状
 */
public class ZZXXChoiceActivity extends AppCompatActivity {


    private         Context                 mContext;
    private         ZZXXChoiceActivity      mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            rv_zzxx;                //症状信息
    private         ZZXXChoiceAdapter       mZZXXChoiceAdapter;      //症状信息适配器

    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串
    private         List<ProvideBasicsDomain>  mChocieList = new ArrayList<>();
    private         List<ProvideBasicsDomain>  mList = new ArrayList<>();                   //
    private         int                     mType;

    private TextView        tv_commit;
    private         LinearLayout    li_back;
    private         TextView         tv_title;

    /**
     * 初始化布局
     */
    private void initLayout() {
        li_back = (LinearLayout)this.findViewById(R.id.li_back);
        li_back.setOnClickListener(new ButtonClick());
        tv_commit = (TextView)this.findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(new ButtonClick());
        tv_title = (TextView)this.findViewById(R.id.tv_title);
        //起病症状
        rv_zzxx = (RecyclerView) this.findViewById(R.id.rv_zzxx);
        //起病症状
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rv_zzxx.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rv_zzxx.setHasFixedSize(true);
        //创建并设置Adapter
        mZZXXChoiceAdapter = new ZZXXChoiceAdapter(mList,mContext);
        rv_zzxx.setAdapter(mZZXXChoiceAdapter);
        mZZXXChoiceAdapter.setOnItemClickListener(new ZZXXChoiceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mList.get(position).setChoice(!mList.get(position).isChoice());
                mZZXXChoiceAdapter.setDate(mList);
                mZZXXChoiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_back:
                    finish();
                    break;
                    case R.id.tv_commit:
                        List<ProvideBasicsDomain> list = new ArrayList<>();
                        for (int i = 0; i < mList.size(); i++)
                        {
                            if (mList.get(i).isChoice())
                                list.add(mList.get(i));
                        }
                        Intent intent = new Intent();
                        intent.putExtra("zzxx",(Serializable) list);
                        setResult(RESULT_OK,intent);
                        finish();
                        break;
            }
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdda_zzxx_choice);
        mContext = this;
        mActivity = this;
        mType = getIntent().getIntExtra("type",0);
        mChocieList =  (ArrayList<ProvideBasicsDomain>) getIntent().getSerializableExtra("zzxx");
        mApp = (JYKJApplication) getApplication();
        initLayout();

        switch (mType)
        {
            case 1:
                tv_title.setText("起病症状");
                break;
            case 2:
                tv_title.setText("目前症状");
                break;
            case 3:
                tv_title.setText("并发症");
                break;
            case 4:
                tv_title.setText("合并疾病");
                break;
            case 5:
                tv_title.setText("治疗方案");
                break;
        }
        initHandler();
        getDate();
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        else
                        {
                            mList = JSON.parseArray(netRetEntity.getResJsonData(),ProvideBasicsDomain.class);
                            for (int i = 0; i < mList.size(); i++)
                            {
                                for (int j = 0; j < mChocieList.size(); j++)
                                {
                                    if (mList.get(i).getAttrCode().equals(mChocieList.get(j).getAttrCode()))
                                    {
                                        mList.get(i).setChoice(true);
                                    }
                                }
                            }
                            mZZXXChoiceAdapter.setDate(mList);
                            mZZXXChoiceAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 1:
                        cacerProgress();
                        showViewDate();
                        break;
                    case 2:
                        break;
                }
            }
        };
    }



    /**
     * 设置显示
     */
    private void showViewDate() {


    }

    private String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String string = simpleDateFormat.format(data);
        return string;
    }

    /**
     * 获取数据
     */
    private void getDate() {
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    switch (mType)
                    {
                        case 1:
                            provideBasicsDomain.setBaseCode(10001);
                            break;
                        case 2:
                            provideBasicsDomain.setBaseCode(10002);
                            break;
                        case 3:
                            provideBasicsDomain.setBaseCode(10003);
                            break;
                        case 4:
                            provideBasicsDomain.setBaseCode(10004);
                            break;
                        case 5:
                            provideBasicsDomain.setBaseCode(10005);
                            break;
                    }
                    String str = new Gson().toJson(provideBasicsDomain);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                    mHandler.sendEmptyMessage(0);

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    return;
                }
            }
        }.start();
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


}
