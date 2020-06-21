package entity.mySelf;

import java.io.Serializable;
import java.util.Date;

public class VersionInfo implements Serializable {
    private Date createDate;
    private String  latelyUpdDesc;
    private String thisTimeUpdDesc;
    private String updateDateShow;
    private String versionIdentifying;
    private String versionNum;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLatelyUpdDesc() {
        return latelyUpdDesc;
    }

    public void setLatelyUpdDesc(String latelyUpdDesc) {
        this.latelyUpdDesc = latelyUpdDesc;
    }

    public String getThisTimeUpdDesc() {
        return thisTimeUpdDesc;
    }

    public void setThisTimeUpdDesc(String thisTimeUpdDesc) {
        this.thisTimeUpdDesc = thisTimeUpdDesc;
    }

    public String getUpdateDateShow() {
        return updateDateShow;
    }

    public void setUpdateDateShow(String updateDateShow) {
        this.updateDateShow = updateDateShow;
    }

    public String getVersionIdentifying() {
        return versionIdentifying;
    }

    public void setVersionIdentifying(String versionIdentifying) {
        this.versionIdentifying = versionIdentifying;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }
}
