package entity.mySelf;

import java.util.Date;

/**
 * 患者信息
 * 
 * @author JiaQ
 */
public class UpProvideViewSysUserPatientInfoAndRegion implements java.io.Serializable {
	private String loginPatientPosition;
	private String requestClientType;
	private String operPatientCode;
	private String patientId;//用户ID
	private String userName;//姓名
	private String userNameAlias;//姓名别名
	private String flagUserNameAliasStatus;//状态
	private String gender;//性别.0:未知;1:男;2:女;
	private String birthdayStr;//生日
	private String province;//用户所在省份
	private String city;//用户所在城市
	private String area;//用户所在区(县)
	private String address;//地址
	private String base64ImgData;//民族

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

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameAlias() {
		return userNameAlias;
	}

	public void setUserNameAlias(String userNameAlias) {
		this.userNameAlias = userNameAlias;
	}

	public String getFlagUserNameAliasStatus() {
		return flagUserNameAliasStatus;
	}

	public void setFlagUserNameAliasStatus(String flagUserNameAliasStatus) {
		this.flagUserNameAliasStatus = flagUserNameAliasStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBase64ImgData() {
		return base64ImgData;
	}

	public void setBase64ImgData(String base64ImgData) {
		this.base64ImgData = base64ImgData;
	}
}
