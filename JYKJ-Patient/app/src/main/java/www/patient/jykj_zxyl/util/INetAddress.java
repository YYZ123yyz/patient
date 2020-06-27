package www.patient.jykj_zxyl.util;
public class INetAddress {
    public static String QUERY_CONTACT_URL = "patientPersonalSetControlle/getUserPatientUrgentContacts";//查询联系人
    public static String MATAIN_CONTACT_URL = "/patientPersonalSetControlle/operUserPatientUrgentContacts";//维护联系人数据
    public static String QUERY_UNIMPLEMENT_ORDER_URL = "patientMyOrderControlle/searchPatientMyOrderResIncomplete";//订单列表-未完成
    public static String QUERY_HEALTHY_BASIC_URL = "patientHealthRecordsControlle/searchPatientConditionHealthyResBasics";//查询基本健康信息
    public static String MAINTAIN_HEALTHY_BASIC_URL = "patientHealthRecordsControlle/operUpdPatientConditionHealthyResBasics";//修改基本健康信息
    public static String QUERY_HEALTHY_SYMPTOM_URL = "patientHealthRecordsControlle/searchPatientConditionHealthyResSymptom";//读取症状信息
    public static String MAINTAIN_HEALTHY_SYMPTOM_URL = "patientHealthRecordsControlle/operUpdPatientConditionHealthyResSymptom";//维护症状信息
    public static String QUERY_PATIENTLABEL_URL = "patientHealthRecordsControlle/searchPatientConditionLabel";//获取标签信息
    public static String QUERY_PASTHIST_URL = "patientHealthRecordsControlle/searchPatientConditionDiseaseRecordList";//获取既往病史
    public static String QUERY_PERSON_PASTHIST_URL = "patientHealthRecordsControlle/searchPatientConditionDiseaseRecordResData";//本人填写既往病史详情
    public static String QUERY_DOCTOR_PASTHIST_URL = "patientHealthRecordsControlle/searchPatientConditionDiseaseRecordResDoctorData";//医生填写既往病史详情
    public static String MAINTAIN_PATIENTHIST_URL = "patientHealthRecordsControlle/operUpdPatientConditionDiseaseRecord";//更新既往病史
    public static String QUERY_PATIENTRECIMG_URL = "patientHealthRecordsControlle/searchPatientConditionDiseaseRecordResImg";//获取既往病史详情图片
    public static String SUB_PATIENTHISTIMG_URL = "patientHealthRecordsControlle/operUpdPatientConditionDiseaseRecordImg";//既往病史上传图片
    public static String QUERY_PATIENTHISTIMG_URL = "patientHealthRecordsControlle/searchPatientConditionDiseaseRecordResImg";//既往病史详情[本人填写][图片数据]
    public static String QUERY_MYORDER_PROCESS_URL = "patientMyOrderControlle/searchPatientMyOrderResOngoing";//订单列表-进行中
    public static String QUERY_MYORDER_FINISH_URL = "patientMyOrderControlle/searchPatientMyOrderResCompleted";//订单列表-已完成
    public static String QUERY_VERSION_URL = "patientPersonalSetControlle/searchPatientVersionUpdateDescription";//[设置]版本更新说明
    public static String SUB_COMMEND_URL = "patientPersonalSetControlle/operSubmitBasicsSystemFeedback";//[意见反馈]意见反馈内容提交
    public static String QUERY_JWBS_YSTX_DETAIL = "patientHealthRecordsControlle/searchPatientConditionDiseaseRecordResDoctorData";//既往病史详情[医生填写]
    public static String SUB_ZHLY_CHARACTER = "patientMyOrderControlle/operPatientMyOrderResMessageContent";//我的订单】订单列表-已完成[提交诊后留言-文字部分]
    public static String SUB_ZHLY_IMG = "patientMyOrderControlle/operPatientMyOrderResMessageImg";//【我的订单】订单列表-已完成[提交诊后留言-图片部分]
    public static String QUERY_ZHLY_CHARACTER_INFO = "patientMyOrderControlle/searchPatientMyOrderResMessageContent";//【我的订单】订单列表-已完成[获取诊后留言-文字部分]
    public static String QUERY_ZHLY_IMG_INFO = "patientMyOrderControlle/searchPatientMyOrderResMessageImg";//【我的订单】订单列表-已完成[获取诊后留言-图片部分]
    public static String QUERY_COMMENT = "patientMyOrderControlle/searchPatientMyOrderResComment";//【我的订单】订单列表-已完成[获取评价数据]
    public static String SUB_COMMENT = "patientMyOrderControlle/operPatientMyOrderResComment";//【我的订单】订单列表-已完成[提交评论数据]
}
