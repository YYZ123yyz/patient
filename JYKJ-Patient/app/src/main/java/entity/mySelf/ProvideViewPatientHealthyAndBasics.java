package entity.mySelf;

import java.util.Date;
/**
 * 患者基本健康档案
 * 
 * @author JiaQ
 */
public class ProvideViewPatientHealthyAndBasics implements java.io.Serializable {

	private Integer healthyId;//基本信息编号
	private String patientCode;//用户(患者)基本信息编码
	private Integer characterType;//性格类型.(0:未知;1:内向;2:外向;)
	private Integer height;//身高(CM)
	private Integer waistline;//腰围
	private Integer weight;//体重
	private Integer flagSmoking;//是否吸烟.1:是;0:否;
	private Integer flagAlcoholism;//是否酗酒.1:是;0:否;
	private Integer flagStayUpLate;//是否熬夜.1:是;0:否;
	private Date bloodPressureAbnormalDate;//最早发现血压异常日期
	private Date confirmedYear;//确诊年份
	private Integer flagFamilyHtn;//是否有家族病史(直系亲属).1:是;0:否;[操作方式:单选按钮]
	private Integer flagHtnHistory;//是否有高血压病史.1:是;0:否;[操作方式:单选按钮]
	private String htnHistory;//高血压病史描述[操作方式:文本]
	
	
	
	private String onsetSymptoms;//起病症状[操作方式:多选.最多5项].数据字典编码:10001
	private String currentSymptoms;//目前症状[操作方式:多选.最多5项].数据字典编码:10002
	private String complication;//并发症[操作方式:多选.最多5项].数据字典编码:10003
	private String combinedDisease;//合并疾病[操作方式:多选.最多5项].数据字典编码:10004
	private String currentTreatmentPlan;//目前治疗方案[操作方式:多选.最多5项].数据字典编码:10005	
	private String stateOfIllness;//病情自述
	private String onsetSymptomsName;
	private String currentSymptomsName;
	private String complicationName;
	private String combinedDiseaseName;
	private String currentTreatmentPlanName;
	
	
	
	private String userName;
	private String userNameAlias;
	private Integer flagUserNameAliasStatus;
	private String idNumber;//身份证号
	private String nation;//民族
	private String nativePlace;//籍贯
	private Integer flagPatientStatus;

	public Integer getHealthyId() {
		return healthyId;
	}

	public void setHealthyId(Integer healthyId) {
		this.healthyId = healthyId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public Integer getCharacterType() {
		return characterType;
	}

	public void setCharacterType(Integer characterType) {
		this.characterType = characterType;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWaistline() {
		return waistline;
	}

	public void setWaistline(Integer waistline) {
		this.waistline = waistline;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getFlagSmoking() {
		return flagSmoking;
	}

	public void setFlagSmoking(Integer flagSmoking) {
		this.flagSmoking = flagSmoking;
	}

	public Integer getFlagAlcoholism() {
		return flagAlcoholism;
	}

	public void setFlagAlcoholism(Integer flagAlcoholism) {
		this.flagAlcoholism = flagAlcoholism;
	}

	public Integer getFlagStayUpLate() {
		return flagStayUpLate;
	}

	public void setFlagStayUpLate(Integer flagStayUpLate) {
		this.flagStayUpLate = flagStayUpLate;
	}

	public Date getBloodPressureAbnormalDate() {
		return bloodPressureAbnormalDate;
	}

	public void setBloodPressureAbnormalDate(Date bloodPressureAbnormalDate) {
		this.bloodPressureAbnormalDate = bloodPressureAbnormalDate;
	}

	public Date getConfirmedYear() {
		return confirmedYear;
	}

	public void setConfirmedYear(Date confirmedYear) {
		this.confirmedYear = confirmedYear;
	}

	public Integer getFlagFamilyHtn() {
		return flagFamilyHtn;
	}

	public void setFlagFamilyHtn(Integer flagFamilyHtn) {
		this.flagFamilyHtn = flagFamilyHtn;
	}

	public Integer getFlagHtnHistory() {
		return flagHtnHistory;
	}

	public void setFlagHtnHistory(Integer flagHtnHistory) {
		this.flagHtnHistory = flagHtnHistory;
	}

	public String getHtnHistory() {
		return htnHistory;
	}

	public void setHtnHistory(String htnHistory) {
		this.htnHistory = htnHistory;
	}

	public String getOnsetSymptoms() {
		return onsetSymptoms;
	}

	public void setOnsetSymptoms(String onsetSymptoms) {
		this.onsetSymptoms = onsetSymptoms;
	}

	public String getCurrentSymptoms() {
		return currentSymptoms;
	}

	public void setCurrentSymptoms(String currentSymptoms) {
		this.currentSymptoms = currentSymptoms;
	}

	public String getComplication() {
		return complication;
	}

	public void setComplication(String complication) {
		this.complication = complication;
	}

	public String getCombinedDisease() {
		return combinedDisease;
	}

	public void setCombinedDisease(String combinedDisease) {
		this.combinedDisease = combinedDisease;
	}

	public String getCurrentTreatmentPlan() {
		return currentTreatmentPlan;
	}

	public void setCurrentTreatmentPlan(String currentTreatmentPlan) {
		this.currentTreatmentPlan = currentTreatmentPlan;
	}

	public String getStateOfIllness() {
		return stateOfIllness;
	}

	public void setStateOfIllness(String stateOfIllness) {
		this.stateOfIllness = stateOfIllness;
	}

	public String getOnsetSymptomsName() {
		return onsetSymptomsName;
	}

	public void setOnsetSymptomsName(String onsetSymptomsName) {
		this.onsetSymptomsName = onsetSymptomsName;
	}

	public String getCurrentSymptomsName() {
		return currentSymptomsName;
	}

	public void setCurrentSymptomsName(String currentSymptomsName) {
		this.currentSymptomsName = currentSymptomsName;
	}

	public String getComplicationName() {
		return complicationName;
	}

	public void setComplicationName(String complicationName) {
		this.complicationName = complicationName;
	}

	public String getCombinedDiseaseName() {
		return combinedDiseaseName;
	}

	public void setCombinedDiseaseName(String combinedDiseaseName) {
		this.combinedDiseaseName = combinedDiseaseName;
	}

	public String getCurrentTreatmentPlanName() {
		return currentTreatmentPlanName;
	}

	public void setCurrentTreatmentPlanName(String currentTreatmentPlanName) {
		this.currentTreatmentPlanName = currentTreatmentPlanName;
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

	public Integer getFlagUserNameAliasStatus() {
		return flagUserNameAliasStatus;
	}

	public void setFlagUserNameAliasStatus(Integer flagUserNameAliasStatus) {
		this.flagUserNameAliasStatus = flagUserNameAliasStatus;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public Integer getFlagPatientStatus() {
		return flagPatientStatus;
	}

	public void setFlagPatientStatus(Integer flagPatientStatus) {
		this.flagPatientStatus = flagPatientStatus;
	}
}
