package entity;


/**
 * 【前端使用】【数据展示使用】【数据分组】
 * 医生排班记录表-坐诊时间段
 * 
 * @author JiaQ
 */
public class ProvideDoctorSetSchedulingInfoGroupTime implements java.io.Serializable {
	private Integer schedulingInfoId;//医生排班记录编号
	private Integer workState;//出诊状态.0:未处理;1:不出诊;2:已过期;3:正常出诊;
	
	private Integer dayTimeType;//时间类型.0:未知;1:早;2:中;3:晚;
	private String dayTimeSlot;//时间段(时间类型).Eg.8:00-12:00
	private Integer workSourceNum;//坐诊号源剩余数量

	private boolean choice;			//是否选中

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}

	public Integer getSchedulingInfoId() {
		return schedulingInfoId;
	}

	public void setSchedulingInfoId(Integer schedulingInfoId) {
		this.schedulingInfoId = schedulingInfoId;
	}

	public Integer getWorkState() {
		return workState;
	}

	public void setWorkState(Integer workState) {
		this.workState = workState;
	}

	public Integer getDayTimeType() {
		return dayTimeType;
	}

	public void setDayTimeType(Integer dayTimeType) {
		this.dayTimeType = dayTimeType;
	}

	public String getDayTimeSlot() {
		return dayTimeSlot;
	}

	public void setDayTimeSlot(String dayTimeSlot) {
		this.dayTimeSlot = dayTimeSlot;
	}

	public Integer getWorkSourceNum() {
		return workSourceNum;
	}

	public void setWorkSourceNum(Integer workSourceNum) {
		this.workSourceNum = workSourceNum;
	}
}
