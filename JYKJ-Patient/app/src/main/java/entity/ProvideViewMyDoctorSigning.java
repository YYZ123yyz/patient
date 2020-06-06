package entity;

import java.util.Date;


/**
 * 【我的医生】
 * 签约类型
 * 
 * @author JiaQ
 */
public class ProvideViewMyDoctorSigning implements java.io.Serializable {
	private Integer dpBindingId;//编号
	private String dpBindingCode;//关联编码
	private String mainUserCode;//医生-关联编码
	private String bindingUserCode;//患者-关联编码	
	private Integer flagSigning;//是否签约.1:已签约;0:未签约;
	private Integer flagPatientSourceType;//患者类型.1:网上患者;2:医院患者;3:临时患者;
	private Integer flagPatientFeeType;//患者费用类型.1:付费患者;2:免费患者;
	
	private Integer treatmentSigningId;//患者就诊签约编号
	private String patientLinkPhone;//就诊联系电话(电话签约服务就诊需要)
	private Integer limitSigningMonth;//签约服务(限制)-签约月数
	private Date limitSigningStartDate;//签约服务(限制)-开始日期
	private Date limitSigningExpireDate;//签约服务(限制)-到期日期
	private Integer limitImgText;//图文就诊(限制)-医生解答剩余数量
	private Integer limitPhone;//电话就诊(限制)-剩余通话时间
	private Integer limitAudio;//音频就诊(限制)-剩余通话时间
	private Integer limitVideo;//视频就诊(限制)-剩余通话时间
	
	private Integer doctorId;//医生编号
	private String doctorCode;//医生编码
	private String linkPhone;//手机号
	private String userName;//姓名
	private String userNameAlias;//姓名别名
	private String userNameSpell;//姓名拼音助记码
	private String qrCode;//二维码
	private String userLogoUrl;//医生头像地址	
	private Integer gender;//性别(0:未知;1:男;2:女;)
	private Date birthday;//生日
	private String country;//所在国家
	private String province;//所在省份
	private String provinceName;//所在省份名称
	private String city;//所在市
	private String cityName;//所在市名称
	private String area;//所在区(县)
	private String areaName;//所在区(县)名称	
	private String hospitalInfoCode;//医院编码
	private String hospitalInfoName;//医院名称	
	private Integer hospitalType;//医院类型编码
	private String hospitalTypeName;//医院类型名称	
	private String departmentId;//一级科室编码
	private String departmentName;//一级科室名称
	private String departmentSecondId;//二级科室编码
	private String departmentSecondName;//二级科室名称
	private Integer doctorTitle;//医生职称编码
	private String doctorTitleName;//医生职称名称
	private String synopsis;//个人简介
	private String goodAtRealm;//擅长领域
	private Integer flagDoctorStatus;//医生认证状态(0:未认证;1:已认证;)
	private Date newLoginDate;//最后一次登录日期
	
	/************************* 【非对称属性】 *************************/
	private Integer flagSigningBtn;//咨询按钮是否可用(0:不可使用;1:可以使用;)


	private String rowNum;
	private String pageNum;
	private String loginUserPosition;
	private String requestClientType;
	private String operPatientCode;
	private String operPatientName;
	private String searchName;
	private String searchProvince;
	private String searchCity;
	private String searchArea;
	private String searchDepartmentId;
	private String searchDepartmentSecondId;
	private String searchHospitalType;
	private String searchDoctorTitle;

	public Integer getDpBindingId() {
		return dpBindingId;
	}

	public void setDpBindingId(Integer dpBindingId) {
		this.dpBindingId = dpBindingId;
	}

	public String getDpBindingCode() {
		return dpBindingCode;
	}

	public void setDpBindingCode(String dpBindingCode) {
		this.dpBindingCode = dpBindingCode;
	}

	public String getMainUserCode() {
		return mainUserCode;
	}

	public void setMainUserCode(String mainUserCode) {
		this.mainUserCode = mainUserCode;
	}

	public String getBindingUserCode() {
		return bindingUserCode;
	}

	public void setBindingUserCode(String bindingUserCode) {
		this.bindingUserCode = bindingUserCode;
	}

	public Integer getFlagSigning() {
		return flagSigning;
	}

