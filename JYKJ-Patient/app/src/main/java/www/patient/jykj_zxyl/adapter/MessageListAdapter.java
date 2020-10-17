package www.patient.jykj_zxyl.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.myappointment.bean.ViewInteractPatientMessageBean;
import www.patient.jykj_zxyl.util.DateUtils;

/**
 * 医生回复消息的适配器
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    private List<ViewInteractPatientMessageBean.InteractPatientMessageActiveListBean> datas;

    public MessageListAdapter(List<ViewInteractPatientMessageBean.InteractPatientMessageActiveListBean> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messagelist, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewInteractPatientMessageBean.InteractPatientMessageActiveListBean interactPatientMessageActiveListBean = datas.get(position);
        if (interactPatientMessageActiveListBean != null) {
            holder.notlin.setVisibility(View.GONE);
            holder.lin.setVisibility(View.VISIBLE);
            //医生姓名
            holder.dector_name.setText(interactPatientMessageActiveListBean.getDoctorName());
            //回复类型
            int flagMessageType = interactPatientMessageActiveListBean.getFlagMessageType();
            if (flagMessageType == 1) {
                holder.message_type.setText("主动发起");
            } else {
                holder.message_type.setText("留言回复");
            }
            //日期
            long doctorReplyDate = interactPatientMessageActiveListBean.getDoctorReplyDate();
            String stringTimeOfSSS = DateUtils.getStringTimeOfSSS(doctorReplyDate);
            holder.date.setText(stringTimeOfSSS);
            //消息类型
            int flagDoctorReplyType = interactPatientMessageActiveListBean.getFlagDoctorReplyType();
            if (flagDoctorReplyType == 1) {
                holder.type.setText("正常");
            } else if (flagDoctorReplyType == 2) {
                holder.type.setText("一般");
            } else if (flagDoctorReplyType == 3) {
                holder.type.setText("紧急");
            } else if (flagDoctorReplyType == 4) {
                holder.type.setText("重大紧急");
            }
            String doctorReplyContent = interactPatientMessageActiveListBean.getDoctorReplyContent();
            if (!TextUtils.isEmpty(doctorReplyContent)) {
                holder.content.setText(doctorReplyContent);
            }

        } else {
            holder.lin.setVisibility(View.GONE);
            holder.notlin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dector_name;
        private TextView message_type;
        private TextView date;
        private TextView type;
        private TextView content;
        private LinearLayout lin,notlin;

        public ViewHolder(View itemView) {
            super(itemView);
            dector_name = itemView.findViewById(R.id.dector_name);
            message_type = itemView.findViewById(R.id.message_type);
            date = itemView.findViewById(R.id.date);
            type = itemView.findViewById(R.id.type);
            content = itemView.findViewById(R.id.content);
            lin = itemView.findViewById(R.id.lin);
            notlin = itemView.findViewById(R.id.notlin);
        }
    }
}
