package entity.mySelf;



/**
 * 患者个人状况-服药记录
 * 
 * @author JiaQ
 */
public class ProvidePatientConditionTakingRecord implements java.io.Serializable {	
	private Integer takingRecordId;//服药记录编号
	private String patientCode;//患者编码
	private String imgCode;//图片编码
	private Integer takingMedicineId;//服药设置编号
	private Integer recordUserType;//记录用户类型.1:本人添加;2:亲属添加;3:医生添加;
	private Integer takingMedicineType;//记录类型.0:未分类;1:手动录入;2:平台导入;
	private Integer takingMedicineTimeType;//记录时间类型.0:未分类;1:;2:;3:;
	private long remindDate;//提醒日期
	private long remindTime;//提醒时间
	private Integer flagSendRemind;//服药提醒消息推送标识.0:未推送;1:推送失败;2:推送成功;

	private String takingMedicineVoucher;//服药凭证编码
	private Integer drugCode;//药品编号
	private String drugName;//药品名称
	private Integer useNum;//每次服用数量
	private String useUnit;//每次服用单位(片、粒)
	private long takingMedicineTime;//用药时间
	private Integer useFrequency;//[预留]用药频率(多少次/每天)
	private Integer useCycle;//[预留]服用周期(服药天数)
	private String useDesc;//用药描述
	
	private Integer flagTakingMedicine;//服药标识.0:未操作;1:未服用;2:操作过期;3:已服用;
	private Integer flagTakingMedicineUserType;//服药用户类型.1:本人操作;2:亲属操作;
	private long takingMedicineDate;//服药日期

	public Integer getTakingRecordId() {
		return takingRecordId;
	}

	public void setTakingRecordId(Integer takingRecordId) {
		this.takingRecordId = takingRecordId;
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

	public Integer getTakingMedicineId() {
		return takingMedicineId;
	}

	public void setTakingMedicineId(Integer takingMedicineId) {
		this.takingMedicineId = takingMedicineId;
	}

	public Integer getRecordUserType() {
		return recordUserType;
	}

	public void setRecordUserType(Integer recordUserType) {
		this.recordUserType = recordUserType;
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

	public long getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(long remindDate) {
		this.remindDate = remindDate;
	}

	public long getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(long remindTime) {
		this.remindTime = remindTime;
	}

	public Integer getFlagSendRemind() {
		return flagSendRemind;
	}

	public void setFlagSendRemind(Integer flagSendRemind) {
		this.flagSendRemind = flagSendRemind;
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

	public long getTakingMedicineTime() {
		return takingMedicineTime;
	}

	public void setTakingMedicineTime(long takingMedicineTime) {
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

	public Integer getFlagTakingMedicine() {
		return flagTakingMedicine;
	}

	public void setFlagTakingMedicine(Integer flagTakingMedicine) {
		this.flagTakingMedicine = flagTakingMedicine;
	}

	public Integer getFlagTakingMedicineUserType() {
		return flagTakingMedicineUserType;
	}

	public void setFlagTakingMedicineUserType(Integer flagTakingMedicineUserType) {
		this.flagTakingMedicineUserType = flagTakingMedicineUserType;
	}

	public long getTakingMedicineDate() {
		return takingMedicineDate;
	}

	public void setTakingMedicineDate(long takingMedicineDate) {
		this.takingMedicineDate = takingMedicineDate;
	}
}

