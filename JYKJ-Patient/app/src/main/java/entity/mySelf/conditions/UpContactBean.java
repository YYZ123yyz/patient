package entity.mySelf.conditions;

import java.io.Serializable;

public class UpContactBean implements Serializable {
    private String loginPatientPosition;
    private String requestClientType;
    private String operPatientCode;
    private String contactsId;
    private String contactsPhone1;
    private String contactsPhone2;

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

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getContactsPhone1() {
        return contactsPhone1;
    }

    public void setContactsPhone1(String contactsPhone1) {
        this.contactsPhone1 = contactsPhone1;
    }

    public String getContactsPhone2() {
        return contactsPhone2;
    }

    public void setContactsPhone2(String contactsPhone2) {
        this.contactsPhone2 = contactsPhone2;
    }
}
