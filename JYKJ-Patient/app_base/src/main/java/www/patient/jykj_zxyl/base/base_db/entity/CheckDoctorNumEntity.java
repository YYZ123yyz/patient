package www.patient.jykj_zxyl.base.base_db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by G on 2020/9/25 15:15
 */

@Entity(nameInDb = "check_doctor_num")
public class CheckDoctorNumEntity {

    @Id()
    private String  docId;
    @Property(nameInDb = "used_num")
    private int UsedNum;
    @Generated(hash = 1473019971)
    public CheckDoctorNumEntity(String docId, int UsedNum) {
        this.docId = docId;
        this.UsedNum = UsedNum;
    }
    @Generated(hash = 966456716)
    public CheckDoctorNumEntity() {
    }
    public String getDocId() {
        return this.docId;
    }
    public void setDocId(String docId) {
        this.docId = docId;
    }
    public int getUsedNum() {
        return this.UsedNum;
    }
    public void setUsedNum(int UsedNum) {
        this.UsedNum = UsedNum;
    }

}
