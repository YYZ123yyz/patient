package entity.mySelf;

import java.util.Date;

import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;

/**
 * 【医患互动】
 * 患者就诊-订单信息
 * 
 * @author JiaQ
 */
public class ProvideInteractOrderInfo extends
		MultiItemEntity implements java.io.Serializable {
	private Integer orderId;//订单编号
	private String orderCode;//订单关联编码
	private String treatmentSerialNum;//就诊流水号
	private String treatmentCardNum;//诊疗卡
	private Date orderDate;//订单生成日期
	private String patientCode;//患者关联编码
	private String patientName;//患者姓名
	private String hospitalInfoCode;//医院编号
	private String hospitalInfoName;//医院名称
	private String departmentId;//一级科室编号(大类)
	private String departmentName;//一级科室名称
	private String departmentSecondId;//二级科室编号(小类)
	private String departmentSecondName;//二级科室名称
	private String doctorCode;//医生关联编码
	private String doctorName;//医生姓名
	private Integer treatmentType;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	private Integer paymentMode;//支付方式.Eg.0:未知;1:微信支付;2:支付宝支付;3:银联支付;
	private Float serviceTotal;//服务总价(选择服务的总价格)
	private Float priceDiscountCoupon;//平台优惠(优惠方式:优惠券)
	private Float priceDiscountIntegral;//平台优惠(优惠方式:积分)
	private Float orderTotal;//订单总价(优惠后的总价格)
	private double actualPayment;//实付款(实际支付的总价格)
	private String couponRecordHistoryCode;//使用的咨询优惠券记录编码
	private String integralRecordHistoryCode;//使用的积分记录编码
	private String orderDesc;//订单描述.Eg.音频就诊 128元/10分钟
	private Integer flagOrderState;//订单状态.0:未知;1:待付款;2:已付款;3:;4:;5:;10:已完成;11:交易关闭(未付款超时);
	private Date treatmentDate;//就诊日期(音视频就诊需要)
	private String treatmentTimeSlot;//就诊时间段(音视频就诊需要)
	private String treatmentLinkPhone;//就诊联系电话(电话就诊需要)

	private String coachUnitCode;
	private String coachUnitName;
	private Integer coachValue;
	private Integer proCount;
	private String timesCode;
	private String timesName;
	private String signNo;
	private int orderType;
	private long createDate;
	private String signDuration;
	private String signDurationUnit;
	private String detectRateUnitCode;
	private String detectRateUnitName;
	private String detectRate;
	private String signStatus;
	private String mainDoctorName;
	private String mainDoctorCode;
	private String signCode;


	public String getDetectRate() {
		return detectRate;
	}

	public void setDetectRate(String detectRate) {
		this.detectRate = detectRate;
	}

	public String getMainDoctorName() {
		return mainDoctorName;
	}

	public void setMainDoctorName(String mainDoctorName) {
		this.mainDoctorName = mainDoctorName;
	}

	public String getMainDoctorCode() {
		return mainDoctorCode;
	}

	public void setMainDoctorCode(String mainDoctorCode) {
		this.mainDoctorCode = mainDoctorCode;
	}

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getDetectRateUnitCode() {
		return detectRateUnitCode;
	}

	public void setDetectRateUnitCode(String detectRateUnitCode) {
		this.detectRateUnitCode = detectRateUnitCode;
	}

	public String getDetectRateUnitName() {
		return detectRateUnitName;
	}

	public void setDetectRateUnitName(String detectRateUnitName) {
		this.detectRateUnitName = detectRateUnitName;
	}

	public String getSignDuration() {
		return signDuration;
	}

	public void setSignDuration(String signDuration) {
		this.signDuration = signDuration;
	}

	public String getSignDurationUnit() {
		return signDurationUnit;
	}

	public void setSignDurationUnit(String signDurationUnit) {
		this.signDurationUnit = signDurationUnit;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}

	public String getCoachUnitCode() {
		return coachUnitCode;
	}

	public void setCoachUnitCode(String coachUnitCode) {
		this.coachUnitCode = coachUnitCode;
	}

	public String getCoachUnitName() {
		return coachUnitName;
	}

	public void setCoachUnitName(String coachUnitName) {
		this.coachUnitName = coachUnitName;
	}

	public Integer getCoachValue() {
		return coachValue;
	}

	public void setCoachValue(Integer coachValue) {
		this.coachValue = coachValue;
	}

	public Integer getProCount() {
		return proCount;
	}

	public void setProCount(Integer proCount) {
		this.proCount = proCount;
	}

	public String getTimesCode() {
		return timesCode;
	}

	public void setTimesCode(String timesCode) {
		this.timesCode = timesCode;
	}

	public String getTimesName() {
		return timesName;
	}

	public void setTimesName(String timesName) {
		this.timesName = timesName;
	}

	/****************************** 【非对称属性】 ******************************/
	private String treatmentTypeName;//就诊(治疗)类型名称
	private String paymentModeName;//支付方式名称
	private String flagOrderStateName;//订单状态名称
	
	private String orderShowContent;//订单内容描述
	private Integer flagPayBtn;//支付按钮显示状态.0:不显示;1:显示;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getTreatmentSerialNum() {
		return treatmentSerialNum;
	}

	public void setTreatmentSerialNum(String treatmentSerialNum) {
		this.treatmentSerialNum = treatmentSerialNum;
	}

	public String getTreatmentCardNum() {
		return treatmentCardNum;
	}

	public void setTreatmentCardNum(String treatmentCardNum) {
		this.treatmentCardNum = treatmentCardNum;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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

	public Integer getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(Integer treatmentType) {
		this.treatmentType = treatmentType;
	}

	public Integer getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Float getServiceTotal() {
		return serviceTotal;
	}

	public void setServiceTotal(Float serviceTotal) {
		this.serviceTotal = serviceTotal;
	}

	public Float getPriceDiscountCoupon() {
		return priceDiscountCoupon;
	}

	public void setPriceDiscountCoupon(Float priceDiscountCoupon) {
		this.priceDiscountCoupon = priceDiscountCoupon;
	}

	public Float getPriceDiscountIntegral() {
		return priceDiscountIntegral;
	}

	public void setPriceDiscountIntegral(Float priceDiscountIntegral) {
		this.priceDiscountIntegral = priceDiscountIntegral;
	}

	public Float getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Float orderTotal) {
		this.orderTotal = orderTotal;
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

	public String getIntegralRecordHistoryCode() {
		return integralRecordHistoryCode;
	}

	public void setIntegralRecordHistoryCode(String integralRecordHistoryCode) {
		this.integralRecordHistoryCode = integralRecordHistoryCode;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public Integer getFlagOrderState() {
		return flagOrderState;
	}

	public void setFlagOrderState(Integer flagOrderState) {
		this.flagOrderState = flagOrderState;
	}

	public Date getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public String getTreatmentTimeSlot() {
		return treatmentTimeSlot;
	}

	public void setTreatmentTimeSlot(String treatmentTimeSlot) {
		this.treatmentTimeSlot = treatmentTimeSlot;
	}

	public String getTreatmentLinkPhone() {
		return treatmentLinkPhone;
	}

	public void setTreatmentLinkPhone(String treatmentLinkPhone) {
		this.treatmentLinkPhone = treatmentLinkPhone;
	}

	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
	}

	public String getPaymentModeName() {
		return paymentModeName;
	}

	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	public String getFlagOrderStateName() {
		return flagOrderStateName;
	}

	public void setFlagOrderStateName(String flagOrderStateName) {
		this.flagOrderStateName = flagOrderStateName;
	}

	public String getOrderShowContent() {
		return orderShowContent;
	}

	public void setOrderShowContent(String orderShowContent) {
		this.orderShowContent = orderShowContent;
	}

	public Integer getFlagPayBtn() {
		return flagPayBtn;
	}

	public void setFlagPayBtn(Integer flagPayBtn) {
		this.flagPayBtn = flagPayBtn;
	}
}
