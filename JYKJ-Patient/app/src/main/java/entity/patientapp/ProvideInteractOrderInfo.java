package entity.patientapp;

import java.util.Date;


/**
 * 【医患互动】
 * 患者就诊-订单信息
 * 
 * @author JiaQ
 */

public class ProvideInteractOrderInfo implements java.io.Serializable {
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
	private Float actualPayment;//实付款(实际支付的总价格)
	private String couponRecordHistoryCode;//使用的咨询优惠券记录编码
	private String integralRecordHistoryCode;//使用的积分记录编码
	private String orderDesc;//订单描述.Eg.音频就诊 128元/10分钟
	private Integer flagOrderState;//订单状态.0:未知;1:待付款;2:已付款;3:;4:;5:;10:已完成;11:交易关闭(未付款超时);
	private Date treatmentDate;//就诊日期(音视频就诊需要)
	private String treatmentTimeSlot;//就诊时间段(音视频就诊需要)
	private String treatmentLinkPhone;//就诊联系电话(电话就诊需要)
	
	  
}
