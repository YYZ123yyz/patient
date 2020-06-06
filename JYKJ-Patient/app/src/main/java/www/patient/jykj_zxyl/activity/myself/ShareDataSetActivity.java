package www.patient.jykj_zxyl.activity.myself;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.ActivityUtil;

/**
 * 我的排班
 */
public class ShareDataSetActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 ShareDataSetActivity        mActivity;
    private                 LinearLayout                mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmentself_sharedataset);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        setData();
    }



    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout) findViewById(R.id.ll_back);
        mBack.setOnClickListener(new ButtonClick());
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;
            }
        }
    }

    /**
     * 设置数据
     */
    private void setData() {

    }



}
