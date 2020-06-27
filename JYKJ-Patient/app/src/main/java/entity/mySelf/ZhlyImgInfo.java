package entity.mySelf;

import java.io.Serializable;

public class ZhlyImgInfo implements Serializable {
    private Integer basicsImgId;
    private String imgName;
    private Integer imgTypeSecond;
    private String imgTypeSecondName;
    private String imgUrl;

    public Integer getBasicsImgId() {
        return basicsImgId;
    }

    public void setBasicsImgId(Integer basicsImgId) {
        this.basicsImgId = basicsImgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Integer getImgTypeSecond() {
        return imgTypeSecond;
    }

    public void setImgTypeSecond(Integer imgTypeSecond) {
        this.imgTypeSecond = imgTypeSecond;
    }

    public String getImgTypeSecondName() {
        return imgTypeSecondName;
    }

    public void setImgTypeSecondName(String imgTypeSecondName) {
        this.imgTypeSecondName = imgTypeSecondName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
