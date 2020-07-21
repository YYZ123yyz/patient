package www.patient.jykj_zxyl.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import www.patient.jykj_zxyl.activity.home.BloodLogAcitivity;
import www.patient.jykj_zxyl.activity.home.myself.JDDAActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.myself.*;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.custom.MyselfItemView;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.BloodLogAcitivity;
import www.patient.jykj_zxyl.activity.myself.MedicationActivity;
import www.patient.jykj_zxyl.custom.MyselfItemView;
import www.patient.jykj_zxyl.util.widget.AuthorityJQQDDialog;


/**
 * 个人中心fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentMySelf extends Fragment implements View.OnClickListener {
    private MyselfItemView myselfBindFriend;//绑定亲友
    private MyselfItemView myselfOrder;//我的订单
    private MyselfItemView myselfMyDoctor;//我的医生
    private MyselfItemView myselfFile;//建档档案
    private MyselfItemView myselfBlood;//血压记录
    private MyselfItemView myselfMedication;//用药提醒
    private MyselfItemView myselfRecommend;//推荐
    private MyselfItemView myselfSetting;//设置

    private ImageView iv_fragmentMyself_userHeadImage;            //用户头像
    private JYKJApplication mApp;
    private Context mContext;
    private TextView tv_fragmentMySelf_nameText;

    private LinearLayout  tv_wdye,li_yhq,li_jf;
    ImageView discountBtn;
    ImageView gradeBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymain_myselffragment, container, false);
        mContext = getContext();
        mApp = (JYKJApplication) mContext.getApplicationContext();
        initLayout(v);
        initListener();
        return v;
    }

    private void initLayout(View v) {
        tv_wdye = v.findViewById(R.id.tv_wdye);


        li_yhq = v.findViewById(R.id.li_yhq);
        li_jf = v.findViewById(R.id.li_jf);
        li_jf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthorityJQQDDialog mAuthorityJQQDDialog = new AuthorityJQQDDialog(mContext);
//                mAuthorityDialog.setmProvideViewMyDoctorOrderAndTreatment(provideViewMyDoctorOrderAndTreatment);
                mAuthorityJQQDDialog.show();
            }
        });

        li_yhq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthorityJQQDDialog mAuthorityJQQDDialog = new AuthorityJQQDDialog(mContext);
//                mAuthorityDialog.setmProvideViewMyDoctorOrderAndTreatment(provideViewMyDoctorOrderAndTreatment);
                mAuthorityJQQDDialog.show();
            }
        });

        myselfBindFriend = v.findViewById(R.id.myself_bind_friend);
        myselfOrder = v.findViewById(R.id.myself_order);
        myselfMyDoctor = v.findViewById(R.id.myself_doctor_btn);
        myselfFile = v.findViewById(R.id.myself_file);
        myselfBlood = v.findViewById(R.id.myself_blood);
        myselfMedication = v.findViewById(R.id.myself_medication);
        myselfRecommend = v.findViewById(R.id.myself_recommend);
        myselfSetting = v.findViewById(R.id.myself_setting);
        discountBtn = v.findViewById(R.id.discountBtn);
        gradeBtn = v.findViewById(R.id.gradeBtn);
        iv_fragmentMyself_userHeadImage = (ImageView)v.findViewById(R.id.iv_fragmentMyself_userHeadImage);
        tv_fragmentMySelf_nameText = (TextView)v.findViewById(R.id.tv_fragmentMySelf_nameText);
    }

    private void initListener(){
        myselfBindFriend.setOnClickListener(this);
        myselfOrder.setOnClickListener(this);
        myselfMyDoctor.setOnClickListener(this);
        myselfFile.setOnClickListener(this);
        myselfBlood.setOnClickListener(this);
        myselfMedication.setOnClickListener(this);
        myselfRecommend.setOnClickListener(this);
        myselfSetting.setOnClickListener(this);
        tv_fragmentMySelf_nameText.setOnClickListener(this);
        iv_fragmentMyself_userHeadImage.setOnClickListener(this);
        tv_wdye.setOnClickListener(this);
        discountBtn.setOnClickListener(this);
        gradeBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            int avatarResId = Integer.parseInt(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            Glide.with(mContext).load(avatarResId).into(iv_fragmentMyself_userHeadImage);
        } catch (Exception e) {
            //use default avatar
            Glide.with(mContext).load(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl())
                    .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(iv_fragmentMyself_userHeadImage);
        }
        if(null!= mApp.mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus() && 1==mApp.mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus()){
            if(null!=mApp.mProvideViewSysUserPatientInfoAndRegion.getUserNameAlias()){
                tv_fragmentMySelf_nameText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserNameAlias());
            }
        }else {
            if (mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName() != null)
                tv_fragmentMySelf_nameText.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_fragmentMyself_userHeadImage:
                startActivity(new Intent(getActivity(), UserCenterActivity.class));
                break;
            case R.id.tv_fragmentMySelf_nameText:
                startActivity(new Intent(getActivity(), UserCenterActivity.class));
                break;
            case R.id.myself_bind_friend://绑定亲友
                startActivity(new Intent(getActivity(),BindFamilyActivity.class));
                break;
            case R.id.myself_order://我的订单
                startActivity(new Intent(getActivity(),MyOrderActivity.class));
                break;
            case R.id.myself_doctor_btn://我的医生
                startActivity(new Intent(getActivity(),WDYSActivity.class));
                break;
            case R.id.myself_file://建档
                startActivity(new Intent(getActivity(),JDDAActivity.class));
                break;
            case R.id.myself_recommend://推荐
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;

            case R.id.myself_blood://血压
                startActivity(new Intent(getActivity(), BloodLogAcitivity.class));
                break;
            case R.id.myself_medication://用药提醒
                startActivity(new Intent(getActivity(), MedicationActivity.class));
                break;
            case R.id.myself_setting://设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.tv_wdye:
                startActivity(new Intent(getActivity(),MySurplusActivity.class));
                break;
            case R.id.discountBtn:
                alertWillpub();
                break;
            case R.id.gradeBtn:
                alertWillpub();
                break;
        }
    }
    void alertWillpub(){
        AuthorityJQQDDialog mAuthorityJQQDDialog = new AuthorityJQQDDialog(mContext);
//                mAuthorityDialog.setmProvideViewMyDoctorOrderAndTreatment(provideViewMyDoctorOrderAndTreatment);
        mAuthorityJQQDDialog.show();
    }
}
