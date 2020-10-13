package www.patient.jykj_zxyl.adapter.patient.fragmentShouYe;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.allin.commlibrary.encode.Base64Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import entity.patientapp.Photo_Info;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.BitmapUtil;
import www.patient.jykj_zxyl.util.ImageViewUtil;
import www.patient.jykj_zxyl.util.StrUtils;


public class ImageViewRecycleAdapter extends RecyclerView.Adapter<ImageViewRecycleAdapter.ViewHolder> {
    public List<Photo_Info> datas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private JYKJApplication mApp;
    private boolean mCanClick = true;

    public ImageViewRecycleAdapter(List<Photo_Info> list, JYKJApplication app) {
        datas = new ArrayList<>();
        datas.addAll(list);
        mApp = app;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycleview_imageview, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        /**
         * 强制关闭复用，否则出现数据混乱
         */
        viewHolder.setIsRecyclable(false);
        if ("ADDPHOTO".equals(datas.get(position).getPhotoID())) {
            viewHolder.mImageView.setBackgroundResource(R.mipmap.addphoto);
            Log.i("执行", "执行Add:" + position);
        } else if (datas.get(position).getPhoto() != null) {
            viewHolder.mImageView.setImageBitmap(BitmapUtil.stringtoBitmap(datas.get(position).getPhoto()));
            Log.i("执行", "执行:" + position);
        } else if (StrUtils.defaultStr(datas.get(position).getPhotoUrl()).length() > 0) {
            Glide.with(viewHolder.mImageView.getContext()).load(datas.get(position).getPhotoUrl())
                    .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(viewHolder.mImageView);

        }


        if (mOnItemClickListener != null) {
            viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCanClick){
                        mOnItemClickListener.onClick(position);
                    }

                }
            });

            viewHolder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    if (mCanClick){
                        mOnItemClickListener.onLongClick(position);
                    }
                    return false;
                }
            });
        }
        viewHolder.mImageView.setClickable(mCanClick);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 重置数据
     *
     * @param mPhotoInfos
     */
    public void setDate(List<Photo_Info> mPhotoInfos) {
        datas.clear();
        datas.addAll(mPhotoInfos);
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;            //

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_itempublishActivity_addPicture);
        }
    }


    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setCanClick(boolean canClick) {
        this.mCanClick = canClick;
    }
}