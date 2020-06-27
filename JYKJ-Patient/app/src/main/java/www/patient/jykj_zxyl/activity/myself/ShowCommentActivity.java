package www.patient.jykj_zxyl.activity.myself;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ToggleButton;
import entity.mySelf.CommentInfo;
import entity.mySelf.MyOrderProcess;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.StrUtils;

public class ShowCommentActivity extends AppCompatActivity {
    private CommentActivity mActivity;
    private Context mContext;
    private JYKJApplication mApp;
    private CommentInfo mComment;
    private MyOrderProcess myorder;
    private TextView tv_comm_order_no;
    private TextView tv_comm_treat_type;
    private TextView tv_comm_treat_date;
    private TextView tv_comm_content;
    private ToggleButton good_comment;
    private ToggleButton middle_comment;
    private ToggleButton weak_comment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComment = (CommentInfo)getIntent().getSerializableExtra("commentinfo");
        myorder = (MyOrderProcess)getIntent().getSerializableExtra("orderinfo");
        setContentView(R.layout.show_opinion);
        tv_comm_order_no = findViewById(R.id.tv_comm_order_no);
        tv_comm_treat_type = findViewById(R.id.tv_comm_treat_type);
        tv_comm_treat_date = findViewById(R.id.tv_comm_treat_date);
        tv_comm_content = findViewById(R.id.tv_comm_content);
        good_comment = findViewById(R.id.good_comment);
        middle_comment = findViewById(R.id.middle_comment);
        weak_comment = findViewById(R.id.weak_comment);
        tv_comm_order_no.setText(StrUtils.defaultStr(myorder.getOrderCode()));
        if(null!=myorder.getOrderDate()) {
            tv_comm_treat_type.setText(DateUtils.fomrDateSeflFormat(myorder.getOrderDate(),"yyyy-MM-dd HH:mm"));
        }
        tv_comm_treat_type.setText(StrUtils.defaultStr(myorder.getTreatmentTypeName()));
        tv_comm_content.setText(StrUtils.defaultStr(mComment.getCommentContent()));
        if(null!=mComment.getCommentType() && 1==mComment.getCommentType()){
            good_comment.setChecked(true);
        }else if(null!=mComment.getCommentType() && 2==mComment.getCommentType()){
            middle_comment.setChecked(true);
        }else if(null!=mComment.getCommentType() && 3==mComment.getCommentType()){
            weak_comment.setChecked(true);
        }
    }
}
