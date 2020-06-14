package entity.mySelf;

import java.io.Serializable;

public class PatientRecordImg implements Serializable {
    private String basicsImgId;
    private String imgUrl;

    public String getBasicsImgId() {
        return basicsImgId;
    }

    public void setBasicsImgId(String basicsImgId) {
        this.basicsImgId = basicsImgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
