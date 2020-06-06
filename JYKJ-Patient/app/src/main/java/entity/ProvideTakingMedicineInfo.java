package entity;

import java.util.Date;


/**
 * 患者个人状况-服药信息
 * 
 * @author JiaQ
 */

public class ProvideTakingMedicineInfo implements java.io.Serializable {

	private Integer takingMedicineId;//服药设置编号
	private Integer drugCode;//药品编号(0即表示为手动输入的药名)
	private String drugName;//药品名称[患者可以手动任意输入药名]
	private Float useNum;//每次服用数量
	private String useUnit;//每次服用单位(片、粒)
	private String takingMedicineTime;//用药时间
	private Integer useFrequency;//用药频率(多少次/每天).Eg.1:每天1次;2:每天2次;3:每天3次;
	private Integer useCycle;//[预留]服用周期(服药天数)
	private String useDesc;//用药描述

	private String loginPatientPosition;//用药描述
	private String requestClientType;//用药描述
	private String operPatientCode;//用药描述
	private String operPatientName;//用药描述

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

	public Integer getTakingMedicineId() {
		return takingMedicineId;
	}

	public void setTakingMedicineId(Integer takingMedicineId) {
		this.takingMedicineId = takingMedicineId;
	}

	public Integer getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(Integer drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public Float getUseNum() {
		return useNum;
	}

	public void setUseNum(Float useNum) {
		this.useNum = useNum;
	}

	public String getUseUnit() {
		return useUnit;
	}

	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}

	public String getTakingMedicineTime() {
		return takingMedicineTime;
	}

	public void setTakingMedicineTime(String takingMedicineTime) {
		this.takingMedicineTime = takingMedicineTime;
	}

	public Integer getUseFrequency() {
		return useFrequency;
	}

	public void setUseFrequency(Integer useFrequency) {
		this.useFrequency = useFrequency;
	}

	public Integer getUseCycle() {
		return useCycle;
	}

	public void setUseCycle(Integer useCycle) {
		this.useCycle = useCycle;
	}

	public String getUseDesc() {
		return useDesc;
	}

	public void setUseDesc(String useDesc) {
		this.useDesc = useDesc;
	}
}
