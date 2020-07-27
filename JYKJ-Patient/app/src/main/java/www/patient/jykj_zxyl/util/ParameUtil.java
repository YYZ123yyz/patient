package www.patient.jykj_zxyl.util;

import android.app.Activity;
import android.test.mock.MockApplication;

import java.util.HashMap;

import www.patient.jykj_zxyl.application.JYKJApplication;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-07-15 14:02
 */
public class ParameUtil {
    /**
     * 构建基础请求参数
     * @return HashMap
     */
    public static HashMap<String, Object> buildBaseParam() {
        return new HashMap<>();
    }

    /**
     * 构建基础的请求参数
     * @param activity 当前activity
     * @return HashMap
     */
    public static HashMap<String, Object> buildBaseParam(Activity activity) {
        HashMap<String, Object> paramMap = new HashMap<>();
        JYKJApplication mApp = (JYKJApplication) activity.getApplication();
        paramMap.put("loginPatientPosition",mApp.loginDoctorPosition);
        paramMap.put("requestClientType","1");
        paramMap.put("operPatientCode",mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        paramMap.put("operPatientName",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        return paramMap;
    }

}
