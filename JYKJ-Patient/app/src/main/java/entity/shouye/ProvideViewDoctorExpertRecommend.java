package entity.shouye;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 【专家推荐】功能中展示的医生信息
 * 
 * @author JiaQ
 */

public class ProvideViewDoctorExpertRecommend implements java.io.Serializable {
	private Integer doctorId;//医生编号
	private String doctorCode;//医生编码
	private String linkPhone;//手机号
	private String email;//邮箱
	private String userName;//姓名
	private String userNameAlias;//姓名别名
	private String userNameSpell;//姓名拼音助记码
	private String qrCode;//二维码
	private String userLogoUrl;//用户logo头像地址
	private Integer gender;//性别(0:未知;1:男;2:女;)
	private Date birthday;//生日
	private String country;//所在国家
	private String province;//所在省份
	private String provinceName;//所在省份名称
	private String city;//所在市
	private String cityName;//所在市名称
	private String area;//所在区(县)
	private String areaName;//所在区(县)名称
	private String address;//地址
	private String nation;//民族
	private String hospitalInfoCode;//医院编码
	private String hospitalInfoName;//医院名称
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
	
	private Integer collectNum;//收藏
	private Integer thumbsUpNum;//点赞
	private Integer allSumNum;//点赞
	private Integer flagImgText;//图文就诊开通标识(0:未开通;1:已开通;)
	private Long imgTextSumNum;//就诊量：图文
	private Integer flagAudio;//音频就诊开通标识(0:未开通;1:已开通;)
	private Long audioSumNum;//就诊量：音频
	private Integer flagVideo;//视频就诊开通标识(0:未开通;1:已开通;)
	private Long videoSumNum;//就诊量：视频
    private Long phoneSumNum;//就诊量：电话
    private Integer flagPhone;//电话就诊开通标识(0:未开通;1:已开通;)
	private Integer flagSigning;//签约就诊开通标识(0:未开通;1:已开通;)
	private Long signingSumNum;//就诊量：签约
	private Integer praiseCommentNum;//好评数量
	private Integer middleCommentNum;//中评数量
	private Integer differenceCommentNum;//差评数量
	private BigDecimal comprehensiveScore;//综合平分
	private Date statisticDate;//数据统计日期[统计日期只针对综合评分]
	
	/********************************** 非对称属性 ************************************/
	private Float imgTextPrice;//图文就诊价格

	private Float audioPrice;//音频就诊价格

    private Float phonePrice;//电话就诊价格
    private String phonePriceShow;//【展示价格内容】电话就诊价格

	private Float videoPrice;//视频就诊价格


	private Float signingPrice;//签约就诊价格

	private String schedulingInfo;//排班信息

    private Integer flagBindingState;//医患绑定状态[0：未绑定(未绑定时，绑定按钮可用)；1：已绑定(已绑定时，无按钮，显示已绑定);]
	private Integer flagCollectState;//收藏状态[0：未收藏(收藏按钮可用)；1：已收藏(收藏按钮不可用);]
	private Integer flagThumbsUpState;//点赞状态[0：未点赞(点赞按钮可用)；1：已点赞(点赞按钮不可用);]


	/********************************** 非对称属性 ************************************/
	private String imgTextPriceShow;//【展示价格内容】图文就诊价格


	private String audioPriceShow;//【展示价格内容】音频就诊价格

	private String videoPriceShow;//【展示价格内容】视频就诊价格

	private String signingPriceShow;//【展示价格内容】签约就诊价格


	/********** 【我的医生】 **********/
	private Integer pcBindingId;//【关注医生】使用。患者关注收藏医生编号

	public Integer getAllSumNum() {
		return allSumNum;
	}

	public void setAllSumNum(Integer allSumNum) {
		this.allSumNum = allSumNum;
	}

	private String patientCode;

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public String getImgTextPriceShow() {
		return imgTextPriceShow;
	}

	public void setImgTextPriceShow(String imgTextPriceShow) {
		this.imgTextPriceShow = imgTextPriceShow;
	}

	public String getAudioPriceShow() {
		return audioPriceShow;
	}

	public void setAudioPriceShow(String audioPriceShow) {
		this.audioPriceShow = audioPriceShow;
	}

	public String getVideoPriceShow() {
		return videoPriceShow;
	}

	public void setVideoPriceShow(String videoPriceShow) {
		this.videoPriceShow = videoPriceShow;
	}

