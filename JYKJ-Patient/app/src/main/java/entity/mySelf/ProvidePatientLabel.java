package entity.mySelf;

import java.util.Date;


/**
 * 【基础类】
 * 患者标签记录
 * 
 * @author JiaQ
 */
public class ProvidePatientLabel implements java.io.Serializable {
	
	private Integer userLabelId;//患者标签数据编号
	private String patientCode;//患者编码
	private Integer userLabel;//患者标签类型[大类]
	private Integer userLabelSecond;//患者标签类型[小类]
	private String userLabelSecondName;//标签名称
	private Integer flagUseState;//标签使用状态.0:未使用;1:使用中;
	
	private Date createDate;//记录日期
	private String createMan;//记录人

	public Integer getUserLabelId() {
		return userLabelId;
	}

	public void setUserLabelId(Integer userLabelId) {
		this.userLabelId = userLabelId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public Integer getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(Integer userLabel) {
		this.userLabel = userLabel;
	}

	public Integer getUserLabelSecond() {
		return userLabelSecond;
	}

	public void setUserLabelSecond(Integer userLabelSecond) {
		this.userLabelSecond = userLabelSecond;
	}

	public String getUserLabelSecondName() {
		return userLabelSecondName;
	}

	public void setUserLabelSecondName(String userLabelSecondName) {
		this.userLabelSecondName = userLabelSecondName;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}
}