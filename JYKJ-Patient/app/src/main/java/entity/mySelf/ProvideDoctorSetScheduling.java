package entity.mySelf;


/**
 * 医生设置类-排班
 * 
 * @author JiaQ
 */

public class ProvideDoctorSetScheduling implements java.io.Serializable {
	private Integer schedulingSetId;//就诊类型(服务权限)设置编号
	private String doctorCode;//医生关联编码.外键:sys_user_doctor_info
	private Integer mondayMorning;//[周一][早]是否坐诊.0:否;1:是;
	private Integer mondayMorningSourceNum;//[预留][周一][早]剩余号源数量
	private Integer mondayNoon;//[周一][中]是否坐诊.0:否;1:是;
	private Integer mondayNoonSourceNum;//[预留][周一][中]剩余号源数量
	private Integer mondayNight;//[周一][晚]是否坐诊.0:否;1:是;
	private Integer mondayNightSourceNum;//[预留][周一][晚]剩余号源数量
	private Integer tuesdayMorning;//[周二][早]是否坐诊.0:否;1:是;
	private Integer tuesdayMorningSourceNum;//[预留][周二][早]剩余号源数量
	private Integer tuesdayNoon;//[周二][中]是否坐诊.0:否;1:是;
	private Integer tuesdayNoonSourceNum;//[预留][周二][中]剩余号源数量
	private Integer tuesdayNight;//[周二][晚]是否坐诊.0:否;1:是;
	private Integer tuesdayNightSourceNum;//[预留][周二][晚]剩余号源数量
	private Integer wednesdayMorning;//[周三][早]是否坐诊.0:否;1:是;
	private Integer wednesdayMorningSourceNum;//[预留][周三][早]剩余号源数量
	private Integer wednesdayNoon;//[周三][中]是否坐诊.0:否;1:是;
	private Integer wednesdayNoonSourceNum;//[预留][周三][中]剩余号源数量
	private Integer wednesdayNight;//[周三][晚]是否坐诊.0:否;1:是;
	private Integer wednesdayNightSourceNum;//[预留][周三][晚]剩余号源数量
	private Integer thursdayMorning;//[周四][早]是否坐诊.0:否;1:是;
	private Integer thursdayMorningSourceNum;//[预留][周四][早]剩余号源数量
	private Integer thursdayNoon;//[周四][中]是否坐诊.0:否;1:是;
	private Integer thursdayNoonSourceNum;//[预留][周四][中]剩余号源数量
	private Integer thursdayNight;//[周四][晚]是否坐诊.0:否;1:是;
	private Integer thursdayNightSourceNum;//[预留][周四][晚]剩余号源数量
	private Integer fridayMorning;//[周五][早]是否坐诊.0:否;1:是;
	private Integer fridayMorningSourceNum;//[预留][周五][早]剩余号源数量
	private Integer fridayNoon;//[周五][中]是否坐诊.0:否;1:是;
	private Integer fridayNoonSourceNum;//[预留][周五][中]剩余号源数量
	private Integer fridayNight;//[周五][晚]是否坐诊.0:否;1:是;
	private Integer fridayNightSourceNum;//[预留][周五][晚]剩余号源数量
	private Integer saturdayMorning;//[周六][早]是否坐诊.0:否;1:是;
	private Integer saturdayMorningSourceNum;//[预留][周六][早]剩余号源数量
	private Integer saturdayNoon;//[周六][中]是否坐诊.0:否;1:是;
	private Integer saturdayNoonSourceNum;//[预留][周六][中]剩余号源数量
	private Integer saturdayNight;//[周六][晚]是否坐诊.0:否;1:是;
	private Integer saturdayNightSourceNum;//[预留][周六][晚]剩余号源数量
	private Integer sundayMorning;//[周日][早]是否坐诊.0:否;1:是;
	private Integer sundayMorningSourceNum;//[预留][周日][早]剩余号源数量
	private Integer sundayNoon;//[周日][中]是否坐诊.0:否;1:是;
	private Integer sundayNoonSourceNum;//[预留][周日][中]剩余号源数量
	private Integer sundayNight;//[周日][晚]是否坐诊.0:否;1:是;
	private Integer sundayNightSourceNum;//[预留][周日][晚]剩余号源数量
	private Integer flagUseState;//使用状态.0:未使用;1:使用中;

	private	String loginDoctorPosition;			//
	private	String operDoctorCode;			//
	private	String operDoctorName;			//

	private	String loginPatientPosition;			//
	private	String requestClientType;			//
	private	String searchDoctorCode;			//
	private	String searchDoctorName;			//

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

	public String getSearchDoctorCode() {
		return searchDoctorCode;
	}

	public void setSearchDoctorCode(String searchDoctorCode) {
		this.searchDoctorCode = searchDoctorCode;
	}

	public String getSearchDoctorName() {
		return searchDoctorName;
	}

