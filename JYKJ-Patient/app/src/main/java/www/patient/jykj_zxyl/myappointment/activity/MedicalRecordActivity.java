package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.presenter.MedicalRecordPresenter;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.view.MedicalRecordView;

/*
* 病历
*
* Drug_Rv_Adapter
*
* */
public class MedicalRecordActivity extends AbstractMvpBaseActivity<MedicalRecordView, MedicalRecordPresenter> {

    @BindView(R.id.id_flowlayout)
    TagFlowLayout diagFlow; //临床诊断
    @BindView(R.id.flowlayout_check)
    TagFlowLayout checkFlow;//检查检验
    @BindView(R.id.flowlayout_prescr)
    TagFlowLayout prescrFlow;//处方笺

    @Override
    protected int setLayoutId() {
        return R.layout.activity_medical_record;
    }

    @OnClick({R.id.confirm,R.id.download,R.id.lin_chief,R.id.lin_history,
            R.id.lin_past,R.id.lin_examination,R.id.lin_look,R.id.lin_suggest})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.confirm:
                startActivity(new Intent(MedicalRecordActivity.this,CheckListActivity.class));
                break;
            case R.id.download:
                startActivity(new Intent(MedicalRecordActivity.this,PrescriptionDetActivity.class));
                break;
        }
    }


    protected void initData() {
        LayoutInflater mInflater = LayoutInflater.from(this);

        ArrayList<String> diagStrngs = new ArrayList<>();
        ArrayList<String> checkStrngs = new ArrayList<>();
        ArrayList<String> prescrStrngs = new ArrayList<>();

        diagStrngs.add("诊断1");
        diagStrngs.add("诊断2");
        diagStrngs.add("诊断3");

        checkStrngs.add("高血压预防");
        checkStrngs.add("诊断");
        checkStrngs.add("治疗");
        checkStrngs.add("康复");
        checkStrngs.add("营养");
        checkStrngs.add("营养科技哦哦哦");

        prescrStrngs.add("999感冒灵");
        prescrStrngs.add("药品1");
        prescrStrngs.add("999感000冒灵");
        diagFlow.setAdapter(new TagAdapter<String>(diagStrngs) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                        diagFlow, false);
                tv.setText(s);
                return tv;
            }
        });

        checkFlow.setAdapter(new TagAdapter<String>(checkStrngs) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                        checkFlow, false);
                tv.setText(s);
                return tv;
            }
        });

        prescrFlow.setAdapter(new TagAdapter<String>(prescrStrngs) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                        prescrFlow, false);
                tv.setText(s);
                return tv;
            }
        });

    }

    @Override
    public void showLoading(int code) {

    }
}