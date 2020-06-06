package entity;


/**
 * 患者个人状况-服药记录【饼状图数据统计】
 * 
 * @author JiaQ
 */
public class ProvidePatientConditionTakingRecordStatistics implements java.io.Serializable {
	
	//服药标识.总数据量
	private int flagTakingMedicineSum;	
	//服药标识.0:未操作;
	private int flagTakingMedicine0;	
	//服药标识.1:未服用;
	private int flagTakingMedicine1;	
	//服药标识.2:操作过期;
	private int flagTakingMedicine2;	
	//服药标识.3:已服用;
	private int flagTakingMedicine3;

	private String loginPatientPosition;
	private String requestClientType;
	private String operPatientCode;
	private String operPatientName;
	private String searchTakingMedicine;
	private String searchDateType;
	private String searchDateStart;
	private String searchDateEnd;

	public int getFlagTakingMedicineSum() {
		return flagTakingMedicineSum;
	}

	public void setFlagTakingMedicineSum(int flagTakingMedicineSum) {
		this.flagTakingMedicineSum = flagTakingMedicineSum;
	}

	public int getFlagTakingMedicine0() {
		return flagTakingMedicine0;
	}

	public void setFlagTakingMedicine0(int flagTakingMedicine0) {
		this.flagTakingMedicine0 = flagTakingMedicine0;
	}

	public int getFlagTakingMedicine1() {
		return flagTakingMedicine1;
	}

	public void setFlagTakingMedicine1(int flagTakingMedicine1) {
		this.flagTakingMedicine1 = flagTakingMedicine1;
	}

	public int getFlagTakingMedicine2() {
		return flagTakingMedicine2;
	}

	public void setFlagTakingMedicine2(int flagTakingMedicine2) {
		this.flagTakingMedicine2 = flagTakingMedicine2;
	}

	public int getFlagTakingMedicine3() {
		return flagTakingMedicine3;
	}

	public void setFlagTakingMedicine3(int flagTakingMedicine3) {
		this.flagTakingMedicine3 = flagTakingMedicine3;
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

	public String getSearchTakingMedicine() {
		return searchTakingMedicine;
	}

	public void setSearchTakingMedicine(String searchTakingMedicine) {
		this.searchTakingMedicine = searchTakingMedicine;
	}

	public String getSearchDateType() {
		return searchDateType;
	}

	public void setSearchDateType(String searchDateType) {
		this.searchDateType = searchDateType;
	}

	public String getSearchDateStart() {
		return searchDateStart;
	}

	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}

	public String getSearchDateEnd() {
		return searchDateEnd;
	}

	public void setSearchDateEnd(String searchDateEnd) {
		this.searchDateEnd = searchDateEnd;
	}
}