	public void setSearchDoctorName(String searchDoctorName) {
		this.searchDoctorName = searchDoctorName;
	}

	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
	}

	public String getOperDoctorCode() {
		return operDoctorCode;
	}

	public void setOperDoctorCode(String operDoctorCode) {
		this.operDoctorCode = operDoctorCode;
	}

	public String getOperDoctorName() {
		return operDoctorName;
	}

	public void setOperDoctorName(String operDoctorName) {
		this.operDoctorName = operDoctorName;
	}

	public Integer getSchedulingSetId() {
		return schedulingSetId;
	}

	public void setSchedulingSetId(Integer schedulingSetId) {
		this.schedulingSetId = schedulingSetId;
	}

	public String getPatientCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public Integer getMondayMorning() {
		return mondayMorning;
	}

	public void setMondayMorning(Integer mondayMorning) {
		this.mondayMorning = mondayMorning;
	}

	public Integer getMondayMorningSourceNum() {
		return mondayMorningSourceNum;
	}

	public void setMondayMorningSourceNum(Integer mondayMorningSourceNum) {
		this.mondayMorningSourceNum = mondayMorningSourceNum;
	}

	public Integer getMondayNoon() {
		return mondayNoon;
	}

	public void setMondayNoon(Integer mondayNoon) {
		this.mondayNoon = mondayNoon;
	}

	public Integer getMondayNoonSourceNum() {
		return mondayNoonSourceNum;
	}

	public void setMondayNoonSourceNum(Integer mondayNoonSourceNum) {
		this.mondayNoonSourceNum = mondayNoonSourceNum;
	}

	public Integer getMondayNight() {
		return mondayNight;
	}

	public void setMondayNight(Integer mondayNight) {
		this.mondayNight = mondayNight;
	}

	public Integer getMondayNightSourceNum() {
		return mondayNightSourceNum;
	}

	public void setMondayNightSourceNum(Integer mondayNightSourceNum) {
		this.mondayNightSourceNum = mondayNightSourceNum;
	}

	public Integer getTuesdayMorning() {
		return tuesdayMorning;
	}

	public void setTuesdayMorning(Integer tuesdayMorning) {
		this.tuesdayMorning = tuesdayMorning;
	}

	public Integer getTuesdayMorningSourceNum() {
		return tuesdayMorningSourceNum;
	}

	public void setTuesdayMorningSourceNum(Integer tuesdayMorningSourceNum) {
		this.tuesdayMorningSourceNum = tuesdayMorningSourceNum;
	}

	public Integer getTuesdayNoon() {
		return tuesdayNoon;
	}

	public void setTuesdayNoon(Integer tuesdayNoon) {
		this.tuesdayNoon = tuesdayNoon;
	}

	public Integer getTuesdayNoonSourceNum() {
		return tuesdayNoonSourceNum;
	}

	public void setTuesdayNoonSourceNum(Integer tuesdayNoonSourceNum) {
		this.tuesdayNoonSourceNum = tuesdayNoonSourceNum;
	}

	public Integer getTuesdayNight() {
		return tuesdayNight;
	}

	public void setTuesdayNight(Integer tuesdayNight) {
		this.tuesdayNight = tuesdayNight;
	}

	public Integer getTuesdayNightSourceNum() {
		return tuesdayNightSourceNum;
	}

	public void setTuesdayNightSourceNum(Integer tuesdayNightSourceNum) {
		this.tuesdayNightSourceNum = tuesdayNightSourceNum;
	}

	public Integer getWednesdayMorning() {
		return wednesdayMorning;
	}

	public void setWednesdayMorning(Integer wednesdayMorning) {
		this.wednesdayMorning = wednesdayMorning;
	}

	public Integer getWednesdayMorningSourceNum() {
		return wednesdayMorningSourceNum;
	}

	public void setWednesdayMorningSourceNum(Integer wednesdayMorningSourceNum) {
		this.wednesdayMorningSourceNum = wednesdayMorningSourceNum;
	}

	public Integer getWednesdayNoon() {
		return wednesdayNoon;
	}

	public void setWednesdayNoon(Integer wednesdayNoon) {
		this.wednesdayNoon = wednesdayNoon;
	}

	public Integer getWednesdayNoonSourceNum() {
		return wednesdayNoonSourceNum;
	}

	public void setWednesdayNoonSourceNum(Integer wednesdayNoonSourceNum) {
		this.wednesdayNoonSourceNum = wednesdayNoonSourceNum;
	}

	public Integer getWednesdayNight() {
		return wednesdayNight;
	}

	public void setWednesdayNight(Integer wednesdayNight) {
		this.wednesdayNight = wednesdayNight;
	}

	public Integer getWednesdayNightSourceNum() {
		return wednesdayNightSourceNum;
	}

	public void setWednesdayNightSourceNum(Integer wednesdayNightSourceNum) {
		this.wednesdayNightSourceNum = wednesdayNightSourceNum;
	}

	public Integer getThursdayMorning() {
		return thursdayMorning;
	}

	public void setThursdayMorning(Integer thursdayMorning) {
		this.thursdayMorning = thursdayMorning;
	}

	public Integer getThursdayMorningSourceNum() {
		return thursdayMorningSourceNum;
	}

	public void setThursdayMorningSourceNum(Integer thursdayMorningSourceNum) {
		this.thursdayMorningSourceNum = thursdayMorningSourceNum;
	}

	public Integer getThursdayNoon() {
		return thursdayNoon;
	}

	public void setThursdayNoon(Integer thursdayNoon) {
		this.thursdayNoon = thursdayNoon;
	}

	public Integer getThursdayNoonSourceNum() {
		return thursdayNoonSourceNum;
	}

	public void setThursdayNoonSourceNum(Integer thursdayNoonSourceNum) {
		this.thursdayNoonSourceNum = thursdayNoonSourceNum;
	}

	public Integer getThursdayNight() {
		return thursdayNight;
	}

	public void setThursdayNight(Integer thursdayNight) {
		this.thursdayNight = thursdayNight;
	}

	public Integer getThursdayNightSourceNum() {
		return thursdayNightSourceNum;
	}

	public void setThursdayNightSourceNum(Integer thursdayNightSourceNum) {
		this.thursdayNightSourceNum = thursdayNightSourceNum;
	}

	public Integer getFridayMorning() {
		return fridayMorning;
	}

	public void setFridayMorning(Integer fridayMorning) {
		this.fridayMorning = fridayMorning;
	}

	public Integer getFridayMorningSourceNum() {
		return fridayMorningSourceNum;
	}

	public void setFridayMorningSourceNum(Integer fridayMorningSourceNum) {
		this.fridayMorningSourceNum = fridayMorningSourceNum;
	}

	public Integer getFridayNoon() {
		return fridayNoon;
	}

	public void setFridayNoon(Integer fridayNoon) {
		this.fridayNoon = fridayNoon;
	}

	public Integer getFridayNoonSourceNum() {
		return fridayNoonSourceNum;
	}

	public void setFridayNoonSourceNum(Integer fridayNoonSourceNum) {
		this.fridayNoonSourceNum = fridayNoonSourceNum;
	}

	public Integer getFridayNight() {
		return fridayNight;
	}

	public void setFridayNight(Integer fridayNight) {
		this.fridayNight = fridayNight;
	}

	public Integer getFridayNightSourceNum() {
		return fridayNightSourceNum;
	}

	public void setFridayNightSourceNum(Integer fridayNightSourceNum) {
		this.fridayNightSourceNum = fridayNightSourceNum;
	}

	public Integer getSaturdayMorning() {
		return saturdayMorning;
	}

	public void setSaturdayMorning(Integer saturdayMorning) {
		this.saturdayMorning = saturdayMorning;
	}

	public Integer getSaturdayMorningSourceNum() {
		return saturdayMorningSourceNum;
	}

	public void setSaturdayMorningSourceNum(Integer saturdayMorningSourceNum) {
		this.saturdayMorningSourceNum = saturdayMorningSourceNum;
	}

	public Integer getSaturdayNoon() {
		return saturdayNoon;
	}

	public void setSaturdayNoon(Integer saturdayNoon) {
		this.saturdayNoon = saturdayNoon;
	}

	public Integer getSaturdayNoonSourceNum() {
		return saturdayNoonSourceNum;
	}

	public void setSaturdayNoonSourceNum(Integer saturdayNoonSourceNum) {
		this.saturdayNoonSourceNum = saturdayNoonSourceNum;
	}

	public Integer getSaturdayNight() {
		return saturdayNight;
	}

	public void setSaturdayNight(Integer saturdayNight) {
		this.saturdayNight = saturdayNight;
	}

	public Integer getSaturdayNightSourceNum() {
		return saturdayNightSourceNum;
	}

	public void setSaturdayNightSourceNum(Integer saturdayNightSourceNum) {
		this.saturdayNightSourceNum = saturdayNightSourceNum;
	}

	public Integer getSundayMorning() {
		return sundayMorning;
	}

	public void setSundayMorning(Integer sundayMorning) {
		this.sundayMorning = sundayMorning;
	}

	public Integer getSundayMorningSourceNum() {
		return sundayMorningSourceNum;
	}

	public void setSundayMorningSourceNum(Integer sundayMorningSourceNum) {
		this.sundayMorningSourceNum = sundayMorningSourceNum;
	}

	public Integer getSundayNoon() {
		return sundayNoon;
	}

	public void setSundayNoon(Integer sundayNoon) {
		this.sundayNoon = sundayNoon;
	}

	public Integer getSundayNoonSourceNum() {
		return sundayNoonSourceNum;
	}

	public void setSundayNoonSourceNum(Integer sundayNoonSourceNum) {
		this.sundayNoonSourceNum = sundayNoonSourceNum;
	}

	public Integer getSundayNight() {
		return sundayNight;
	}

	public void setSundayNight(Integer sundayNight) {
		this.sundayNight = sundayNight;
	}

	public Integer getSundayNightSourceNum() {
		return sundayNightSourceNum;
	}

	public void setSundayNightSourceNum(Integer sundayNightSourceNum) {
		this.sundayNightSourceNum = sundayNightSourceNum;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
	}
}
