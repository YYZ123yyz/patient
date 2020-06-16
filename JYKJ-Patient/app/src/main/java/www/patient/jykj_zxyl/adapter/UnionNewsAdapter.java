package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entity.ProvideMsgPushReminder;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.Util;

public class UnionNewsAdapter extends RecyclerView.Adapter<UnionNewsAdapter.ViewHolder>{

    private         List<ProvideMsgPushReminder>   mDate = new ArrayList<>();
    private         Context                         mContext;
    private         OnItemClickListener mOnItemClickListener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_union_news_list,parent,false);
        UnionNewsAdapter.ViewHolder vh = new UnionNewsAdapter.ViewHolder(view);
        return vh;
    }

    public UnionNewsAdapter(Context context){
        mContext = context;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println(mDate.get(position).getMsgContent());
        holder.mMessageType.setText(mDate.get(position).getMsgOperName());
        holder.msgOperName.setText(mDate.get(position).getMsgTitleName());
//        if (mDate.get(position).getMsgCreateDate() == null )
//            holder.mMessageDatet.setText("未设置");
//        else
            holder.mMessageDatet.setText(mDate.get(position).getSendMsgDate());

        if (mOnItemClickListener != null)
        {
            holder.mClickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.mClickLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    /**
     * 设置数据
     * @param mMsgPushReminders
     */
    public void setdate(List<ProvideMsgPushReminder> mMsgPushReminders) {
        mDate = mMsgPushReminders;
    }


    public void setDate(List<ProvideMsgPushReminder> mList) {
        mDate = mList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView   mHead;
        private TextView    mUserName;
        private TextView    mMessageType;
        private TextView    mMessageDatet;
        private LinearLayout    mClickLayout;
        private TextView        msgOperName;


        public ViewHolder(View view){
            super(view);
            mHead = (ImageView)view.findViewById(R.id.iv_itemNewsAdapter_head);
//            mUserName = (TextView)view.findViewById(R.id.tv_itemNewsAdapter_userNameText);
            mMessageType = (TextView)view.findViewById(R.id.tv_itemNewsAdapter_userMessageText);
            mMessageDatet = (TextView)view.findViewById(R.id.tv_itemNewMessageAdapter_userDateText);
            msgOperName = (TextView)view.findViewById(R.id.msgOperName);
            mClickLayout = (LinearLayout)view.findViewById(R.id.click);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
