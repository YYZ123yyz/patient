package entity;

import java.util.Date;


/**
 * 【医院】
 * 药品-信息
 * 前台医嘱使用
 * 
 * @author JiaQ
 */
public class ProvideDrugInfo implements java.io.Serializable {
	private Integer drugId;//药品信息编号
	private String drugCode;//药品信息编码
	private String drugName;//药品名称
	private String drugUnit;//药品单位
	private String drugSpec;//药品规格

	private String rowNum;
	private String pageNum;
	private String loginPatientPosition;
	private String requestClientType;
	private String operPatientCode;
	private String operPatientName;
	private String srarchDrugName;

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

	public String getSrarchDrugName() {
		return srarchDrugName;
	}

	public void setSrarchDrugName(String srarchDrugName) {
		this.srarchDrugName = srarchDrugName;
	}

	public Integer getDrugId() {
		return drugId;
	}

	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugUnit() {
		return drugUnit;
	}

	public void setDrugUnit(String drugUnit) {
		this.drugUnit = drugUnit;
	}

	public String getDrugSpec() {
		return drugSpec;
	}

	public void setDrugSpec(String drugSpec) {
		this.drugSpec = drugSpec;
	}
}
