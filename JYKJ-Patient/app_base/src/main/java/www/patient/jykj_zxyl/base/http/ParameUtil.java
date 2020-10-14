package www.patient.jykj_zxyl.base.http;

import android.app.Activity;
import java.util.HashMap;
import www.patient.jykj_zxyl.base.base_bean.ProvideViewSysUserPatientInfoAndRegion;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-07-15 14:02
 */
public class ParameUtil {

    public static String loginDoctorPosition = "108.93425^34.23053";
    /**
     * 构建基础请求参数
     * @return HashMap
     */
    public static HashMap<String, Object> buildBaseParam() {
        return new HashMap<>();
    }


    /**
     * 构建患者端的基础请求参数
     * @param activity 当前activity
     * @return Hashmap
     */
    public static HashMap<String, Object> buildBasePatientParam(Activity activity) {
        SharedPreferences_DataSave m_persist = new SharedPreferences_DataSave(activity,
                "JYKJDOCTER");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion
                = GsonUtils.fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginPatientPosition",loginDoctorPosition);
        paramMap.put("requestClientType","1");
        paramMap.put("operPatientCode",mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        paramMap.put("operPatientName",mProvideViewSysUserPatientInfoAndRegion.getUserName());
        paramMap.put("mainPatientCode",mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        paramMap.put("mainPatientName",mProvideViewSysUserPatientInfoAndRegion.getUserName());
        return paramMap;
    }

    /**
     * 请求医生端请求参数
     * @param activity 当前activity
     * @return HashMap
     */
    public static HashMap<String,Object> buildBaseDoctorParam(Activity activity){
        SharedPreferences_DataSave m_persist = new SharedPreferences_DataSave(activity,
                "JYKJDOCTER");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion
                = GsonUtils.fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginDoctorPosition",loginDoctorPosition);
        paramMap.put("requestClientType","1");
        paramMap.put("operatorCode",mProvideViewSysUserPatientInfoAndRegion.getPatientCode());//7b5d2d0205164f12974a3e228f5a6083  mProvideViewSysUserPatientInfoAndRegion.getPatientCode()
        paramMap.put("operatorName",mProvideViewSysUserPatientInfoAndRegion.getUserName());//贾青  mProvideViewSysUserPatientInfoAndRegion.getUserName()
        return paramMap;
    }

}
