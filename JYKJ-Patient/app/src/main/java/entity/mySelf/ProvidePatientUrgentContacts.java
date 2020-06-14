package entity.mySelf;
import java.util.Date;

/**
 * 【基础类】
 * 患者紧急联系人
 *
 * @author JiaQ
 */
public class ProvidePatientUrgentContacts implements java.io.Serializable {

    private Integer contactsId;//紧急联系人编号
    private String patientCode;//用户(患者)基本信息编号.外键:sys_user_patient_info

    private String contactsName1;//[预留]紧急人-1-姓名
    private String contactsPhone1;//紧急-1-电话
    private String contactsRelation1;//[预留]紧急-1-关系

    private String contactsName2;//[预留]紧急人-2-姓名
    private String contactsPhone2;//紧急-2-电话
    private String contactsRelation2;//[预留]紧急-2-关系

    private String contactsName3;//[预留]紧急人-3-姓名
    private String contactsPhone3;//[预留]紧急-3-电话
    private String contactsRelation3;//[预留]紧急-3-关系

    public Integer getContactsId() {
        return contactsId;
    }

    public void setContactsId(Integer contactsId) {
        this.contactsId = contactsId;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getContactsName1() {
        return contactsName1;
    }

    public void setContactsName1(String contactsName1) {
        this.contactsName1 = contactsName1;
    }

    public String getContactsPhone1() {
        return contactsPhone1;
    }

    public void setContactsPhone1(String contactsPhone1) {
        this.contactsPhone1 = contactsPhone1;
    }

    public String getContactsRelation1() {
        return contactsRelation1;
    }

    public void setContactsRelation1(String contactsRelation1) {
        this.contactsRelation1 = contactsRelation1;
    }

    public String getContactsName2() {
        return contactsName2;
    }

    public void setContactsName2(String contactsName2) {
        this.contactsName2 = contactsName2;
    }

    public String getContactsPhone2() {
        return contactsPhone2;
    }

    public void setContactsPhone2(String contactsPhone2) {
        this.contactsPhone2 = contactsPhone2;
    }

    public String getContactsRelation2() {
        return contactsRelation2;
    }

    public void setContactsRelation2(String contactsRelation2) {
        this.contactsRelation2 = contactsRelation2;
    }

    public String getContactsName3() {
        return contactsName3;
    }

    public void setContactsName3(String contactsName3) {
        this.contactsName3 = contactsName3;
    }

    public String getContactsPhone3() {
        return contactsPhone3;
    }

    public void setContactsPhone3(String contactsPhone3) {
        this.contactsPhone3 = contactsPhone3;
    }

    public String getContactsRelation3() {
        return contactsRelation3;
    }

    public void setContactsRelation3(String contactsRelation3) {
        this.contactsRelation3 = contactsRelation3;
    }
}


