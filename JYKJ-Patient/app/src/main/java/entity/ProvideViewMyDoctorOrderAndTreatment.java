package entity;

import java.util.Date;


/**
 * 【医患互动】
 * <就诊订单>和<就诊重要数据>
 * 
 * @author JiaQ
 */
public class ProvideViewMyDoctorOrderAndTreatment implements java.io.Serializable {
	
	private Integer orderId;//订单编号
	private String orderCode;//订单关联编码(生成唯一订单号.Eg.年月日时分秒+日期的编码)
	private String treatmentSerialNum;//就诊流水号(当天的订单顺序号.Eg.年月日+5位随机数字)
	private String treatmentCardNum;//诊疗卡(可用于线下取药时使用.Eg.起始卡号:年份+00020000)
	private Date orderDate;//订单生成日期
	private String patientCode;//患者关联编码
	private String patientName;//[冗余]患者姓名
	private String doctorCode;//医生关联编码
	private String doctorName;//[冗余]医生姓名
	private Integer treatmentType;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	private Integer paymentMode;//支付方式.Eg.0:未知;1:微信支付;2:支付宝支付;3:银联支付;

	private Float serviceTotal;//服务总价(选择服务的总价格)
	private Float priceDiscountCoupon;//平台优惠(优惠方式:优惠券)
	private Float priceDiscountIntegral;//平台优惠(优惠方式:积分)
	private Float orderTotal;//订单总价(优惠后的总价格)
	private Float actualPayment;//实付款(实际支付的总价格)
	private String couponRecordHistoryCode;//使用的咨询优惠券记录编码.外键:咨询优惠券使用记录表(couponRecordHistoryCode)
	private String integralRecordHistoryCode;//使用的积分记录编码.外键:积分使用记录表(integralRecordHistoryCode)
	private String orderDesc;//订单描述.Eg.音频就诊 128元/10分钟
	
	private Integer flagOrderState;//订单状态.0:未知;1:待付款;2:已付款;3:;4:;5:;10:已完成;11:交易关闭(未付款超时);
	
	
	
	private Integer treatmentId;//就诊记录信息编号
	private Integer sourceTypeId;//来源(数据)类型.1:平台内就诊记录;2:平台外就诊记录;
	private Integer flagTreatmentState;//接诊状态标识.0:未支付(等待支付状态);1:已支付(可开始接诊);2:接诊中(医生开始回复或操作);3:接诊结束(等待回复诊后留言);11:接诊完成(诊后留言已回复);
	private Date treatmentDateStart;//就诊开始日期(图文\\签约就诊需要)
	private Date treatmentDateEnd;//就诊结束日期(图文\\签约就诊需要)
	private Date treatmentDate;//预约就诊日期(电话\\音频\\视频就诊需要)
	private String treatmentTimeSlot;//预约就诊时间段(电话\\音频\\视频就诊需要).0:未知;1:早;2:中;3:晚;
	private String treatmentLinkPhone;//就诊联系电话(电话就诊需要)
	private Date doctorReceiveDate;//医生接诊日期
	private String doctorReceiveTimeSlot;//医生接诊时间段(电话\\音频\\视频就诊需要).0:未知;1:早;2:中;3:晚;
	private Integer limitImgText;//图文就诊(限制)-医生解答剩余数量
	private Integer limitPhone;//电话就诊(限制)-剩余通话时间
	private Integer limitAudio;//音频就诊(限制)-剩余通话时间
	private Integer limitVideo;//视频就诊(限制)-剩余通话时间
	private Integer limitSigning;//签约服务(限制)-签约天数
	private Date limitSigningExpireDate;//签约服务(限制)-到期日期
	private Integer treatmentFlagUseState;//使用状态.0:未使用;1:使用中


	
	private String treatmentTypeName;//就诊(治疗)类型名称
	private String flagOrderStateName;//订单状态名称
	private String paymentModeName;//支付方式名称
	private Integer serviceCount;//服务数量
	private String serviceStartDate;//服务开始日期
	private String serviceStopDate;//服务截止日期

	private	String loginPatientPosition;
	private	String requestClientType;
	private	String searchPatientCode;
	private	String searchPatientName;

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

	public Float getActualPayment() {
		return actualPayment;
	}

	public void setActualPayment(Float actualPayment) {
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

	public Integer getTreatmentId() {
		return treatmentId;
	}

	public void setTreatmentId(Integer treatmentId) {
		this.treatmentId = treatmentId;
	}

	public Integer getSourceTypeId() {
		return sourceTypeId;
	}

	public void setSourceTypeId(Integer sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	public Integer getFlagTreatmentState() {
		return flagTreatmentState;
	}

	public void setFlagTreatmentState(Integer flagTreatmentState) {
		this.flagTreatmentState = flagTreatmentState;
	}

	public Date getTreatmentDateStart() {
		return treatmentDateStart;
	}

	public void setTreatmentDateStart(Date treatmentDateStart) {
		this.treatmentDateStart = treatmentDateStart;
	}

	public Date getTreatmentDateEnd() {
		return treatmentDateEnd;
	}

	public void setTreatmentDateEnd(Date treatmentDateEnd) {
		this.treatmentDateEnd = treatmentDateEnd;
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

	public Date getDoctorReceiveDate() {
		return doctorReceiveDate;
	}

	public void setDoctorReceiveDate(Date doctorReceiveDate) {
		this.doctorReceiveDate = doctorReceiveDate;
	}

	public String getDoctorReceiveTimeSlot() {
		return doctorReceiveTimeSlot;
	}

	public void setDoctorReceiveTimeSlot(String doctorReceiveTimeSlot) {
		this.doctorReceiveTimeSlot = doctorReceiveTimeSlot;
	}

	public Integer getLimitImgText() {
		return limitImgText;
	}

	public void setLimitImgText(Integer limitImgText) {
		this.limitImgText = limitImgText;
	}

	public Integer getLimitPhone() {
		return limitPhone;
	}

	public void setLimitPhone(Integer limitPhone) {
		this.limitPhone = limitPhone;
	}

	public Integer getLimitAudio() {
		return limitAudio;
	}

	public void setLimitAudio(Integer limitAudio) {
		this.limitAudio = limitAudio;
	}

	public Integer getLimitVideo() {
		return limitVideo;
	}

	public void setLimitVideo(Integer limitVideo) {
		this.limitVideo = limitVideo;
	}

	public Integer getLimitSigning() {
		return limitSigning;
	}

	public void setLimitSigning(Integer limitSigning) {
		this.limitSigning = limitSigning;
	}

	public Date getLimitSigningExpireDate() {
		return limitSigningExpireDate;
	}

	public void setLimitSigningExpireDate(Date limitSigningExpireDate) {
		this.limitSigningExpireDate = limitSigningExpireDate;
	}

	public Integer getTreatmentFlagUseState() {
		return treatmentFlagUseState;
	}

	public void setTreatmentFlagUseState(Integer treatmentFlagUseState) {
		this.treatmentFlagUseState = treatmentFlagUseState;
	}

	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
	}

	public String getFlagOrderStateName() {
		return flagOrderStateName;
	}

	public void setFlagOrderStateName(String flagOrderStateName) {
		this.flagOrderStateName = flagOrderStateName;
	}

	public String getPaymentModeName() {
		return paymentModeName;
	}

	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	public Integer getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(Integer serviceCount) {
		this.serviceCount = serviceCount;
	}

	public String getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(String serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public String getServiceStopDate() {
		return serviceStopDate;
	}

	public void setServiceStopDate(String serviceStopDate) {
		this.serviceStopDate = serviceStopDate;
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
}
