package entity.mySelf.usercenter;

import java.util.Date;

/**
 * 【医患互动】
 * <就诊订单>和<就诊重要数据>和<问诊资料>合并视图
 * 
 * @author JiaQ
 */
public class ProvideViewDoctorReceiveOrderAndTreatmentAndInterrogation implements java.io.Serializable {

	/************************** interact_order_info **************************/
	private Integer orderId;//订单编号
	private String orderCode;//订单关联编码
	private String treatmentSerialNum;//就诊流水号
	private String treatmentCardNum;//诊疗卡
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
	private String couponRecordHistoryCode;//使用的咨询优惠券记录编码
	private String integralRecordHistoryCode;//使用的积分记录编码
	private String orderDesc;//订单描述.Eg.音频就诊 128元/10分钟	
	private Integer flagOrderState;//订单状态.0:未知;1:待付款;2:已付款;3:;4:;5:;10:已完成;11:交易关闭(未付款超时);
	private Integer flagDel;
	private String remark;

	/************************** interact_order_treatment **************************/
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
	/*************** 【非签约患者】服务时长数据部分 Start ***************/
	private Integer limitImgText;//图文就诊(限制)-医生解答剩余数量
	private Integer limitPhone;//电话就诊(限制)-剩余通话时间
	private Integer limitAudio;//音频就诊(限制)-剩余通话时间
	private Integer limitVideo;//视频就诊(限制)-剩余通话时间
	/*************** 【非签约患者】服务时长数据部分 End ***************/
//	private Integer limitSigning;//签约服务(限制)-签约天数
//	private Date limitSigningExpireDate;//签约服务(限制)-到期日期
	private Integer treatmentFlagUseState;//使用状态.0:未使用;1:使用
	
	/************************** interact_patient_interrogation **************************/
	private String interrogationPatientCode;//(问诊人)患者关联编码.外键:sys_user_patient_info
	private String interrogationPatientName;//(问诊人)[冗余]患者姓名
	private String interrogationPatientLinkPhone;//(问诊人)患者手机号(电话就诊时,接听医生来电)
	private Integer interrogationGender;//性别.0:未知;1:男;2:女;
	private String interrogationBirthday;//年龄(或者存储出生日期)
	private Integer interrogationFlagUseState;//使用状态.0:未使用;1:使用中
	private Integer interrogationFlagDel;
	
	/************************** interact_order_treatment_signing **************************/
	private Integer limitSigningMonth;
	private Date limitSigningStartDate;
	private Date limitSigningExpireDate;
	/*************** 【签约患者】服务时长数据部分 Start ***************/
	private Integer limitImgTextSigning;
	private Integer limitPhoneSigning;
	private Integer limitAudioSigning;
	private Integer limitVideoSigning;
	/*************** 【签约患者】服务时长数据部分 End ***************/
	private Integer signingFlagUseState;
	
	
	
	
	
	
	/************************** 非对称属性 **************************/
	private String treatmentTypeName;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	private String treatmentTimeSlotName;//患者预约就诊时间段(电话\\音频\\视频就诊需要).0:未知;1:早;2:中;3:晚;
	
	private Integer limitImgTextShow;//值为-1时，无限制
	private Integer limitPhoneShow;//限制时长
	private Integer limitAudioShow;//限制时长
	private Integer limitVideoShow;//限制时长
	
	private String doctorReceiveShow;//医生接诊时间(当前状态)
	private Integer flagColor;//颜色标识.0:正常色;1:红色;
	
	
	//private String sourceTypeIdName;//来源(数据)类型.1:平台内就诊记录;2:平台外就诊记录;
	//private String flagTreatmentStateName;//医生接诊状态标识.0:未支付(等待支付状态);1:已支付(可开始接诊);2:接诊中(医生开始回复或操作);3:接诊结束(等待回复诊后留言);11:接诊完成(诊后留言已回复);


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

	public Integer getFlagDel() {
		return flagDel;
	}