	public String getSigningPriceShow() {
		return signingPriceShow;
	}

	public void setSigningPriceShow(String signingPriceShow) {
		this.signingPriceShow = signingPriceShow;
	}

	private	 String operThumbsUpType;
	private	 String thumbsUpDoctorCode;

    private	 String operCollectType;

	private	 String collectDoctorCode;
	private	 String collectDoctorName;

    public String getOperCollectType() {
        return operCollectType;
    }

    public void setOperCollectType(String operCollectType) {
        this.operCollectType = operCollectType;
    }

    public Integer getPcBindingId() {
		return pcBindingId;
	}

	public void setPcBindingId(Integer pcBindingId) {
		this.pcBindingId = pcBindingId;
	}

	public String getCollectDoctorCode() {
		return collectDoctorCode;
	}

	public void setCollectDoctorCode(String collectDoctorCode) {
		this.collectDoctorCode = collectDoctorCode;
	}

	public String getCollectDoctorName() {
		return collectDoctorName;
	}

	public void setCollectDoctorName(String collectDoctorName) {
		this.collectDoctorName = collectDoctorName;
	}

	public String getOperThumbsUpType() {
		return operThumbsUpType;
	}

	public void setOperThumbsUpType(String operThumbsUpType) {
		this.operThumbsUpType = operThumbsUpType;
	}

	public String getThumbsUpDoctorCode() {
		return thumbsUpDoctorCode;
	}

	public void setThumbsUpDoctorCode(String thumbsUpDoctorCode) {
		this.thumbsUpDoctorCode = thumbsUpDoctorCode;
	}

	public Integer getFlagCollectState() {
		return flagCollectState;
	}

	public void setFlagCollectState(Integer flagCollectState) {
		this.flagCollectState = flagCollectState;
	}

	public Integer getFlagThumbsUpState() {
		return flagThumbsUpState;
	}

	public void setFlagThumbsUpState(Integer flagThumbsUpState) {
		this.flagThumbsUpState = flagThumbsUpState;
	}

	private	String loginPatientPosition;
	private	String requestClientType;
	private	String operPatientCode;
	private	String operPatientName;
	private	String showNum;

	private String rowNum;
	private String pageNum;
	private String loginUserPosition;
	private String searchDoctorName;
	private String searchQrCode;
	private String searchDoctorTitle;
	private String searchProvince;
	private String searchCity;
	private String searchArea;
	private String searchHospitalInfoCode;
	private String searchDepartmentId;
	private String searchDepartmentSecondId;
	private String searchDoctorCode;

	private String searchHospitalType;
	private String searchPriceSectionLow;
	private String searchPriceSectionHigh;
	private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Long getPhoneSumNum() {
        return phoneSumNum;
    }

    public void setPhoneSumNum(Long phoneSumNum) {
        this.phoneSumNum = phoneSumNum;
    }

    public Integer getFlagPhone() {
        return flagPhone;
    }

    public void setFlagPhone(Integer flagPhone) {
        this.flagPhone = flagPhone;
    }

    public String getPhonePriceShow() {
        return phonePriceShow;
    }

    public void setPhonePriceShow(String phonePriceShow) {
        this.phonePriceShow = phonePriceShow;
    }

    public Integer getFlagBindingState() {
        return flagBindingState;
    }

    public void setFlagBindingState(Integer flagBindingState) {
        this.flagBindingState = flagBindingState;
    }

    public String getDoctorCode() {
		return doctorCode;
	}

	public String getSearchHospitalType() {
		return searchHospitalType;
	}

	public void setSearchHospitalType(String searchHospitalType) {
		this.searchHospitalType = searchHospitalType;
	}

	public String getSearchPriceSectionLow() {
		return searchPriceSectionLow;
	}

	public void setSearchPriceSectionLow(String searchPriceSectionLow) {
		this.searchPriceSectionLow = searchPriceSectionLow;
	}

	public String getSearchPriceSectionHigh() {
		return searchPriceSectionHigh;
	}

	public void setSearchPriceSectionHigh(String searchPriceSectionHigh) {
		this.searchPriceSectionHigh = searchPriceSectionHigh;
	}

	public String getSearchDoctorCode() {
		return searchDoctorCode;
	}

