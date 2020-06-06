package entity;

import java.util.Date;


/**
 * 患者打卡记录-服药设置
 * 
 * @author JiaQ
 */
public class ProvidePatientConditionTakingMedicine implements java.io.Serializable {
	
	private Integer takingMedicineId;//服药设置编号
	private String patientCode;//用户(患者)基本信息编码
	private String imgCode;//图片编码
	private Integer recordUserType;//记录用户类型.1:本人添加;2:亲属添加;3:医生添加;
	private Date recordDate;//记录日期
	private Integer takingMedicineType;//记录类型.0:未分类;1:手动录入;2:平台导入(平台就诊医生开具处方后,患者选择导入用药提醒功能后,导入的数据);
	private Integer takingMedicineTimeType;//记录时间类型.0:未分类;1:;2:;3:;
	private String takingMedicineVoucher;//服药凭证编码.(一组会存在多个药品信息,此编码相同的药品信息即为一组)
	private Integer drugCode;//药品编号(0即表示为手动输入的药名)
	private String drugName;//药品名称[患者可以手动任意输入药名]
	private Integer useNum;//每次服用数量
	private String useUnit;//每次服用单位(片、粒)
	private Date takingMedicineTime;//用药时间
	private Integer useFrequency;//用药频率(多少次/每天).Eg.1:每天1次;2:每天2次;3:每天3次;
	private Integer useCycle;//[预留]服用周期(服药天数)
	private String useDesc;//用药描述
	
	/**
	 * *******************************************【非对称属性】
	 */
	private String takingMedicineTimeShow;//用药时间


	private String rowNum;
	private String pageNum;
	private String loginPatientPosition;
	private String requestClientType;
	private String operPatientCode;
	private String operPatientName;

	public Integer getTakingMedicineId() {
		return takingMedicineId;
	}

	public void setTakingMedicineId(Integer takingMedicineId) {
		this.takingMedicineId = takingMedicineId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public Integer getRecordUserType() {
		return recordUserType;
	}

	public void setRecordUserType(Integer recordUserType) {
		this.recordUserType = recordUserType;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getTakingMedicineType() {
		return takingMedicineType;
	}

	public void setTakingMedicineType(Integer takingMedicineType) {
		this.takingMedicineType = takingMedicineType;
	}

	public Integer getTakingMedicineTimeType() {
		return takingMedicineTimeType;
	}

	public void setTakingMedicineTimeType(Integer takingMedicineTimeType) {
		this.takingMedicineTimeType = takingMedicineTimeType;
	}

	public String getTakingMedicineVoucher() {
		return takingMedicineVoucher;
	}

	public void setTakingMedicineVoucher(String takingMedicineVoucher) {
		this.takingMedicineVoucher = takingMedicineVoucher;
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

	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public String getUseUnit() {
		return useUnit;
	}

	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}

	public Date getTakingMedicineTime() {
		return takingMedicineTime;
	}

	public void setTakingMedicineTime(Date takingMedicineTime) {
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

	public String getTakingMedicineTimeShow() {
		return takingMedicineTimeShow;
	}

	public void setTakingMedicineTimeShow(String takingMedicineTimeShow) {
		this.takingMedicineTimeShow = takingMedicineTimeShow;
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
}

