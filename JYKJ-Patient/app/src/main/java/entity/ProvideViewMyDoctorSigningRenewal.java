package entity;

import java.util.Date;


/**
 * 【医患互动】
 * 患者(我的医生功能)下的签约医生(续约所需数据)
 * 
 * @author JiaQ
 */
public class ProvideViewMyDoctorSigningRenewal implements java.io.Serializable {
	
	private Integer treatmentSigningId;//患者就诊签约编号
	private String patientCode;//患者编码
	private String patientName;//患者姓名
	private String patientLinkPhone;//联系电话
	private String doctorCode;//医生编码
	//private String doctorName;//医生姓名
	private Integer limitSigningMonth;//签约服务(限制)-签约月数
	private Date limitSigningStartDate;//签约服务(限制)-开始日期
	private Date limitSigningExpireDate;//签约服务(限制)-到期日期
	private Integer flagUseState;//使用状态[未使用的为历史标签数据].0:未使用;1:使用中;
	private Integer flagDel;//删除标识.1:正常;0:删除;

	private Integer serviceType;//就诊(治疗)类型.4:签约服务;
	private Integer flagOpening;//是否开通此类型服务.0:否;1:是;
	private Float priceBasics;//基础价格
	private Float pricePremium;//溢价价格
	private Integer sourceNumBasics;//基础号源数量.号源数量无限制时,记录-1
	private Integer sourceNumPremium;//溢价号源数量.号源数量无限制时,记录-1

	private String userName;//医生姓名(最新,用户表获取的数据)
	
	private String loginPatientPosition;
	private String requestClientType;
	private String operPatientCode;
	private String operPatientName;
	private String signingDoctorCode;

	public String getSigningDoctorCode() {
		return signingDoctorCode;
	}

	public void setSigningDoctorCode(String signingDoctorCode) {
		this.signingDoctorCode = signingDoctorCode;
	}

	public Integer getTreatmentSigningId() {
		return treatmentSigningId;
	}

	public void setTreatmentSigningId(Integer treatmentSigningId) {
		this.treatmentSigningId = treatmentSigningId;
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

	public String getPatientLinkPhone() {
		return patientLinkPhone;
	}

	public void setPatientLinkPhone(String patientLinkPhone) {
		this.patientLinkPhone = patientLinkPhone;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public Integer getLimitSigningMonth() {
		return limitSigningMonth;
	}

	public void setLimitSigningMonth(Integer limitSigningMonth) {
		this.limitSigningMonth = limitSigningMonth;
	}

	public Date getLimitSigningStartDate() {
		return limitSigningStartDate;
	}

	public void setLimitSigningStartDate(Date limitSigningStartDate) {
		this.limitSigningStartDate = limitSigningStartDate;
	}

	public Date getLimitSigningExpireDate() {
		return limitSigningExpireDate;
	}

	public void setLimitSigningExpireDate(Date limitSigningExpireDate) {
		this.limitSigningExpireDate = limitSigningExpireDate;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
	}

	public Integer getFlagDel() {
		return flagDel;
	}

	public void setFlagDel(Integer flagDel) {
		this.flagDel = flagDel;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getFlagOpening() {
		return flagOpening;
	}

	public void setFlagOpening(Integer flagOpening) {
		this.flagOpening = flagOpening;
	}

	public Float getPriceBasics() {
		return priceBasics;
	}

	public void setPriceBasics(Float priceBasics) {
		this.priceBasics = priceBasics;
	}

	public Float getPricePremium() {
		return pricePremium;
	}

	public void setPricePremium(Float pricePremium) {
		this.pricePremium = pricePremium;
	}

	public Integer getSourceNumBasics() {
		return sourceNumBasics;
	}

	public void setSourceNumBasics(Integer sourceNumBasics) {
		this.sourceNumBasics = sourceNumBasics;
	}

	public Integer getSourceNumPremium() {
		return sourceNumPremium;
	}

	public void setSourceNumPremium(Integer sourceNumPremium) {
		this.sourceNumPremium = sourceNumPremium;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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