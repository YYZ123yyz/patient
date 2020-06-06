package www.patient.jykj_zxyl.activity.home.tjhz;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import entity.unionInfo.ProvideViewUnionDoctorMemberApplyInfo;
import www.patient.jykj_zxyl.R;

/**
 * 添加患者==>处理审核
 */
    public class ApplicationAuditDealActivity extends AppCompatActivity {

        private             Context                     mContext;
        private             RecyclerView                mApplicationList;                  //申请审核

        private ProvideViewUnionDoctorMemberApplyInfo   mProvideViewUnionDoctorMemberApplyInfo;
        private             TextView                    mApplyDateText;                     //申请时间
//        private             TextView                    mApplyDateText;                     //申请状态
//        private             TextView                    mApplyDateText;                     //申请者姓名
//        private             TextView                    mApplyDateText;                     //申请提示
        private             Button                      mAgreeBt;                           //通过按钮
        private             TextView                    mReasonTitle;                     //拒绝理由标题
        private             EditText                    mReasonEdit;                     //拒绝理由
        private             Button                      mRefundBt;                          //拒绝按钮

        @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationauditdeal);
        mContext = this;
        mProvideViewUnionDoctorMemberApplyInfo = (ProvideViewUnionDoctorMemberApplyInfo) getIntent().getSerializableExtra("applyInfo");
//        mProvideViewUnionDoctorMemberApplyInfo = (ProvideViewUnionDoctorMemberApplyInfo) JSON.parse(string);
        initLayout();
    }

    /**
     * 初始化界面
     */
    private void initLayout() {
        mApplicationList = (RecyclerView) this.findViewById(R.id.rv_activityApplicationAudit_applicationList);


    }



    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_activityAddPatient_applicationAudit:
                    break;

            }
        }
    }


}
