package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.ActivityUtil;

/*
* 病历
*
* Drug_Rv_Adapter
*
* */
public class MedicalRecordActivity extends AppCompatActivity {

    private TagFlowLayout diagFlow;
    private TagFlowLayout checkFlow;
    private TagFlowLayout prescrFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setStatusBarMain(this);
        setContentView(R.layout.activity_medical_record);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        diagFlow= findViewById(R.id.id_flowlayout);
        checkFlow= findViewById(R.id.flowlayout_check);
        prescrFlow= findViewById(R.id.flowlayout_prescr);
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicalRecordActivity.this,CheckListActivity.class));
            }
        });
        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicalRecordActivity.this,PrescriptionDetActivity.class));
            }
        });

    }

    private void initData() {
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
}