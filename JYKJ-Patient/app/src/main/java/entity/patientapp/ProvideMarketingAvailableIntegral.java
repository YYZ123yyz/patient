package entity.patientapp;

import java.util.Date;


/**
 * 订单生成时：
 * 营销中心-患者可用的积分信息
 * 
 * @author JiaQ
 */

public class ProvideMarketingAvailableIntegral implements java.io.Serializable {
	
	private String integralHaveCode;//积分编码
	private String integralNumber;//积分数量
	private String integralName;//积分名称	
	private Float integralDeductionMoney;//本次使用积分数据(相当于：可抵扣的金额.Eg.5000积分抵扣50元)
	private Float integralConditionMoney;//本次【总共】【可使用】的积分数量
	
	private Integer flagIntegralState;//积分状态.0:未领取;1:已领取;2:已过期;3:已使用;
	private Date receiveDate;//领取日期
	private Date expireDate;//到期日期

	private String loginPatientPosition;
	private String requestClientType;
	private String operPatientCode;
	private String operPatientName;
	private String orderCode;
	private String treatmentType;
	private String doctorCode;
	private String doctorName;

	public String getIntegralHaveCode() {
		return integralHaveCode;
	}

	public void setIntegralHaveCode(String integralHaveCode) {
		this.integralHaveCode = integralHaveCode;
	}

	public String getIntegralNumber() {
		return integralNumber;
	}

	public void setIntegralNumber(String integralNumber) {
		this.integralNumber = integralNumber;
	}

	public String getIntegralName() {
		return integralName;
	}

	public void setIntegralName(String integralName) {
		this.integralName = integralName;
	}

	public Float getIntegralDeductionMoney() {
		return integralDeductionMoney;
	}

	public void setIntegralDeductionMoney(Float integralDeductionMoney) {
		this.integralDeductionMoney = integralDeductionMoney;
	}

	public Float getIntegralConditionMoney() {
		return integralConditionMoney;
	}

	public void setIntegralConditionMoney(Float integralConditionMoney) {
		this.integralConditionMoney = integralConditionMoney;
	}

	public Integer getFlagIntegralState() {
		return flagIntegralState;
	}

	public void setFlagIntegralState(Integer flagIntegralState) {
		this.flagIntegralState = flagIntegralState;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
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

	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getPatientCode() {
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
}
