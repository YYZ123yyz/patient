package entity;

import java.util.Date;


/**
 * 【基础数据】
 * 公共图片存储
 * 
 * @author JiaQ
 */
public class ProvideBasicsImg implements java.io.Serializable {
	private Integer basicsImgId;//图片信息编号
	private Integer imgType;//图片类型大类
	private String imgTypeName;//图片类型大类名称
	private Integer imgTypeSecond;//图片类型二级类
	private String imgTypeSecondName;//图片类型二级类名称
	private String imgName;//图片名称
	private String imgUrl;//图片路径
	private Integer sort;//排序

	private	String loginPatientPosition;
	private	String requestClientType;
	private	String operPatientCode;
	private	String operPatientName;
	private	String orderCode;
	private	String imgCode;

	public Integer getBasicsImgId() {
		return basicsImgId;
	}

	public void setBasicsImgId(Integer basicsImgId) {
		this.basicsImgId = basicsImgId;
	}

	public Integer getImgType() {
		return imgType;
	}

	public void setImgType(Integer imgType) {
		this.imgType = imgType;
	}

	public String getImgTypeName() {
		return imgTypeName;
	}

	public void setImgTypeName(String imgTypeName) {
		this.imgTypeName = imgTypeName;
	}

	public Integer getImgTypeSecond() {
		return imgTypeSecond;
	}

	public void setImgTypeSecond(Integer imgTypeSecond) {
		this.imgTypeSecond = imgTypeSecond;
	}

	public String getImgTypeSecondName() {
		return imgTypeSecondName;
	}

	public void setImgTypeSecondName(String imgTypeSecondName) {
		this.imgTypeSecondName = imgTypeSecondName;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
}
