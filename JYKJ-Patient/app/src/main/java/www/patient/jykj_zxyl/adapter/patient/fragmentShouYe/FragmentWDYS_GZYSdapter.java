package www.patient.jykj_zxyl.adapter.patient.fragmentShouYe;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import entity.shouye.ProvideViewDoctorExpertRecommend;
import www.patient.jykj_zxyl.R;

/**
 * 首页==》我的医生 == 关注医生
 */
public class FragmentWDYS_GZYSdapter extends RecyclerView.Adapter<FragmentWDYS_GZYSdapter.ViewHolder> {
    public List<ProvideViewDoctorExpertRecommend> datas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;           //用户资料点击事件
    private OnItemClickQXGZListener mOnItemClickQXGZListener;           //用户资料点击事件


    private Context mContext;


    public FragmentWDYS_GZYSdapter(List<ProvideViewDoctorExpertRecommend> list, Context context) {
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideViewDoctorExpertRecommend> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragmentshouyewdys_gzys, viewGroup, false);
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
            int avatarResId = Integer.parseInt(datas.get(position).getUserLogoUrl());
            Glide.with(mContext).load(avatarResId).into(viewHolder.mUserHeard);
        } catch (Exception e) {
            //use default avatar
            Glide.with(mContext).load(datas.get(position).getUserLogoUrl())
                    .apply(RequestOptions.placeholderOf(R.mipmap.tx_nh)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(viewHolder.mUserHeard);
        }

        if (datas.get(position).getUserName() == null || "".equals(datas.get(position).getUserName()))
            viewHolder.mUserName.setText("未设置");
        else
            viewHolder.mUserName.setText(datas.get(position).getUserName());
        if (datas.get(position).getDoctorTitleName() == null || "".equals(datas.get(position).getDoctorTitleName()))
            viewHolder.mUserTitle.setText("未设置");
        else
            viewHolder.mUserTitle.setText(datas.get(position).getDoctorTitleName() + "|" + datas.get(position).getDepartmentSecondName());
        if (datas.get(position).getHospitalInfoName() == null || "".equals(datas.get(position).getHospitalInfoName()))
            viewHolder.mUserHospital.setText("未设置");
        else
            viewHolder.mUserHospital.setText(datas.get(position).getHospitalInfoName());
        if (datas.get(position).getGoodAtRealm() == null || "".equals(datas.get(position).getGoodAtRealm()))
            viewHolder.mUserGoodAtRealm.setText("未设置");
        else
            viewHolder.mUserGoodAtRealm.setText("擅长:  "+datas.get(position).getGoodAtRealm());
     /*   if (datas.get(position).getImgTextPriceShow() == null || "".equals(datas.get(position).getImgTextPriceShow()))
            viewHolder.imgTextPrice.setText("未设置");
        else
            viewHolder.imgTextPrice.setText("￥" + datas.get(position).getImgTextPriceShow());
        if (datas.get(position).getImgTextSumNum() == null || "".equals(datas.get(position).getImgTextSumNum()))
            viewHolder.imgTextSumNum.setText("未设置");
        else
            viewHolder.imgTextSumNum.setText(datas.get(position).getImgTextSumNum() + "人咨询");
        if (datas.get(position).getPhonePriceShow() == null || "".equals(datas.get(position).getPhonePriceShow()))
            viewHolder.phonePriceStr.setText("未设置");
        else
            viewHolder.phonePriceStr.setText("￥" + datas.get(position).getPhonePriceShow());
        if (datas.get(position).getPhonePrice() == null || "".equals(datas.get(position).getPhonePrice()))
            viewHolder.phoneSumNum.setText("未设置");
        else
            viewHolder.phoneSumNum.setText(datas.get(position).getPhonePrice() + "人咨询");
        if (datas.get(position).getAudioPriceShow() == null || "".equals(datas.get(position).getAudioPriceShow()))
            viewHolder.audioPrice.setText("未设置");
        else
            viewHolder.audioPrice.setText("￥" + datas.get(position).getAudioPriceShow());
        if (datas.get(position).getAudioSumNum() == null || "".equals(datas.get(position).getAudioSumNum()))
            viewHolder.audioSumNum.setText("未设置");
        else
            viewHolder.audioSumNum.setText(datas.get(position).getAudioSumNum() + "人咨询");
        if (datas.get(position).getVideoPriceShow() == null || "".equals(datas.get(position).getVideoPriceShow()))
            viewHolder.videoPrice.setText("未设置");
        else
            viewHolder.videoPrice.setText("￥" + datas.get(position).getVideoPriceShow());
        if (datas.get(position).getVideoSumNum() == null || "".equals(datas.get(position).getVideoSumNum()))
            viewHolder.videoSumNum.setText("未设置");
        else
            viewHolder.videoSumNum.setText(datas.get(position).getVideoSumNum() + "人咨询");
        if (datas.get(position).getSigningPriceShow() == null || "".equals(datas.get(position).getSigningPriceShow()))
            viewHolder.signingPrice.setText("未设置");
        else
            viewHolder.signingPrice.setText("￥" + datas.get(position).getSigningPriceShow());
        if (datas.get(position).getSigningSumNum() == null || "".equals(datas.get(position).getSigningSumNum()))
            viewHolder.signingSumNum.setText("未设置");
        else
            viewHolder.signingSumNum.setText(datas.get(position).getSigningSumNum() + "人咨询");
*/

       /* if (datas.get(position).getFlagImgText() != null && datas.get(position).getFlagImgText() == 1)
            viewHolder.mImgTWJZ.setBackgroundResource(R.mipmap.tw_p);
        else
            viewHolder.mImgTWJZ.setBackgroundResource(R.mipmap.tw_d);

        if (datas.get(position).getFlagPhone() != null && datas.get(position).getFlagPhone() == 1)
            viewHolder.mImgDHJZ.setBackgroundResource(R.mipmap.dh_p);
        else
            viewHolder.mImgDHJZ.setBackgroundResource(R.mipmap.dh_d);

        if (datas.get(position).getFlagAudio() != null && datas.get(position).getFlagAudio() == 1)
            viewHolder.mImgYYJZ.setBackgroundResource(R.mipmap.yp_p);
        else
            viewHolder.mImgYYJZ.setBackgroundResource(R.mipmap.yp_d);

        if (datas.get(position).getFlagVideo() != null && datas.get(position).getFlagVideo() == 1)
            viewHolder.mImgSPJZ.setBackgroundResource(R.mipmap.sp_p);
        else
            viewHolder.mImgSPJZ.setBackgroundResource(R.mipmap.sp_d);

        if (datas.get(position).getFlagSigning() != null && datas.get(position).getFlagSigning() == 1)
            viewHolder.mImgQYJZ.setBackgroundResource(R.mipmap.qy_p);
        else
            viewHolder.mImgQYJZ.setBackgroundResource(R.mipmap.qy_d);*/

      /*  if (mOnItemClickListener != null) {
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
        }*/


        if (mOnItemClickQXGZListener != null) {
            viewHolder.tv_qxgz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickQXGZListener.onClick(position);
                }
            });

            viewHolder.tv_qxgz.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickQXGZListener.onLongClick(position);
                    return false;
                }
            });
        }


    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return datas.size();
