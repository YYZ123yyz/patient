package entity.patientapp;


/**
 * 医生设置类-排班(患者选择)
 * 
 * @author JiaQ
 */

public class ProvideDoctorSetSchedulingPatient implements java.io.Serializable {
	private             int                 date;                           //时间（星期一到日 1~~7）
	private             int                 gradation;                       //阶段（1~3早中晚）
	private 			Integer 				sourceNum;			//[预留][周一][早]剩余号源数量
	private             boolean                 choice;               			//是否选择
	private				String				text;


	public Integer getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(Integer sourceNum) {
		this.sourceNum = sourceNum;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getGradation() {
		return gradation;
	}

	public void setGradation(int gradation) {
		this.gradation = gradation;
	}

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}
}
