package entity;

import java.util.Date;


/**
 * 【医患互动】
 * 患者就诊-处方记录
 * 
 * @author JiaQ
 */
public class ProvideInteractOrderPrescribe implements java.io.Serializable {
	private Integer prescribeId;//处方记录信息编号
	private String orderCode;//订单关联编码
	private String imgCode;//图片编码
	private String prescribeVoucher;//处方凭证编码
	private Integer prescribeType;//处方类型.0:未知;1:平台处方;2:医院处方;3:患者自购;4:其他;
	private String patientCode;//用户(患者)基本信息编码
	private String patientName;//问诊患者姓名
	private String doctorCode;//就诊医生编码
	private String doctorName;//就诊医生姓名
	private Date prescribeDate;//处方日期
	private String drugCode;//药品编码
	private String drugName;//药品名称
	private String productBatchNumber;//药品批次号
	private String specUnit;//单位
	private String specName;//格规
	private Float drugAmount;//数量
	private Float drugPrice;//单价
	private Float drugMoneys;//金额
	private Integer useNum;//每次服用数量(片、粒)
	private Integer useFrequency;//用于频率(多少次/每天)
	private Integer useCycle;//服用周期(服药天数)
	private String useDesc;//用药描述


	
	private String prescribeTypeName;//(展示)处方类型.0:未知;1:平台处方;2:医院处方;3:患者自购;4:其他;
	private String drugAmountName;//(展示)购买数量.Eg.数量+规格
	private String useNumName;//(展示)服用数量.Eg.每次服用数量+单位+/次

	private String	loginPatientPosition;
	private String	requestClientType;
	private String	operPatientCode;
	private String	operPatientName;

	public Integer getPrescribeId() {
		return prescribeId;
	}

	public void setPrescribeId(Integer prescribeId) {
		this.prescribeId = prescribeId;
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

	public String getPrescribeVoucher() {
		return prescribeVoucher;
	}

	public void setPrescribeVoucher(String prescribeVoucher) {
		this.prescribeVoucher = prescribeVoucher;
	}

	public Integer getPrescribeType() {
		return prescribeType;
	}

	public void setPrescribeType(Integer prescribeType) {
		this.prescribeType = prescribeType;
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

	public Date getPrescribeDate() {
		return prescribeDate;
	}

	public void setPrescribeDate(Date prescribeDate) {
		this.prescribeDate = prescribeDate;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getProductBatchNumber() {
		return productBatchNumber;
	}

	public void setProductBatchNumber(String productBatchNumber) {
		this.productBatchNumber = productBatchNumber;
	}

	public String getSpecUnit() {
		return specUnit;
	}

	public void setSpecUnit(String specUnit) {
		this.specUnit = specUnit;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public Float getDrugAmount() {
		return drugAmount;
	}

	public void setDrugAmount(Float drugAmount) {
		this.drugAmount = drugAmount;
	}

	public Float getDrugPrice() {
		return drugPrice;
	}

	public void setDrugPrice(Float drugPrice) {
		this.drugPrice = drugPrice;
	}

	public Float getDrugMoneys() {
		return drugMoneys;
	}

	public void setDrugMoneys(Float drugMoneys) {
		this.drugMoneys = drugMoneys;
	}

	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
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

	public String getPrescribeTypeName() {
		return prescribeTypeName;
	}

	public void setPrescribeTypeName(String prescribeTypeName) {
		this.prescribeTypeName = prescribeTypeName;
	}

	public String getDrugAmountName() {
		return drugAmountName;
	}

	public void setDrugAmountName(String drugAmountName) {
		this.drugAmountName = drugAmountName;
	}

	public String getUseNumName() {
		return useNumName;
	}

	public void setUseNumName(String useNumName) {
		this.useNumName = useNumName;
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
