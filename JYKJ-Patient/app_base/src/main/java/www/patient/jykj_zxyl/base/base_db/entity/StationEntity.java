package www.patient.jykj_zxyl.base.base_db.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb="station")
public class StationEntity {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "is_choosed")
    private boolean isChoosed;
    @Property(nameInDb = "short_id")
    private String shortId;
    @Property(nameInDb = "corpus_id")
    private long corpusId;
    @Property(nameInDb = "corpus_name")
    private String corpusName;
    @Property(nameInDb = "head_img")
    private String icon;
    @Property(nameInDb = "summary")
    private String summary;
    @Property(nameInDb = "status")
    private int status;
    @Property(nameInDb = "status_desc")
    private String statusDesc;
    @Property(nameInDb = "sort_time_long")
    private long sortTimeLong;
    @Property(nameInDb = "master_accId")
    private long masterAccId;
    @Generated(hash = 52321283)
    public StationEntity(Long id, boolean isChoosed, String shortId, long corpusId,
            String corpusName, String icon, String summary, int status,
            String statusDesc, long sortTimeLong, long masterAccId) {
        this.id = id;
        this.isChoosed = isChoosed;
        this.shortId = shortId;
        this.corpusId = corpusId;
        this.corpusName = corpusName;
        this.icon = icon;
        this.summary = summary;
        this.status = status;
        this.statusDesc = statusDesc;
        this.sortTimeLong = sortTimeLong;
        this.masterAccId = masterAccId;
    }
    @Generated(hash = 1480181119)
    public StationEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getIsChoosed() {
        return this.isChoosed;
    }
    public void setIsChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }
    public String getShortId() {
        return this.shortId;
    }
    public void setShortId(String shortId) {
        this.shortId = shortId;
    }
    public long getCorpusId() {
        return this.corpusId;
    }
    public void setCorpusId(long corpusId) {
        this.corpusId = corpusId;
    }
    public String getCorpusName() {
        return this.corpusName;
    }
    public void setCorpusName(String corpusName) {
        this.corpusName = corpusName;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getSummary() {
        return this.summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getStatusDesc() {
        return this.statusDesc;
    }
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
    public long getSortTimeLong() {
        return this.sortTimeLong;
    }
    public void setSortTimeLong(long sortTimeLong) {
        this.sortTimeLong = sortTimeLong;
    }
    public long getMasterAccId() {
        return this.masterAccId;
    }
    public void setMasterAccId(long masterAccId) {
        this.masterAccId = masterAccId;
    }
}
