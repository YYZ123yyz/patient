package www.patient.jykj_zxyl.adapter.myself;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liyi.grid.AutoGridView;
import entity.mySelf.ZhlyDetailInfo;
import entity.mySelf.ZhlyImgInfo;
import indi.liyi.viewer.ImageViewer;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.LeaveMessageShowActivity;
import www.patient.jykj_zxyl.adapter.ImgAutogridAdapter;
import www.patient.jykj_zxyl.liyi.PhotoLoader;
import www.patient.jykj_zxyl.liyi.glide.GlideUtil;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.StrUtils;

import java.util.ArrayList;
import java.util.List;

public class ZhlyShowAdapter extends RecyclerView.Adapter<ZhlyShowAdapter.ViewHolder>{
    private List<ZhlyDetailInfo> detaildatas = new ArrayList();
    private List<ZhlyImgInfo> imgdatas = new ArrayList();
    private LeaveMessageShowActivity parcontext;
    private ImageViewer imageViewer;
    public ZhlyShowAdapter(LeaveMessageShowActivity parcontext, List<ZhlyDetailInfo> detaildatas, List<ZhlyImgInfo> imgdatas, ImageViewer imageViewer){
        this.parcontext = parcontext;
        this.detaildatas = detaildatas;
        this.imgdatas = imgdatas;
        this.imageViewer = imageViewer;
    }

    public void setDatas(List<ZhlyDetailInfo> detaildatas,List<ZhlyImgInfo> imgdatas){
        this.detaildatas = detaildatas;
        this.imgdatas = imgdatas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leavemsg_after_threatment_show,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ZhlyDetailInfo detailInfo = detaildatas.get(position);
        holder.tv_zhly_treatment_type.setText(StrUtils.defaultStr(detailInfo.getTreatmentTypeName()));
        if(null!=detailInfo.getMessageDate()) {
            holder.tv_zhly_date.setText(DateUtils.fomrDateSeflFormat(detailInfo.getMessageDate(),"yyyy-MM-dd HH:mm"));
        }
        holder.tv_zhly_linkphone.setText(StrUtils.defaultStr(detailInfo.getPatientLinkPhone()));
        holder.tv_zhly_content.setText(StrUtils.defaultStr(detailInfo.getMessageContent()));
        holder.tv_zhly_replydoctor.setText(StrUtils.defaultStr(detailInfo.getDoctorName()));
        if(null!=detailInfo.getReplyDate()) {
            holder.tv_zhly_replydate.setText(DateUtils.fomrDateSeflFormat(detailInfo.getReplyDate(),"yyyy-MM-dd HH:mm"));
        }
        holder.tv_zhly_replytype.setText(StrUtils.defaultStr(detailInfo.getFlagReplyTypeName()));
        holder.tv_zhly_replycontent.setText(StrUtils.defaultStr(detailInfo.getReplyContent()));
        ImgAutogridAdapter imgAdapter = new ImgAutogridAdapter();
        List<String> imgsarr = new ArrayList();
        for(int i=0;i<imgdatas.size();i++){
            ZhlyImgInfo parimg = imgdatas.get(i);
            imgsarr.add(parimg.getImgUrl());
        }
        if(imgsarr.size()>0){
            imageViewer.overlayStatusBar(false)
                    .imageData(imgsarr)
                    .imageLoader(new PhotoLoader());
            imgAdapter.setSource(imgsarr);
            imgAdapter.setImageLoader(new ImgAutogridAdapter.ImageLoader() {
                @Override
                public void onLoadImage(final int position, Object source, final ImageView imageView, int viewType) {
                    GlideUtil.loadImage(parcontext, source, new SimpleTarget<Drawable>() {

                        @Override
                        public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            imageViewer.getViewData().get(position).setImageWidth(resource.getIntrinsicWidth());
                            imageViewer.getViewData().get(position).setImageHeight(resource.getIntrinsicHeight());
                            imageView.setImageDrawable(resource);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            imageView.setImageDrawable(errorDrawable);
                        }
                    });
                }
            });


            holder.zhlyGridView.setOnItemClickListener(new AutoGridView.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    imageViewer.bindViewGroup(holder.zhlyGridView);
                    imageViewer.watch(position);
                }
            });
            holder.zhlyGridView.setAdapter(imgAdapter);
        }
        holder.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parcontext.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return detaildatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mBack;
        public TextView tv_zhly_treatment_type;
        public TextView tv_zhly_date;
        public TextView tv_zhly_linkphone;
        public TextView tv_zhly_content;
        public AutoGridView zhlyGridView;
        public TextView tv_zhly_replydoctor;
        public TextView tv_zhly_replydate;
        public TextView tv_zhly_replytype;
        public TextView tv_zhly_replycontent;
        public ViewHolder(View view){
            super(view);
            mBack = view.findViewById(R.id.back);
            tv_zhly_treatment_type = view.findViewById(R.id.tv_zhly_treatment_type);
            tv_zhly_date = view.findViewById(R.id.tv_zhly_date);
            tv_zhly_linkphone = view.findViewById(R.id.tv_zhly_linkphone);
            tv_zhly_content = view.findViewById(R.id.tv_zhly_content);
            zhlyGridView = view.findViewById(R.id.zhlyGridView);
            tv_zhly_replydoctor = view.findViewById(R.id.tv_zhly_replydoctor);
            tv_zhly_replydate = view.findViewById(R.id.tv_zhly_replydate);
            tv_zhly_replytype = view.findViewById(R.id.tv_zhly_replytype);
            tv_zhly_replycontent = view.findViewById(R.id.tv_zhly_replycontent);
        }
    }
}
