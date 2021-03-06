package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entity.ProvideViewInteractPatientComment;
import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.Util;

/**
 * 医生评论
 */
public class DoctorCommentsRecycleAdapter extends RecyclerView.Adapter<DoctorCommentsRecycleAdapter.ViewHolder> {

    private         ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    public          List<ProvideViewInteractPatientComment>            datas = new ArrayList<>();
    private         MainActivity                    mActivity;
    private         OnItemClickListener             mOnItemClickListener;
    private         Context                         mContext;
    private         JYKJApplication                 mApp;


    public DoctorCommentsRecycleAdapter(List<ProvideViewInteractPatientComment> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideViewInteractPatientComment> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activityzjtj_doctorcomment,viewGroup,false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
       }
        //将数据与界面进行绑定的操作

    /**
     * 展示数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        try {
            int avatarResId = Integer.parseInt(datas.get(position).getDoctorUserLogoUrl());
            Glide.with(mContext).load(avatarResId).into(viewHolder.mImageView);
        } catch (Exception e) {
            //use default avatar
            Glide.with(mContext).load(datas.get(position).getDoctorUserLogoUrl())
                    .apply(RequestOptions.placeholderOf(R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(viewHolder.mImageView);
        }
        viewHolder.mUserName.setText(datas.get(position).getPatientUserName());
        viewHolder.mDate.setText(Util.dateToStr(datas.get(position).getCommentDate()));
        viewHolder.mMessage.setText(datas.get(position).getCommentContent());


        if (mOnItemClickListener != null)
        {
            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            public ImageView    mImageView;                             // 头像
            public TextView     mUserName;
            public TextView     mMessage;
            public TextView     mDate;

            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_itemFragmentHYHD_clickLayout);
                mUserName = (TextView)view.findViewById(R.id.tv_itemMessageAdapter_userNameText);
                mDate = (TextView)view.findViewById(R.id.tv_itemMessageAdapter_userDateText);
                mMessage = (TextView)view.findViewById(R.id.tv_itemMessageAdapter_userMessageText);
                mImageView = (ImageView)view.findViewById(R.id.iv_itemMessageAdapter_head);
            }
        }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    /**
     * 图片加载第一次显示监听器
     * @author Administrator
     *
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}