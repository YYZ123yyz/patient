package entity.mySelf;

import java.io.Serializable;

public class UserResultInfo implements Serializable {
    private Integer gender;
    private String newLoginDate;
    private String qrCode;
    private String userCode;
    private String userLogoUrl;
    private String userName;
    private String userUseType;

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNewLoginDate() {
        return newLoginDate;
    }

    public void setNewLoginDate(String newLoginDate) {
        this.newLoginDate = newLoginDate;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserLogoUrl() {
        return userLogoUrl;
    }

    public void setUserLogoUrl(String userLogoUrl) {
        this.userLogoUrl = userLogoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUseType() {
        return userUseType;
    }

    public void setUserUseType(String userUseType) {
        this.userUseType = userUseType;
    }
}
