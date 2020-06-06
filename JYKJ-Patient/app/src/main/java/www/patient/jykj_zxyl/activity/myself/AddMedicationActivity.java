package www.patient.jykj_zxyl.activity.myself;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.ProvideDrugInfo;
import entity.ProvidePatientConditionTakingMedicine;
import entity.ProvideTakingMedicineInfo;
import entity.addTaskingParment;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.ChoiceMedicationDateAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.ChoiceMedicationDateAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;

public class AddMedicationActivity extends AppCompatActivity implements View.OnClickListener {

    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串
    private                 Handler                     mHandler;

    private EditText etDrug,etRemark;
    private LinearLayout llBack;
    private TextView tvUnit,tvRate,tvTime,tvConfirm,tv_drug;
    private EditText tv_drug_useNum;
    private String drugName,remark;

    private OptionsPickerView optionPickUnit;
    private List<String> unitList;

    private OptionsPickerView optionPickRate;
    private List<String> rateList;
    private List<String> userNumLit;
    private TimePickerView timePickerView;
    private String time;

    private ProvideDrugInfo  mChoiceDrug;

    private RecyclerView mRecyclerView;
    private ChoiceMedicationDateAdapter mAdapter;
    private List<String> mData = new ArrayList<>();
    private ImageView mAddMedic;                      //添加药品
    private LinearLayout mAddDate;
    private int             taskID = 0;                     //新增= 0  修改 = 1

    private ProvideTakingMedicineInfo mProvideDrugInfo = new ProvideTakingMedicineInfo();
    private ProvidePatientConditionTakingMedicine mProvidePatientConditionTakingMedicine;
    private JYKJApplication         mApp;

    private   ImageView             mDeleteFY;                  //删除用药信息


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        mApp = (JYKJApplication) getApplication();
        mProvidePatientConditionTakingMedicine = (ProvidePatientConditionTakingMedicine) getIntent().getSerializableExtra("providePatientConditionTakingMedicines");
        if (mProvidePatientConditionTakingMedicine == null)
        {
            mProvideDrugInfo = new ProvideTakingMedicineInfo();
            taskID = 0;
        }
        else
        {
            mProvideDrugInfo = new ProvideTakingMedicineInfo();
            mProvideDrugInfo.setTakingMedicineId(mProvidePatientConditionTakingMedicine.getTakingMedicineId());
            mProvideDrugInfo.setUseDesc(mProvidePatientConditionTakingMedicine.getUseDesc());
            mProvideDrugInfo.setDrugCode(mProvidePatientConditionTakingMedicine.getDrugCode());
            mProvideDrugInfo.setDrugName(mProvidePatientConditionTakingMedicine.getDrugName());
            mProvideDrugInfo.setUseNum(Float.valueOf(mProvidePatientConditionTakingMedicine.getUseNum()));
            mProvideDrugInfo.setUseUnit(mProvidePatientConditionTakingMedicine.getUseUnit());
            mProvideDrugInfo.setTakingMedicineTime(mProvidePatientConditionTakingMedicine.getTakingMedicineTimeShow());
            mProvideDrugInfo.setUseFrequency(mProvidePatientConditionTakingMedicine.getUseFrequency());
            mProvideDrugInfo.setUseCycle(mProvidePatientConditionTakingMedicine.getUseCycle());
            taskID = 1;
        }

        initView();
        initData();
        initHandler();
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(AddMedicationActivity.this,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();

                        else
                        {
                            Toast.makeText(AddMedicationActivity.this,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                    case 3:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(AddMedicationActivity.this,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();

                        else
                        {
                            Toast.makeText(AddMedicationActivity.this,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                }
            }
        };
    }
    private void initView(){
        mDeleteFY = (ImageView)this.findViewById(R.id.delete_fy);
        mDeleteFY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFy();
            }
        });
        if (taskID == 0)
            mDeleteFY.setVisibility(View.GONE);
        else
            mDeleteFY.setVisibility(View.VISIBLE);
        tv_drug = findViewById(R.id.tv_drug);
        if (mProvideDrugInfo.getDrugName() != null && !"".equals(mProvideDrugInfo.getDrugName()))
            tv_drug.setText(mProvideDrugInfo.getDrugName());

        etRemark = findViewById(R.id.et_remark);
        if (mProvideDrugInfo.getUseDesc() != null && !"".equals(mProvideDrugInfo.getUseDesc()))
            etRemark.setText(mProvideDrugInfo.getUseDesc());

