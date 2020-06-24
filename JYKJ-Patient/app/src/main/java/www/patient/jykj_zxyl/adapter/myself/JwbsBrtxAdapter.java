package www.patient.jykj_zxyl.adapter.myself;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liyi.grid.AutoGridView;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import indi.liyi.viewer.ImageViewer;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.jyzl.JWBSDetailActivity;
import www.patient.jykj_zxyl.adapter.ImgAutogridAdapter;
import www.patient.jykj_zxyl.liyi.PhotoLoader;
import www.patient.jykj_zxyl.liyi.glide.GlideUtil;
import www.patient.jykj_zxyl.util.Util;

import java.util.ArrayList;
import java.util.List;

public class JwbsBrtxAdapter extends RecyclerView.Adapter<JwbsBrtxAdapter.ViewHolder>{
    List<ProvidePatientConditionDiseaseRecord> beanstrarr = new ArrayList();
    List<String> imgsarr = new ArrayList();
    private ImageViewer imageViewer;
    private JWBSDetailActivity parcontext;
    public JwbsBrtxAdapter(JWBSDetailActivity context,List<ProvidePatientConditionDiseaseRecord> beanstrarr,List<String> imgsarr,ImageViewer imageViewer){
        this.imageViewer = imageViewer;
        this.beanstrarr = beanstrarr;
        this.imgsarr = imgsarr;
        this.parcontext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holdv = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_jyzl_grzl_grzk_jwbsdetail,parent,false);
        return new ViewHolder(holdv);
    }

    public void setDatas(List<ProvidePatientConditionDiseaseRecord> beanstrarr,List<String> imgsarr){
        this.beanstrarr = beanstrarr;
        this.imgsarr = imgsarr;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProvidePatientConditionDiseaseRecord mProvidePatientConditionDiseaseRecords = beanstrarr.get(position);
        holder.mBCBM.setText(mProvidePatientConditionDiseaseRecords.getRecordName());
        holder.mJZRQ.setText(Util.dateToStrDate(mProvidePatientConditionDiseaseRecords.getTreatmentDate()));
        holder.mBCFL.setText(mProvidePatientConditionDiseaseRecords.getRecordTypeName());
        holder.mBQZS.setText(mProvidePatientConditionDiseaseRecords.getRecordContent());
        ImgAutogridAdapter imgAdapter = new ImgAutogridAdapter();
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


        holder.autoGv.setOnItemClickListener(new AutoGridView.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View view) {
            imageViewer.bindViewGroup(holder.autoGv);
            imageViewer.watch(position);
        }
    });
        holder.autoGv.setAdapter(imgAdapter);
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
        return beanstrarr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout mBack;
        public TextView mBCBM;
        public TextView mJZRQ;
        public TextView mBCFL;
        public TextView mBQZS;
        public AutoGridView autoGv;
        public ViewHolder(View view){
            super(view);
            mBack = (RelativeLayout)view.findViewById(R.id.back);
            mBCBM = (TextView)view.findViewById(R.id.tv_activityHZZL_userSG);
            mJZRQ = (TextView)view.findViewById(R.id.tv_activityHZZL_userYW);
            mBCFL = (TextView)view.findViewById(R.id.tv_activityHZZL_userTZ);
            mBQZS = (TextView)view.findViewById(R.id.tv_activityHZZL_region);
            autoGv = view.findViewById(R.id.autoGridView);
        }

    }
}
