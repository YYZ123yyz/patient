package www.patient.jykj_zxyl.base.http;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;

/**
 * Description:存放所有的Api
 *
 * @author: qiuxinhai
 * @date: 2018/5/31 16:10
 */
public interface ApiService {


    //添加问诊信息
    @POST("/patientInteractDataControlle/operAddInteractPatientInterrogationData")
    Observable<String> operAddInteractPatientInterrogationData(@Body RequestBody requestBody);

    //问诊图片上传
    @POST("/patientInteractDataControlle/operUpdPatientInterrogationImg")
    Observable<BaseBean> operUpdPatientInterrogationImg(@Body RequestBody requestBody);

    //验证码登录
    @POST("/patientLoginControlle/loginPatientAppVerifyLoginSmsVerify")
    Observable<String> loginPatientAppVerifyLoginSmsVerify(@Body RequestBody requestBody);
}
