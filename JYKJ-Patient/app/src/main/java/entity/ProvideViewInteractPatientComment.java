package entity;

import java.util.Date;


/**
 * 【医患互动】
 * 患者就诊-患者评价(就诊后针对医生的评论)
 * 
 * @author JiaQ
 */
public class ProvideViewInteractPatientComment implements java.io.Serializable {
	private Integer commentId;//患者评论编号
	private String orderCode;//订单关联编码
	private Integer treatmentType;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	
	private String doctorCode;//(被评论人)医生关联编码
	private String doctorUserName;//(被评论人)医生姓名
	private String doctorUserLogoUrl;//(被评论人)医生头像
	
	private String patientCode;//(评论/问诊人)患者关联编码
	private String patientUserName;//(评论/问诊人)患者姓名
	private String patientUserLogoUrl;//(评论/问诊人)患者头像

	private Integer commentType;//评论类型.0:未知;1:好评;2:中评;2:差评;
	private String commentContent;//评论内容
	private Date commentDate;//评论日期

	private Integer toCommentId;//评论目标人的评论编号(如果没有目标人，则该字段为空).用于级联评论回复
	private String toPatientCode;//评论目标人编码(如果没有目标人，则该字段为空)
	
	/****************** 非对称属性 *****************/
	private String treatmentTypeName;//就诊(治疗)类型展示内容

	private String rowNum;
	private String pageNum;
	private String loginPatientPosition;
	private String requestClientType;
	private String operPatientCode;
	private String operPatientName;
	private String searchDoctorCode;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(Integer treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorUserName() {
		return doctorUserName;
	}

	public void setDoctorUserName(String doctorUserName) {
		this.doctorUserName = doctorUserName;
	}

	public String getDoctorUserLogoUrl() {
		return doctorUserLogoUrl;
	}

	public void setDoctorUserLogoUrl(String doctorUserLogoUrl) {
		this.doctorUserLogoUrl = doctorUserLogoUrl;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public String getPatientUserName() {
		return patientUserName;
	}

	public void setPatientUserName(String patientUserName) {
		this.patientUserName = patientUserName;
	}

	public String getPatientUserLogoUrl() {
		return patientUserLogoUrl;
	}

	public void setPatientUserLogoUrl(String patientUserLogoUrl) {
		this.patientUserLogoUrl = patientUserLogoUrl;
	}

	public Integer getCommentType() {
		return commentType;
	}

	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Integer getToCommentId() {
		return toCommentId;
	}

	public void setToCommentId(Integer toCommentId) {
		this.toCommentId = toCommentId;
	}

	public String getToPatientCode() {
		return toPatientCode;
	}

	public void setToPatientCode(String toPatientCode) {
		this.toPatientCode = toPatientCode;
	}

	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getLoginPatientPosition() {
		return loginPatientPosition;
	}

	public void setLoginPatientPosition(String loginPatientPosition) {
		this.loginPatientPosition = loginPatientPosition;
	}

	public String getRequestClientType() {
		return requestClientType;
	}

	public void setRequestClientType(String requestClientType) {
		this.requestClientType = requestClientType;
	}

	public String getOperPatientCode() {
		return operPatientCode;
	}

	public void setOperPatientCode(String operPatientCode) {
		this.operPatientCode = operPatientCode;
	}

	public String getOperPatientName() {
		return operPatientName;
	}

	public void setOperPatientName(String operPatientName) {
		this.operPatientName = operPatientName;
	}

	public String getSearchDoctorCode() {
		return searchDoctorCode;
	}

	public void setSearchDoctorCode(String searchDoctorCode) {
		this.searchDoctorCode = searchDoctorCode;
	}
}

