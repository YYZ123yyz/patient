package entity;

import java.io.Serializable;

/**
 * 【医患互动】
 * <就诊订单>和<就诊重要数据>
 * 
 * @author JiaQ
 */
public class ProvideViewMyDoctorOrderAndTreatment implements Serializable {

	/**
	 * actualPayment : 99.0
	 * couponRecordHistoryCode : 0
	 * createDate : 1603186148000
	 * departmentId : 1
	 * departmentName : 内科
	 * departmentSecondId : 14
	 * departmentSecondName : 呼吸内科
	 * flagOrderState : 2
	 * flagOrderStateName : 待付款
	 * hospitalInfoCode : 04d72a13c44e40848f07
	 * hospitalInfoName : 北京市阜外医院
	 * integralRecordHistoryCode : 0
	 * interactOrderCode : 0101202010201729019321818474
	 * mainDoctorCode : 055a486639de4c56ae97673382b25166
	 * mainDoctorName : 张强
	 * mainPatientCode : 790e56abcdbf4c12bda2489d3dac0326
	 * mainUserName : User_264545
	 * orderCode : 0102195a83894876abd45e06c81cb007
	 * orderDesc : 类型：图文就诊； 服务时效：24小时； 服务项目：<图文服务>次数/无限制； <音频服务>时长/<视频服务>时长/<电话服务>时长/
	 * orderNo : 0101202010201729019321818474
	 * orderTotal : 99.0
	 * orderType : 1
	 * patientPhone : 15296734545
	 * paymentMode : 2
	 * priceDiscountCoupon : 0.0
	 * priceDiscountIntegral : 0.0
	 * reserveEndTime : 1603296000000
	 * reserveTime : 1603209600000
	 * serviceTotal : 99.0
	 * treatmentType : 1
	 * treatmentTypeName : 图文就诊
	 */

	private double actualPayment;
	private String couponRecordHistoryCode;
	private long createDate;
	private String departmentId;
	private String departmentName;
	private String departmentSecondId;
	private String departmentSecondName;
	private int flagOrderState;
	private String flagOrderStateName;
	private String hospitalInfoCode;
	private String hospitalInfoName;
	private String integralRecordHistoryCode;
	private String interactOrderCode;
	private String mainDoctorCode;
	private String mainDoctorName;
	private String mainPatientCode;
	private String mainUserName;
	private String orderCode;
	private String orderDesc;
	private String orderNo;
	private double orderTotal;
	private int orderType;
	private String patientPhone;
	private int paymentMode;
	private double priceDiscountCoupon;
	private double priceDiscountIntegral;
	private long reserveEndTime;
	private long reserveTime;
	private double serviceTotal;
	private int treatmentType;
	private String treatmentTypeName;
	private String loginPatientPosition;
	private    String requestClientType;
	private    String searchPatientCode;
	private    String searchPatientName;

	private    String operPatientCode;
	private    String operPatientName;
	private    String flagPayType;

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

	public String getSearchPatientCode() {
		return searchPatientCode;
	}

	public void setSearchPatientCode(String searchPatientCode) {
		this.searchPatientCode = searchPatientCode;
	}

	public String getSearchPatientName() {
		return searchPatientName;
	}

	public void setSearchPatientName(String searchPatientName) {
		this.searchPatientName = searchPatientName;
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

	public String getFlagPayType() {
		return flagPayType;
	}

	public void setFlagPayType(String flagPayType) {
		this.flagPayType = flagPayType;
	}

	public double getActualPayment() {
		return actualPayment;
	}

	public void setActualPayment(double actualPayment) {
		this.actualPayment = actualPayment;
	}

	public String getCouponRecordHistoryCode() {
		return couponRecordHistoryCode;
	}

	public void setCouponRecordHistoryCode(String couponRecordHistoryCode) {
		this.couponRecordHistoryCode = couponRecordHistoryCode;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentSecondId() {
		return departmentSecondId;
	}

	public void setDepartmentSecondId(String departmentSecondId) {
		this.departmentSecondId = departmentSecondId;
	}

	public String getDepartmentSecondName() {
		return departmentSecondName;
	}

	public void setDepartmentSecondName(String departmentSecondName) {
		this.departmentSecondName = departmentSecondName;
	}

	public int getFlagOrderState() {
		return flagOrderState;
	}

	public void setFlagOrderState(int flagOrderState) {
		this.flagOrderState = flagOrderState;
	}

	public String getFlagOrderStateName() {
		return flagOrderStateName;
	}

	public void setFlagOrderStateName(String flagOrderStateName) {
		this.flagOrderStateName = flagOrderStateName;
	}

	public String getHospitalInfoCode() {
		return hospitalInfoCode;
	}

	public void setHospitalInfoCode(String hospitalInfoCode) {
		this.hospitalInfoCode = hospitalInfoCode;
	}

	public String getHospitalInfoName() {
		return hospitalInfoName;
	}

	public void setHospitalInfoName(String hospitalInfoName) {
		this.hospitalInfoName = hospitalInfoName;
	}

	public String getIntegralRecordHistoryCode() {
		return integralRecordHistoryCode;
	}

	public void setIntegralRecordHistoryCode(String integralRecordHistoryCode) {
		this.integralRecordHistoryCode = integralRecordHistoryCode;
	}

	public String getInteractOrderCode() {
		return interactOrderCode;
	}

	public void setInteractOrderCode(String interactOrderCode) {
		this.interactOrderCode = interactOrderCode;
	}

	public String getMainDoctorCode() {
		return mainDoctorCode;
	}

	public void setMainDoctorCode(String mainDoctorCode) {
		this.mainDoctorCode = mainDoctorCode;
	}

	public String getMainDoctorName() {
		return mainDoctorName;
	}

	public void setMainDoctorName(String mainDoctorName) {
		this.mainDoctorName = mainDoctorName;
	}

	public String getMainPatientCode() {
		return mainPatientCode;
	}

	public void setMainPatientCode(String mainPatientCode) {
		this.mainPatientCode = mainPatientCode;
	}

	public String getMainUserName() {
		return mainUserName;
	}

	public void setMainUserName(String mainUserName) {
		this.mainUserName = mainUserName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getPatientPhone() {
		return patientPhone;
	}

	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public double getPriceDiscountCoupon() {
		return priceDiscountCoupon;
	}

	public void setPriceDiscountCoupon(double priceDiscountCoupon) {
		this.priceDiscountCoupon = priceDiscountCoupon;
	}

	public double getPriceDiscountIntegral() {
		return priceDiscountIntegral;
	}

	public void setPriceDiscountIntegral(double priceDiscountIntegral) {
		this.priceDiscountIntegral = priceDiscountIntegral;
	}

	public long getReserveEndTime() {
		return reserveEndTime;
	}

	public void setReserveEndTime(long reserveEndTime) {
		this.reserveEndTime = reserveEndTime;
	}

	public long getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(long reserveTime) {
		this.reserveTime = reserveTime;
	}

	public double getServiceTotal() {
		return serviceTotal;
	}

	public void setServiceTotal(double serviceTotal) {
		this.serviceTotal = serviceTotal;
	}

	public int getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(int treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
	}
}
