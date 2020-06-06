package entity;


/**
 * 【我的诊所】【医患互动】
 * 就诊详情-各项记录填写状态
 * 
 * @author JiaQ
 */
public class ProvideInteractClinicRecordWriteState implements java.io.Serializable {
	
	private Integer totalityState;//整体状态.0:未完成;1:已完成;

	private Integer interrogationState;//问诊状态.0:未填写;1:已填写;
	private Integer messageState;//诊后留言回复.0:问诊人未提交;1:问诊人已提交;2:医生已回复;
	private Integer diagState;//臆断描述状态.0:未填写;1:已填写;
	private Integer treatmentState;//病历小结状态.0:未填写;1:已填写;
	private Integer prescribeState;//处方笺.0:未填写;1:已填写;
	
	//[系统暂时未用]暂时不使用此属性
	private Integer medicalState;//就诊记录.0:未填写;1:已填写;

	private	String loginPatientPosition;
	private	String requestClientType;
	private	String operPatientCode;
	private	String operPatientName;
	private	String orderCode;


	public Integer getTotalityState() {
		return totalityState;
	}

	public void setTotalityState(Integer totalityState) {
		this.totalityState = totalityState;
	}

	public Integer getInterrogationState() {
		return interrogationState;
	}

	public void setInterrogationState(Integer interrogationState) {
		this.interrogationState = interrogationState;
	}

	public Integer getMessageState() {
		return messageState;
	}

	public void setMessageState(Integer messageState) {
		this.messageState = messageState;
	}

	public Integer getDiagState() {
		return diagState;
	}

	public void setDiagState(Integer diagState) {
		this.diagState = diagState;
	}

	public Integer getTreatmentState() {
		return treatmentState;
	}

	public void setTreatmentState(Integer treatmentState) {
		this.treatmentState = treatmentState;
	}

	public Integer getPrescribeState() {
		return prescribeState;
	}

	public void setPrescribeState(Integer prescribeState) {
		this.prescribeState = prescribeState;
	}

	public Integer getMedicalState() {
		return medicalState;
	}

	public void setMedicalState(Integer medicalState) {
		this.medicalState = medicalState;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
}
