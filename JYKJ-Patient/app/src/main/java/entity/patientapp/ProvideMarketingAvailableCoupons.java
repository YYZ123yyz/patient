package entity.patientapp;

import java.util.Date;


/**
 * 订单生成时：
 * 营销中心-患者可用的优惠券信息
 * 
 * @author JiaQ
 */

public class ProvideMarketingAvailableCoupons implements java.io.Serializable {
	
	private String couponsHaveCode;//优惠券编码
	private String couponsNumber;//优惠券号码
	private String couponsName;//优惠券名称	
	private Float couponsDeductionMoney;//优惠券抵扣金额.Eg.减免50元
	private Float couponsConditionMoney;//满足使用条件的金额.Eg.满200可用
	
	private Integer flagCouponsState;//优惠券状态.0:未领取;1:已领取;2:已过期;3:已使用;
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

	public String getCouponsHaveCode() {
		return couponsHaveCode;
	}

	public void setCouponsHaveCode(String couponsHaveCode) {
		this.couponsHaveCode = couponsHaveCode;
	}

	public String getCouponsNumber() {
		return couponsNumber;
	}

	public void setCouponsNumber(String couponsNumber) {
		this.couponsNumber = couponsNumber;
	}

	public String getCouponsName() {
		return couponsName;
	}

	public void setCouponsName(String couponsName) {
		this.couponsName = couponsName;
	}

	public Float getCouponsDeductionMoney() {
		return couponsDeductionMoney;
	}

	public void setCouponsDeductionMoney(Float couponsDeductionMoney) {
		this.couponsDeductionMoney = couponsDeductionMoney;
	}

	public Float getCouponsConditionMoney() {
		return couponsConditionMoney;
	}

	public void setCouponsConditionMoney(Float couponsConditionMoney) {
		this.couponsConditionMoney = couponsConditionMoney;
	}

	public Integer getFlagCouponsState() {
		return flagCouponsState;
	}

	public void setFlagCouponsState(Integer flagCouponsState) {
		this.flagCouponsState = flagCouponsState;
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
}