        llBack = findViewById(R.id.ll_back);
        tvUnit = findViewById(R.id.tv_drug_unit);
        if (mProvideDrugInfo.getUseUnit() != null && !"".equals(mProvideDrugInfo.getUseUnit()))
            tvUnit.setText(mProvideDrugInfo.getUseUnit());
        tvRate = findViewById(R.id.tv_rate);
        if (mProvideDrugInfo.getUseFrequency() != null && !"".equals(mProvideDrugInfo.getUseFrequency()))
            tvRate.setText("每日/"+mProvideDrugInfo.getUseFrequency()+"次");
//        tvTime = findViewById(R.id.tv_time);
        tvConfirm = findViewById(R.id.tv_confirm);
        tv_drug.setOnClickListener(this);
        llBack.setOnClickListener(this);
        tvUnit.setOnClickListener(this);
        tvRate.setOnClickListener(this);
////        tvTime.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        mAddDate = (LinearLayout)this.findViewById(R.id.tv_time);
        mRecyclerView = (RecyclerView)this.findViewById(R.id.add_yysj);
        tv_drug_useNum = (EditText)this.findViewById(R.id.tv_drug_useNum);

        if (mProvideDrugInfo.getUseNum() != null && !"".equals(mProvideDrugInfo.getUseNum()))
            tv_drug_useNum.setText(mProvideDrugInfo.getUseNum()+"");