//            return 10;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public ImageView mUserHeard;                         //用户头像
        public TextView mUserName;                          //用户名
        public TextView mUserTitle;                          //
        public TextView mUserHospital;                          //
        public TextView mUserGoodAtRealm;                          //
        public TextView imgTextPrice;                          //
        public TextView imgTextSumNum;                          //
        public TextView phonePriceStr;                          //
        public TextView phoneSumNum;                          //
        public TextView audioPrice;                          //
        public TextView audioSumNum;                          //
        public TextView videoPrice;                          //
        public TextView videoSumNum;                          //
        public TextView signingPrice;                          //
        public TextView signingSumNum;                          //

        public ImageView mImgTWJZ;                         //图文就诊图标
        public ImageView mImgDHJZ;                         //电话就诊图标
        public ImageView mImgYYJZ;                         //语音就诊图标
        public ImageView mImgSPJZ;                         //视频就诊图标
        public ImageView mImgQYJZ;                         //签约就诊图标

        private TextView tv_qxgz;                            //取消关注


        public ViewHolder(View view) {
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mUserHeard = (ImageView) view.findViewById(R.id.userHead);
            mUserName = (TextView) view.findViewById(R.id.userName);
            mUserTitle = (TextView) view.findViewById(R.id.userTitle);
            mUserHospital = (TextView) view.findViewById(R.id.userHospital);
            mUserGoodAtRealm = (TextView) view.findViewById(R.id.userGoodAtRealm);
//            imgTextPrice = (TextView) view.findViewById(R.id.imgTextPrice);
//            imgTextSumNum = (TextView) view.findViewById(R.id.imgTextSumNum);
//            phonePriceStr = (TextView) view.findViewById(R.id.phonePriceStr);
//            phoneSumNum = (TextView) view.findViewById(R.id.phoneSumNum);
//            audioPrice = (TextView) view.findViewById(R.id.audioPrice);
//            audioSumNum = (TextView) view.findViewById(R.id.audioSumNum);
//            videoPrice = (TextView) view.findViewById(R.id.videoPrice);
//            videoSumNum = (TextView) view.findViewById(R.id.videoSumNum);
//            signingPrice = (TextView) view.findViewById(R.id.signingPrice);
//            signingSumNum = (TextView) view.findViewById(R.id.signingSumNum);

          /*  mImgTWJZ = (ImageView) view.findViewById(R.id.img_tvjz);
            mImgDHJZ = (ImageView) view.findViewById(R.id.img_dhjz);
            mImgYYJZ = (ImageView) view.findViewById(R.id.img_yyjz);
            mImgSPJZ = (ImageView) view.findViewById(R.id.img_spjz);
            mImgQYJZ = (ImageView) view.findViewById(R.id.img_qyjz);*/

            tv_qxgz = (TextView) view.findViewById(R.id.tv_qxgz);


        }
    }


    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public interface OnItemClickQXGZListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickQXGZListener(OnItemClickQXGZListener onItemClickQXGZListener) {
        this.mOnItemClickQXGZListener = onItemClickQXGZListener;
    }

}