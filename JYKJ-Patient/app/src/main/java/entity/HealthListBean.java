package entity;

/**
 * Created by G on 2020/9/17 14:09
 */
public class HealthListBean {

    private String type;
    private String unit;
    private String data;
    private String time;
    private int ivSrc;

    public int getIvSrc() {
        return ivSrc;
    }

    public void setIvSrc(int ivSrc) {
        this.ivSrc = ivSrc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
