package com.hyphenate.easeui.entity;

import java.util.Date;


/**
 * 患者信息
 * 
 * @author JiaQ
 */

public class ProvideViewSysUserPatientInfoAndRegion implements java.io.Serializable {
	
	private Integer patientId;//用户ID
	private String patientCode;//用户编码
	private Integer userUseType;//用户标识.6:患者;
	private String linkPhone;//登录手机号
	private String userAccount;//登录账号
	private String email;//邮箱
	private String userLabelName;//患者标签名称
	private String userName;//姓名
	private String userNameAlias;//姓名别名
	private String userNameSpell;//姓名拼音助记码
	private String qrCode;//二维码
	private String userLogoUrl;//用户头像
	private String idNumber;//身份证号
	private String nativePlace;//籍贯
	private Integer gender;//性别.0:未知;1:男;2:女;
	private Date birthday;//生日
	private String country;//用户所在国家
	private String province;//用户所在省份
	private String provinceName;//用户所在省份名称
	private String city;//用户所在城市
	private String cityName;//用户所在城市名称
	private String area;//用户所在区(县)
	private String areaName;//用户所在区(县)名称
	private String address;//地址
	private String nation;//民族
	private Integer flagPatientStatus;//患者认证状态(0:未认证;1:已认证;)
	private String newLoginDate;//最后一次登录日期

	private String doctorShareUrl;//分享链接地址（医生）
	private String doctorShareTitle;//分享标题（医生）
	private String doctorShareContent;//分享内容（医生）

	private String patientShareUrl;//分享出去链接地址（患者）
	private String patientShareTitle;//分享标题（医生）
	private String patientShareContent;//分享内容（医生）

	private String showRegionInfo;//展示地区信息

	private String userCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getDoctorShareUrl() {
		return doctorShareUrl;
	}

	public void setDoctorShareUrl(String doctorShareUrl) {
		this.doctorShareUrl = doctorShareUrl;
	}

	public String getDoctorShareTitle() {
		return doctorShareTitle;
	}

	public void setDoctorShareTitle(String doctorShareTitle) {
		this.doctorShareTitle = doctorShareTitle;
	}

	public String getDoctorShareContent() {
		return doctorShareContent;
	}

	public void setDoctorShareContent(String doctorShareContent) {
		this.doctorShareContent = doctorShareContent;
	}

	public String getPatientShareUrl() {
		return patientShareUrl;
	}

	public void setPatientShareUrl(String patientShareUrl) {
		this.patientShareUrl = patientShareUrl;
	}

	public String getPatientShareTitle() {
		return patientShareTitle;
	}

	public void setPatientShareTitle(String patientShareTitle) {
		this.patientShareTitle = patientShareTitle;
	}

	public String getPatientShareContent() {
		return patientShareContent;
	}

	public void setPatientShareContent(String patientShareContent) {
		this.patientShareContent = patientShareContent;
	}

	public String getShowRegionInfo() {
		return showRegionInfo;
	}

	public void setShowRegionInfo(String showRegionInfo) {
		this.showRegionInfo = showRegionInfo;
	}

	private String loginPatientPosition;
	private String requestClientType;
	private String userPhone;
	private String userPwd;
	private String sendUserLinkPhone;
	private String userRegisterSmsVerify;
    private String tokenSmsVerify;
	private String userLoginSmsVerify;
	private String newPassWord;
	private String smsVerifyData;
	private String smsVerifyTokenData;

	private String openId;

	private String operPatientCode;
	private String operPatientName;
	private String base64ImgData;

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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSmsVerifyTokenData() {
		return smsVerifyTokenData;
	}

	public void setSmsVerifyTokenData(String smsVerifyTokenData) {
		this.smsVerifyTokenData = smsVerifyTokenData;
	}

	public String getSmsVerifyData() {
		return smsVerifyData;
	}

	public void setSmsVerifyData(String smsVerifyData) {
		this.smsVerifyData = smsVerifyData;
	}

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getUserLoginSmsVerify() {
		return userLoginSmsVerify;
	}

	public void setUserLoginSmsVerify(String userLoginSmsVerify) {
		this.userLoginSmsVerify = userLoginSmsVerify;
	}

	public String getTokenSmsVerify() {
        return tokenSmsVerify;
    }

    public void setTokenSmsVerify(String tokenSmsVerify) {
        this.tokenSmsVerify = tokenSmsVerify;
    }

    public String getUserRegisterSmsVerify() {
        return userRegisterSmsVerify;
    }

    public void setUserRegisterSmsVerify(String userRegisterSmsVerify) {
        this.userRegisterSmsVerify = userRegisterSmsVerify;
    }

    public String getSendUserLinkPhone() {
        return sendUserLinkPhone;
    }

    public void setSendUserLinkPhone(String sendUserLinkPhone) {
        this.sendUserLinkPhone = sendUserLinkPhone;
    }

    public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public Integer getUserUseType() {
		return userUseType;
	}

	public void setUserUseType(Integer userUseType) {
		this.userUseType = userUseType;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserLabelName() {
		return userLabelName;
	}

	public void setUserLabelName(String userLabelName) {
		this.userLabelName = userLabelName;
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

	public String getUserNameSpell() {
		return userNameSpell;
	}

	public void setUserNameSpell(String userNameSpell) {
		this.userNameSpell = userNameSpell;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getUserLogoUrl() {
		return userLogoUrl;
	}

	public void setUserLogoUrl(String userLogoUrl) {
		this.userLogoUrl = userLogoUrl;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Integer getFlagPatientStatus() {
		return flagPatientStatus;
	}

	public void setFlagPatientStatus(Integer flagPatientStatus) {
		this.flagPatientStatus = flagPatientStatus;
	}

	public String getNewLoginDate() {
		return newLoginDate;
	}

	public void setNewLoginDate(String newLoginDate) {
		this.newLoginDate = newLoginDate;
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

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getBase64ImgData() {
		return base64ImgData;
	}

	public void setBase64ImgData(String base64ImgData) {
		this.base64ImgData = base64ImgData;
	}
}