	public void setFlagDel(Integer flagDel) {
		this.flagDel = flagDel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getTreatmentFlagUseState() {
		return treatmentFlagUseState;
	}

	public void setTreatmentFlagUseState(Integer treatmentFlagUseState) {
		this.treatmentFlagUseState = treatmentFlagUseState;
	}

	public String getInterrogationPatientCode() {
		return interrogationPatientCode;
	}

	public void setInterrogationPatientCode(String interrogationPatientCode) {
		this.interrogationPatientCode = interrogationPatientCode;
	}

	public String getInterrogationPatientName() {
		return interrogationPatientName;
	}

	public void setInterrogationPatientName(String interrogationPatientName) {
		this.interrogationPatientName = interrogationPatientName;
	}

	public String getInterrogationPatientLinkPhone() {
		return interrogationPatientLinkPhone;
	}

	public void setInterrogationPatientLinkPhone(String interrogationPatientLinkPhone) {
		this.interrogationPatientLinkPhone = interrogationPatientLinkPhone;
	}

	public Integer getInterrogationGender() {
		return interrogationGender;
	}

	public void setInterrogationGender(Integer interrogationGender) {
		this.interrogationGender = interrogationGender;
	}

	public String getInterrogationBirthday() {
		return interrogationBirthday;
	}

	public void setInterrogationBirthday(String interrogationBirthday) {
		this.interrogationBirthday = interrogationBirthday;
	}

	public Integer getInterrogationFlagUseState() {
		return interrogationFlagUseState;
	}

	public void setInterrogationFlagUseState(Integer interrogationFlagUseState) {
		this.interrogationFlagUseState = interrogationFlagUseState;
	}

	public Integer getInterrogationFlagDel() {
		return interrogationFlagDel;
	}

	public void setInterrogationFlagDel(Integer interrogationFlagDel) {
		this.interrogationFlagDel = interrogationFlagDel;
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

	public Integer getLimitImgTextSigning() {
		return limitImgTextSigning;
	}

	public void setLimitImgTextSigning(Integer limitImgTextSigning) {
		this.limitImgTextSigning = limitImgTextSigning;
	}

	public Integer getLimitPhoneSigning() {
		return limitPhoneSigning;
	}

	public void setLimitPhoneSigning(Integer limitPhoneSigning) {
		this.limitPhoneSigning = limitPhoneSigning;
	}

	public Integer getLimitAudioSigning() {
		return limitAudioSigning;
	}

	public void setLimitAudioSigning(Integer limitAudioSigning) {
		this.limitAudioSigning = limitAudioSigning;
	}

	public Integer getLimitVideoSigning() {
		return limitVideoSigning;
	}

	public void setLimitVideoSigning(Integer limitVideoSigning) {
		this.limitVideoSigning = limitVideoSigning;
	}

	public Integer getSigningFlagUseState() {
		return signingFlagUseState;
	}

	public void setSigningFlagUseState(Integer signingFlagUseState) {
		this.signingFlagUseState = signingFlagUseState;
	}

	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
	}

	public String getTreatmentTimeSlotName() {
		return treatmentTimeSlotName;
	}

	public void setTreatmentTimeSlotName(String treatmentTimeSlotName) {
		this.treatmentTimeSlotName = treatmentTimeSlotName;
	}

	public Integer getLimitImgTextShow() {
		return limitImgTextShow;
	}

	public void setLimitImgTextShow(Integer limitImgTextShow) {
		this.limitImgTextShow = limitImgTextShow;
	}

	public Integer getLimitPhoneShow() {
		return limitPhoneShow;
	}

	public void setLimitPhoneShow(Integer limitPhoneShow) {
		this.limitPhoneShow = limitPhoneShow;
	}

	public Integer getLimitAudioShow() {
		return limitAudioShow;
	}

	public void setLimitAudioShow(Integer limitAudioShow) {
		this.limitAudioShow = limitAudioShow;
	}

	public Integer getLimitVideoShow() {
		return limitVideoShow;
	}

	public void setLimitVideoShow(Integer limitVideoShow) {
		this.limitVideoShow = limitVideoShow;
	}

	public String getDoctorReceiveShow() {
		return doctorReceiveShow;
	}

	public void setDoctorReceiveShow(String doctorReceiveShow) {
		this.doctorReceiveShow = doctorReceiveShow;
	}

	public Integer getFlagColor() {
		return flagColor;
	}

	public void setFlagColor(Integer flagColor) {
		this.flagColor = flagColor;
	}
}
