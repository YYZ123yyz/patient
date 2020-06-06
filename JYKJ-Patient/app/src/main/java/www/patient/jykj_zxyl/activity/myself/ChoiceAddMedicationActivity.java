package www.patient.jykj_zxyl.activity.myself;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;

/**
 * 服药设置 == 》选择药品 ==>添加药品
 */
public class ChoiceAddMedicationActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private EditText mDicName;                      //药品名称
    private TextView   mCommit;                       //提交






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_add_medication);
        initView();
        initData();
    }

    private void initView(){
        llBack = (LinearLayout)this.findViewById(R.id.ll_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDicName = (EditText)this.findViewById(R.id.medic_name);
        mCommit = (TextView)this.findViewById(R.id.commit);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("medicName",mDicName.getText().toString());
                setResult(-1,intent);
                finish();
            }
        });

    }

    private void initData(){


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.ll_back:
                finish();
                break;

        }

    }


}