	public void setFlagSigning(Integer flagSigning) {
		this.flagSigning = flagSigning;
	}

	public Integer getFlagPatientSourceType() {
		return flagPatientSourceType;
	}

	public void setFlagPatientSourceType(Integer flagPatientSourceType) {
		this.flagPatientSourceType = flagPatientSourceType;
	}

	public Integer getFlagPatientFeeType() {
		return flagPatientFeeType;
	}

	public void setFlagPatientFeeType(Integer flagPatientFeeType) {
		this.flagPatientFeeType = flagPatientFeeType;
	}

	public Integer getTreatmentSigningId() {
		return treatmentSigningId;
	}

	public void setTreatmentSigningId(Integer treatmentSigningId) {
		this.treatmentSigningId = treatmentSigningId;
	}

	public String getPatientLinkPhone() {
		return patientLinkPhone;
	}

	public void setPatientLinkPhone(String patientLinkPhone) {
		this.patientLinkPhone = patientLinkPhone;
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

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
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

	public Integer getHospitalType() {
		return hospitalType;
	}

	public void setHospitalType(Integer hospitalType) {
		this.hospitalType = hospitalType;
	}

	public String getHospitalTypeName() {
		return hospitalTypeName;
	}

	public void setHospitalTypeName(String hospitalTypeName) {
		this.hospitalTypeName = hospitalTypeName;
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

	public Integer getDoctorTitle() {
		return doctorTitle;
	}

	public void setDoctorTitle(Integer doctorTitle) {
		this.doctorTitle = doctorTitle;
	}

	public String getDoctorTitleName() {
		return doctorTitleName;
	}

	public void setDoctorTitleName(String doctorTitleName) {
		this.doctorTitleName = doctorTitleName;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getGoodAtRealm() {
		return goodAtRealm;
	}

	public void setGoodAtRealm(String goodAtRealm) {
		this.goodAtRealm = goodAtRealm;
	}

	public Integer getFlagDoctorStatus() {
		return flagDoctorStatus;
	}

	public void setFlagDoctorStatus(Integer flagDoctorStatus) {
		this.flagDoctorStatus = flagDoctorStatus;
	}

	public Date getNewLoginDate() {
		return newLoginDate;
	}

	public void setNewLoginDate(Date newLoginDate) {
		this.newLoginDate = newLoginDate;
	}

	public Integer getFlagSigningBtn() {
		return flagSigningBtn;
	}

	public void setFlagSigningBtn(Integer flagSigningBtn) {
		this.flagSigningBtn = flagSigningBtn;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getLoginUserPosition() {
		return loginUserPosition;
	}

	public void setLoginUserPosition(String loginUserPosition) {
		this.loginUserPosition = loginUserPosition;
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

	public String getOperPatientName() {
		return operPatientName;
	}

	public void setOperPatientName(String operPatientName) {
		this.operPatientName = operPatientName;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchProvince() {
		return searchProvince;
	}

	public void setSearchProvince(String searchProvince) {
		this.searchProvince = searchProvince;
	}

	public String getSearchCity() {
		return searchCity;
	}

	public void setSearchCity(String searchCity) {
		this.searchCity = searchCity;
	}

	public String getSearchArea() {
		return searchArea;
	}

	public void setSearchArea(String searchArea) {
		this.searchArea = searchArea;
	}

	public String getSearchDepartmentId() {
		return searchDepartmentId;
	}

	public void setSearchDepartmentId(String searchDepartmentId) {
		this.searchDepartmentId = searchDepartmentId;
	}

	public String getSearchDepartmentSecondId() {
		return searchDepartmentSecondId;
	}

	public void setSearchDepartmentSecondId(String searchDepartmentSecondId) {
		this.searchDepartmentSecondId = searchDepartmentSecondId;
	}

	public String getSearchHospitalType() {
		return searchHospitalType;
	}

	public void setSearchHospitalType(String searchHospitalType) {
		this.searchHospitalType = searchHospitalType;
	}

	public String getSearchDoctorTitle() {
		return searchDoctorTitle;
	}

	public void setSearchDoctorTitle(String searchDoctorTitle) {
		this.searchDoctorTitle = searchDoctorTitle;
	}
}
