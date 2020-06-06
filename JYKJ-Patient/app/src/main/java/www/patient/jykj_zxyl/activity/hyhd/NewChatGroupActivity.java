package www.patient.jykj_zxyl.activity.hyhd;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import www.patient.jykj_zxyl.adapter.DorcerFriendExpandableListViewAdapter;
import www.patient.jykj_zxyl.util.NestedExpandaleListView;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.DorcerFriendExpandableListViewAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.NestedExpandaleListView;

/**
 * 建群
 */
public class NewChatGroupActivity extends AppCompatActivity {


    private             Context                             mContext;
    private             NewChatGroupActivity                mActivity;
    private             Handler                             mHandler;
    private             JYKJApplication                     mApp;

    private NestedExpandaleListView mYSHY;                                  //医生好友
    private DorcerFriendExpandableListViewAdapter mDorcerFriendExpandableListViewAdapter;   //医生好友适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newchatgroup);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBar(mActivity);
        initLayout();

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
//        mYSHY = (NestedExpandaleListView)this.findViewById(R.id.rv_fragmethyhd_yshyInfo);
//        mDorcerFriendExpandableListViewAdapter = new DorcerFriendExpandableListViewAdapter(mInteractDoctorUnionInfo,mContext,mApp);
//        mYSHY.setAdapter(mDorcerFriendExpandableListViewAdapter);
//        mYSHY.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                mInteractDoctorUnionInfo.get(groupPosition).setClick(!mInteractDoctorUnionInfo.get(groupPosition).isClick());
//                mDorcerFriendExpandableListViewAdapter.setDate(mInteractDoctorUnionInfo);
//                mDorcerFriendExpandableListViewAdapter.notifyDataSetInvalidated();
//                mDorcerFriendExpandableListViewAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
//        mYSHY.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                Intent intent = new Intent();
//                intent.setClass(mContext,ChatActivity.class);
//                intent.putExtra("userCode",mInteractDoctorUnionInfo.get(i).getDoctorUnionPersonnelList().get(i1).getPatientCode());
//                intent.putExtra("userName",mInteractDoctorUnionInfo.get(i).getDoctorUnionPersonnelList().get(i1).getDoctorUserName());
//                startActivity(intent);
//                return false;
//            }
//        });

    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }



}
