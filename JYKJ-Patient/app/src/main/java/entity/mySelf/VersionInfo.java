package entity.mySelf;

import java.io.Serializable;
import java.util.Date;

public class VersionInfo implements Serializable {
    private Integer versionUpdId;
    private Integer userUseType;//使用方类型.5:医生;6:患者;
    private Integer flagVersionState;//版本状态.0:历史版本;1:在用最新版本;
    private String versionNum;//版本号.Eg.0.0.20.返回值时,添加前缀:Version
    private String versionIdentifying;//版本号识别标识
    private String thisTimeUpdDesc;//本次更新说明
    private String latelyUpdDesc;//最近更新说明
    private String updateDateShow;//更新日期


    public String getUpdateDateShow() {
        return updateDateShow;
    }

    public void setUpdateDateShow(String updateDateShow) {
        this.updateDateShow = updateDateShow;
    }

    public Integer getVersionUpdId() {
        return versionUpdId;
    }

    public void setVersionUpdId(Integer versionUpdId) {
        this.versionUpdId = versionUpdId;
    }

    public Integer getUserUseType() {
        return userUseType;
    }

    public void setUserUseType(Integer userUseType) {
        this.userUseType = userUseType;
    }

    public Integer getFlagVersionState() {
        return flagVersionState;
    }

    public void setFlagVersionState(Integer flagVersionState) {
        this.flagVersionState = flagVersionState;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getVersionIdentifying() {
        return versionIdentifying;
    }

    public void setVersionIdentifying(String versionIdentifying) {
        this.versionIdentifying = versionIdentifying;
    }

    public String getThisTimeUpdDesc() {
        return thisTimeUpdDesc;
    }

    public void setThisTimeUpdDesc(String thisTimeUpdDesc) {
        this.thisTimeUpdDesc = thisTimeUpdDesc;
    }

    public String getLatelyUpdDesc() {
        return latelyUpdDesc;
    }

    public void setLatelyUpdDesc(String latelyUpdDesc) {
        this.latelyUpdDesc = latelyUpdDesc;
    }
}