	public void setSearchDoctorCode(String searchDoctorCode) {
		this.searchDoctorCode = searchDoctorCode;
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

	public String getSearchDoctorName() {
		return searchDoctorName;
	}

	public void setSearchDoctorName(String searchDoctorName) {
		this.searchDoctorName = searchDoctorName;
	}

	public String getSearchQrCode() {
		return searchQrCode;
	}

	public void setSearchQrCode(String searchQrCode) {
		this.searchQrCode = searchQrCode;
	}

	public String getSearchDoctorTitle() {
		return searchDoctorTitle;
	}

	public void setSearchDoctorTitle(String searchDoctorTitle) {
		this.searchDoctorTitle = searchDoctorTitle;
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

	public String getSearchHospitalInfoCode() {
		return searchHospitalInfoCode;
	}

	public void setSearchHospitalInfoCode(String searchHospitalInfoCode) {
		this.searchHospitalInfoCode = searchHospitalInfoCode;
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

	public Float getPhonePrice() {
		return phonePrice;
	}

	public void setPhonePrice(Float phonePrice) {
		this.phonePrice = phonePrice;
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

	public String getShowNum() {
		return showNum;
	}

	public void setShowNum(String showNum) {
		this.showNum = showNum;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public Integer getThumbsUpNum() {
		return thumbsUpNum;
	}

	public void setThumbsUpNum(Integer thumbsUpNum) {
		this.thumbsUpNum = thumbsUpNum;
	}

	public Integer getFlagImgText() {
		return flagImgText;
	}

	public void setFlagImgText(Integer flagImgText) {
		this.flagImgText = flagImgText;
	}

	public Long getImgTextSumNum() {
		return imgTextSumNum;
	}

	public void setImgTextSumNum(Long imgTextSumNum) {
		this.imgTextSumNum = imgTextSumNum;
	}

	public Integer getFlagAudio() {
		return flagAudio;
	}

	public void setFlagAudio(Integer flagAudio) {
		this.flagAudio = flagAudio;
	}

	public Long getAudioSumNum() {
		return audioSumNum;
	}

	public void setAudioSumNum(Long audioSumNum) {
		this.audioSumNum = audioSumNum;
	}

	public Integer getFlagVideo() {
		return flagVideo;
	}

	public void setFlagVideo(Integer flagVideo) {
		this.flagVideo = flagVideo;
	}

	public Long getVideoSumNum() {
		return videoSumNum;
	}

	public void setVideoSumNum(Long videoSumNum) {
		this.videoSumNum = videoSumNum;
	}

	public Integer getFlagSigning() {
		return flagSigning;
	}

	public void setFlagSigning(Integer flagSigning) {
		this.flagSigning = flagSigning;
	}

	public Long getSigningSumNum() {
		return signingSumNum;
	}

	public void setSigningSumNum(Long signingSumNum) {
		this.signingSumNum = signingSumNum;
	}

	public Integer getPraiseCommentNum() {
		return praiseCommentNum;
	}

	public void setPraiseCommentNum(Integer praiseCommentNum) {
		this.praiseCommentNum = praiseCommentNum;
	}

	public Integer getMiddleCommentNum() {
		return middleCommentNum;
	}

	public void setMiddleCommentNum(Integer middleCommentNum) {
		this.middleCommentNum = middleCommentNum;
	}

	public Integer getDifferenceCommentNum() {
		return differenceCommentNum;
	}

	public void setDifferenceCommentNum(Integer differenceCommentNum) {
		this.differenceCommentNum = differenceCommentNum;
	}

	public BigDecimal getComprehensiveScore() {
		return comprehensiveScore;
	}

	public void setComprehensiveScore(BigDecimal comprehensiveScore) {
		this.comprehensiveScore = comprehensiveScore;
	}

	public Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}

	public Float getImgTextPrice() {
		return imgTextPrice;
	}

	public void setImgTextPrice(Float imgTextPrice) {
		this.imgTextPrice = imgTextPrice;
	}

    public Float getAudioPrice() {
        return audioPrice;
    }

    public void setAudioPrice(Float audioPrice) {
        this.audioPrice = audioPrice;
    }

    public Float getVideoPrice() {
        return videoPrice;
    }

    public void setVideoPrice(Float videoPrice) {
        this.videoPrice = videoPrice;
    }

    public Float getSigningPrice() {
        return signingPrice;
    }

    public void setSigningPrice(Float signingPrice) {
        this.signingPrice = signingPrice;
    }

    public String getSchedulingInfo() {
		return schedulingInfo;
	}

	public void setSchedulingInfo(String schedulingInfo) {
		this.schedulingInfo = schedulingInfo;
	}
}
