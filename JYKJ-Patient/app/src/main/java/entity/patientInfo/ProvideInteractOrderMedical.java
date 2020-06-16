package entity.patientInfo;

import java.util.Date;

/**
 * 【医患互动】
 * 医患互动-患者就诊-就诊小结
 * 
 * @author JiaQ
 */
public class ProvideInteractOrderMedical implements java.io.Serializable {
	private Integer medicalId;//就诊小结(病历内容)编号
	private String orderCode;//订单关联编码
	private String imgCode;//图片编码

	private String patientCode;//用户(患者)基本信息编码
	private String patientName;//问诊患者姓名
	private String doctorCode;//就诊医生编码
	private String doctorName;//就诊医生姓名
	
	private String treatmentContent;
	private String chiefComplaint;//主诉.对应线下(大病历)功能为:主诉
	private String presentIllness;//既往史.对应线下(大病历)功能为:现病史
	private String medicalExamination;//体征.对应线下(大病历)功能为:入院查体
	private String auxiliaryCheck;//辅助诊断(检查).对应线下(大病历)功能为:辅助检查结果
	private String treatmentPlanCode;//初步臆断
	private Integer flagUseState;//使用状态.0:未使用;1:使用中

	private Date createDate;//记录日期
	
	private Integer medicalTypeCode;//病史类型编码
	private String medicalTypeName;//病史类型名称

	public Integer getMedicalId() {
		return medicalId;
	}

	public void setMedicalId(Integer medicalId) {
		this.medicalId = medicalId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getTreatmentContent() {
		return treatmentContent;
	}

	public void setTreatmentContent(String treatmentContent) {
		this.treatmentContent = treatmentContent;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public String getPresentIllness() {
		return presentIllness;
	}

	public void setPresentIllness(String presentIllness) {
		this.presentIllness = presentIllness;
	}

	public String getMedicalExamination() {
		return medicalExamination;
	}

	public void setMedicalExamination(String medicalExamination) {
		this.medicalExamination = medicalExamination;
	}

	public String getAuxiliaryCheck() {
		return auxiliaryCheck;
	}

	public void setAuxiliaryCheck(String auxiliaryCheck) {
		this.auxiliaryCheck = auxiliaryCheck;
	}

	public String getTreatmentPlanCode() {
		return treatmentPlanCode;
	}

	public void setTreatmentPlanCode(String treatmentPlanCode) {
		this.treatmentPlanCode = treatmentPlanCode;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getMedicalTypeCode() {
		return medicalTypeCode;
	}

	public void setMedicalTypeCode(Integer medicalTypeCode) {
		this.medicalTypeCode = medicalTypeCode;
	}

	public String getMedicalTypeName() {
		return medicalTypeName;
	}

	public void setMedicalTypeName(String medicalTypeName) {
		this.medicalTypeName = medicalTypeName;
	}
}