        mAddDate.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        if (mProvideDrugInfo.getTakingMedicineTime() != null)
        {
            String [] time = mProvideDrugInfo.getTakingMedicineTime().split("\\^");
            if (time != null)
            {
                for (int i = 0; i < time.length; i++)
                {
                    mData.add(time[i]);
                }
            }
        }
        mAdapter = new ChoiceMedicationDateAdapter(mData, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ChoiceMedicationDateAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mData.remove(position);
                mAdapter.setDate(mData);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    /**
     * 删除用药信息
     */
    private void deleteFy() {
        ProvideTakingMedicineInfo provideTakingMedicineInfo = new ProvideTakingMedicineInfo();
        provideTakingMedicineInfo.setTakingMedicineId(mProvideDrugInfo.getTakingMedicineId());
        provideTakingMedicineInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideTakingMedicineInfo.setRequestClientType("1");
        provideTakingMedicineInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideTakingMedicineInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        getProgressBar("请稍候", "正在提交");
        //连接网络，登录
        new Thread() {
            public void run() {
                try {
                    String string = JSON.toJSONString(provideTakingMedicineInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideTakingMedicineInfo), Constant.SERVICEURL + "PatientConditionControlle/operDelPatientConditionTakingMedicineSingle");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 10000) {
            mChoiceDrug = (ProvideDrugInfo) data.getSerializableExtra("medicInfo");
            tv_drug.setText(mChoiceDrug.getDrugName());
            mProvideDrugInfo.setDrugName(mChoiceDrug.getDrugName());
            if (mChoiceDrug.getDrugCode() == null || "".equals(mChoiceDrug.getDrugCode()))
                mProvideDrugInfo.setDrugCode(0);
            else
                mProvideDrugInfo.setDrugCode(Integer.valueOf(mChoiceDrug.getDrugId()));
            if (mChoiceDrug.getDrugUnit() != null && !"".equals(mChoiceDrug.getDrugUnit()))
            {
                tvUnit.setText(mChoiceDrug.getDrugUnit());
                mProvideDrugInfo.setUseUnit(mChoiceDrug.getDrugUnit());
            }

//            mProvideDrugInfo.setDrugSpec(mChoiceDrug.getDrugSpec());
//            mProvideDrugInfo.setDrugUnit(mChoiceDrug.getDrugUnit());
        }
    }
    private void initData(){
        initRateData();
        initUnitData();
        initUseNumList();
    }

    private void initUnitData(){
        unitList = new ArrayList<>();
        unitList.add("片");
        unitList.add("粒");
        unitList.add("颗");
        unitList.add("袋");
        unitList.add("瓶");
        unitList.add("滴");
        unitList.add("mg");
        unitList.add("g");
        unitList.add("ml");
        unitList.add("U");
        unitList.add("u");

    }

    private void initRateData(){
        rateList = new ArrayList<>();
        rateList.add("每日/1次");
        rateList.add("每日/2次");
        rateList.add("每日/3次");
        rateList.add("每日/4次");
        rateList.add("每日/5次");
    }

    private void initUseNumList(){
        userNumLit = new ArrayList<>();
        userNumLit.add("1");
        userNumLit.add("2");
        userNumLit.add("3");
        userNumLit.add("4");
        userNumLit.add("5");
        userNumLit.add("6");
        userNumLit.add("7");
        userNumLit.add("8");
        userNumLit.add("9");
        userNumLit.add("10");

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_drug:
                startActivityForResult(new Intent(AddMedicationActivity.this,ChoiceMedicationActivity.class),10000);
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_drug_unit:
                optionPickUnit = new OptionsPickerBuilder(AddMedicationActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvUnit.setText(unitList.get(options1));
                        mProvideDrugInfo.setUseUnit(unitList.get(options1));
                    }
                }).setSelectOptions(0).build();
                optionPickUnit.setPicker(unitList);
                optionPickUnit.show();
                break;
            case R.id.tv_rate:
                optionPickRate = new OptionsPickerBuilder(AddMedicationActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvRate.setText(rateList.get(options1));
                        mProvideDrugInfo.setUseFrequency(options1+1);
                    }
                }).setSelectOptions(0).build();
                optionPickRate.setPicker(rateList);
                optionPickRate.show();
                break;
            case R.id.tv_time:
                timePickerView = new TimePickerBuilder(AddMedicationActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        time = getDate(date);
                        mData.add(time);
                        mAdapter.setDate(mData);
                        mAdapter.notifyDataSetChanged();
                    }
                }).setType(new boolean[]{false, false, false, true, true, false})
                        .setLabel("", "", "", "时", "分", "").build();
                timePickerView.show();
                break;

            case R.id.tv_confirm:
                commit();
                break;
        }

    }

    /**
     * 提交
     */
    private void commit() {
        String str = "";
        for (int i = 0; i < mData.size(); i++)
        {
            if (i == mData.size() -1)
                str += mData.get(i);
            else
                str += mData.get(i)+"^";
        }
        mProvideDrugInfo.setTakingMedicineTime(str);
        mProvideDrugInfo.setUseDesc(etRemark.getText().toString());
        mProvideDrugInfo.setTakingMedicineId(taskID);
        if (tv_drug_useNum.getText().toString() == null || "".equals(tv_drug_useNum.getText().toString()))
        {
            Toast.makeText(AddMedicationActivity.this,"用药数量不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideDrugInfo.getDrugName() == null || "".equals(mProvideDrugInfo.getDrugName()))
        {
            Toast.makeText(AddMedicationActivity.this,"请选择药品",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideDrugInfo.getUseUnit() == null || "".equals(mProvideDrugInfo.getUseUnit()))
        {
            Toast.makeText(AddMedicationActivity.this,"请选择用药单位",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideDrugInfo.getUseFrequency() == null || "".equals(mProvideDrugInfo.getUseFrequency()))
        {
            Toast.makeText(AddMedicationActivity.this,"请选择用药频率",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideDrugInfo.getTakingMedicineTime() == null || "".equals(mProvideDrugInfo.getTakingMedicineTime()))
        {
            Toast.makeText(AddMedicationActivity.this,"请选择用药时间",Toast.LENGTH_SHORT).show();
            return;
        }
        mProvideDrugInfo.setUseNum(Float.valueOf(tv_drug_useNum.getText().toString()));
        List<ProvideTakingMedicineInfo> medicineInfoList = new ArrayList<>();
        medicineInfoList.add(mProvideDrugInfo);
        addTaskingParment add = new addTaskingParment();
        add.setLoginPatientPosition(mApp.loginDoctorPosition);
        add.setRequestClientType("1");
        add.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        add.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        add.setRecordUserType("1");
        add.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        add.setTakingMedicineType("1");
        add.setTakingMedicineInfoList(medicineInfoList);
        getProgressBar("请稍候", "正在提交");

        //连接网络，登录
        new Thread() {
            public void run() {
                try {
                    String jsonStr = new Gson().toJson(add);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(add), Constant.SERVICEURL + "PatientConditionControlle/operUpdPatientConditionTakingMedicineList");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    private String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "HH:mm");
        String string = simpleDateFormat.format(data);
        return string;
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
