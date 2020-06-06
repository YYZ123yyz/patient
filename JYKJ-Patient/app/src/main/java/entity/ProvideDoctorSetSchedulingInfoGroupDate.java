package entity;

import java.util.Date;
import java.util.List;


/**
 * 【前端使用】【数据展示使用】【数据分组】
 * 医生排班记录表-坐诊日期
 * 
 * @author JiaQ
 */
public class ProvideDoctorSetSchedulingInfoGroupDate implements java.io.Serializable {
	private Date workDate;//坐诊日期
	private Integer workDateType;//坐诊日期类型.Eg.0/1/2
	private String workDateName;//坐诊日期类型名称.Eg.星期日/星期一/星期二	
	List<ProvideDoctorSetSchedulingInfoGroupTime> groupTimeList;//医生排班记录表-坐诊时间段
	private	boolean choice;

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Integer getWorkDateType() {
		return workDateType;
	}

	public void setWorkDateType(Integer workDateType) {
		this.workDateType = workDateType;
	}

	public String getWorkDateName() {
		return workDateName;
	}

	public void setWorkDateName(String workDateName) {
		this.workDateName = workDateName;
	}

	public List<ProvideDoctorSetSchedulingInfoGroupTime> getGroupTimeList() {
		return groupTimeList;
	}

	public void setGroupTimeList(List<ProvideDoctorSetSchedulingInfoGroupTime> groupTimeList) {
		this.groupTimeList = groupTimeList;
	}
}