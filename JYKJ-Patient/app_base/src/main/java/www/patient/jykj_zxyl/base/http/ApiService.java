package www.patient.jykj_zxyl.base.http;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

    //删除既往病史
    @POST("/patientHealthRecordsControlle/operDelPatientConditionDiseaseRecord")
    Observable<String> operDelPatientConditionDiseaseRecord(@Body RequestBody requestBody);

    //服药记录
    @POST("/PatientConditionControlle/operUpdPatientConditionTakingMedicineState")
    Observable<String> operUpdPatientConditionTakingMedicineState(@Body RequestBody requestBody);
    //提交诊后留言

    @POST("/patientMyOrderControlle/operPatientMyOrderResMessageContent")
    Observable<String> operPatientMyOrderResMessageContent(@Body RequestBody requestBody);

    //提交留言图片
    @POST("/patientMyOrderControlle/operPatientMyOrderResMessageImg")
    Observable<String> operPatientMyOrderResMessageImg(@Body RequestBody requestBody);

    //提交订单评价
    @POST("/patientMyOrderControlle/operPatientMyOrderResComment")
    Observable<String> operPatientMyOrderResComment(@Body RequestBody requestBody);

    //搜索订单详情
    @POST("/doctorSignControlle/searchSignPatientDoctorOrder")
    Observable<String> searchSignPatientDoctorOrder(@Query(value = "jsonDataInfo", encoded = true) String queryJson);

    //患者订单操作
    @POST("/patientSignControlle/operSignOrderStatus")
    Observable<String> operSignOrderStatus(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //获取解约原因列表
    @POST("/basicDataController/getBasicsDomain")
    Observable<String> getBasicsDomain(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //患者解约提交
    @POST("/patientSignControlle/operTerminationSumbit")
    Observable<String> operTerminationSumbit(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //患者发起支付接口
    @POST("/patientSignControlle/operPatientOrderPay")
    Observable<String> operPatientOrderPay(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //患者订单操作
    @POST("/patientSignControlle/serchSignInfoByPatientCode")
    Observable<String> serchSignInfoByPatientCode(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //订单列表未完成
    @POST("/patientMyOrderControlle/searchPatientMyOrderResIncomplete")
    Observable<String> searchPatientMyOrderResIncomplete(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //订单进行中
    @POST("/patientMyOrderControlle/searchPatientMyOrderResOngoing")
    Observable<String> searchPatientMyOrderResOngoing(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //订单已完成
    @POST("/patientMyOrderControlle/searchPatientMyOrderResCompleted")
    Observable<String> searchPatientMyOrderResCompleted(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //我的订单
    @POST("/patientMyOrderControlle/searchPatientMyOrderResMessageContent")
    Observable<String> searchPatientMyOrderResMessageContent(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //订单数据评价
    @POST("/patientMyOrderControlle/searchPatientMyOrderResComment")
    Observable<String> searchPatientMyOrderResComment(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //患者端获取签约订单信息接口
    @POST("/patientSignControlle/searchSignPatientDoctorOrder")
    Observable<String> searchSignPatientDoctorOrder2(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //撤销解约流程
    @POST("/patientSignControlle/operTerminationRevoke")
    Observable<String> operTerminationRevoke(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //退款申请文字提交
    @POST("/patientMyOrderControlle/operPatientMyOrderResRefund")
    Observable<String> operPatientMyOrderResRefund(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //退款申请图片部分
    @POST("/patientMyOrderControlle/operPatientMyOrderResRefundImg")
    Observable<String> operPatientMyOrderResRefundImg(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //获取医生或者患者信息
    @POST("/patientDoctorCommonDataController/getUserInfoList")
    Observable<String> getUserInfoList(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //未完成订单删除
    @POST("/msgDataControlle/operDelPatientMsgInteractOrderInfo")
    Observable<String> operDelPatientMsgInteractOrderInfo(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //解约确认
    @POST("/patientSignControlle/operTerminationConfim")
    Observable<String> operTerminationConfim(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //可预约页面头部查询
    @POST("/reservePatientDoctorControll/searchReserveInfoTitleByDoctor")
    Observable<String> resevationTitle(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //可预约列表查询
    @POST("/reservePatientDoctorControll/searchReserveInfoByDoctorInfo")
    Observable<String> resevationList(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //预约提交
    @POST("/reservePatientDoctorControll/operReserveSubmit")
    Observable<String> resevationSubmit(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //身份证
    @POST("/reservePatientDoctorControll/operUserInfo")
    Observable<String> resevationIDCard(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //我的预约列表
    @POST("/reservePatientDoctorControll/searchReserveList")
    Observable<String> myResevationMyList(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //取消预约
    @POST("/reservePatientDoctorControll/operCancelReserve")
    Observable<String> cancelResevation(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //取消预约详情
    @POST("/reservePatientDoctorControll/searchReserveInfo ")
    Observable<String> cancelApp(@Query(value = "jsonDataInfo", encoded = true) String queryJson);

    //获取病历详情
    @POST("/medicalRecordControlle/searchPatientMedicalRecordDetail")
    Observable<String>  getPatientRecordDet(@Query(value = "jsonDataInfo", encoded = true) String queryJson);

    //确认病历
    @POST("/medicalRecordControlle/operPatientMedicalRecordConfirm")
    Observable<String> confirmPatientRecordDet(@Query(value = "jsonDataInfo", encoded = true) String queryJson);

    //im检测
    @POST("/imDataControlle/iMTesting")
    Observable<String> getCheckNum(@Query(value = "jsonDataInfo", encoded = true) String queryJson);

    //提交次数
    @POST("/imDataControlle/figureTextConsumption")
    Observable<String> submitCheckNum(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //检查检验单
    @POST("/medicalRecordControlle/searchPatientInspectionDetail")
    Observable<String> getCheckListDet(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //处方笺
    @POST("/medicalRecordControlle/searchPatientPrescribeDetail")
    Observable<String> getPrescriptionDet(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //处方笺下载
    @POST("/reservePatientDoctorControll/operDownloadAndShareMedical")
    Observable<String> getDownloadDet(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //问诊详情
    @POST("/medicalRecordControlle/searchPatientMedicalRecordInterrogation")
    Observable<String> getInquiryDet(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //提交问诊详情
    @POST("/medicalRecordControlle/operAddInteractPatientInterrogationDataAndImg")
    Observable<String> submitInquiryDet(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //获取所有部门
    @POST("/hospitalDataController/getDepartmentByHospitalCode")
    Observable<String> getAlldepartments(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //获取首页banner
    @POST("/basicDataController/getBannersAndDepartmentInfo")
    Observable<String> getHomeBanner(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
    //获取订单详情
    @POST("msgDataControlle/searchPatientMsgInteractOrderInfoDetail")
    Observable<String> getOrderDet(@Query(value = "jsonDataInfo", encoded = true) String queryJson);
}

