package www.patient.jykj_zxyl.base.base_db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:上传文件实体
 *
 * @author: qiuxinhai
 * @date: 2019-09-16 10:43
 */
@Entity(nameInDb = "upload_file_entity")
public class UploadFileEntity {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "content_id")
    private long contentId;
    @Property(nameInDb = "quId")
    private long quId;
    @Property(nameInDb = "ansId")
    private long ansId;
    @Property(nameInDb = "medial_net_url")
    private String medialNetUrl;
    @Property(nameInDb = "medial_file_type")
    private String medialFileType;
    @Property(nameInDb = "medial_file_path")
    private String medialFilePath;
    @Property(nameInDb = "medial_file_pic")
    private String medialFilePic;
    @Property(nameInDb = "medial_fille_path_suffix")
    private String medialFilePathSuffix;
    @Property(nameInDb = "audio_json")
    private String audioJson;
    @Property(nameInDb = "upload_status")
    private int uploadStatus;
    @Property(nameInDb = "medial_file_compress_path")
    private String medialFileCompressPath;
    @Property(nameInDb = "resourse_type")
    private int resourseType;
    @Generated(hash = 822098830)
    public UploadFileEntity(Long id, long contentId, long quId, long ansId,
            String medialNetUrl, String medialFileType, String medialFilePath,
            String medialFilePic, String medialFilePathSuffix, String audioJson,
            int uploadStatus, String medialFileCompressPath, int resourseType) {
        this.id = id;
        this.contentId = contentId;
        this.quId = quId;
        this.ansId = ansId;
        this.medialNetUrl = medialNetUrl;
        this.medialFileType = medialFileType;
        this.medialFilePath = medialFilePath;
        this.medialFilePic = medialFilePic;
        this.medialFilePathSuffix = medialFilePathSuffix;
        this.audioJson = audioJson;
        this.uploadStatus = uploadStatus;
        this.medialFileCompressPath = medialFileCompressPath;
        this.resourseType = resourseType;
    }
    @Generated(hash = 1994279916)
    public UploadFileEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getContentId() {
        return this.contentId;
    }
    public void setContentId(long contentId) {
        this.contentId = contentId;
    }
    public long getQuId() {
        return this.quId;
    }
    public void setQuId(long quId) {
        this.quId = quId;
    }
    public long getAnsId() {
        return this.ansId;
    }
    public void setAnsId(long ansId) {
        this.ansId = ansId;
    }
    public String getMedialNetUrl() {
        return this.medialNetUrl;
    }
    public void setMedialNetUrl(String medialNetUrl) {
        this.medialNetUrl = medialNetUrl;
    }
    public String getMedialFileType() {
        return this.medialFileType;
    }
    public void setMedialFileType(String medialFileType) {
        this.medialFileType = medialFileType;
    }
    public String getMedialFilePath() {
        return this.medialFilePath;
    }
    public void setMedialFilePath(String medialFilePath) {
        this.medialFilePath = medialFilePath;
    }
    public String getAudioJson() {
        return this.audioJson;
    }
    public void setAudioJson(String audioJson) {
        this.audioJson = audioJson;
    }
    public int getUploadStatus() {
        return this.uploadStatus;
    }
    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }
    public String getMedialFilePathSuffix() {
        return this.medialFilePathSuffix;
    }
    public void setMedialFilePathSuffix(String medialFilePathSuffix) {
        this.medialFilePathSuffix = medialFilePathSuffix;
    }
    public String getMedialFilePic() {
        return this.medialFilePic;
    }
    public void setMedialFilePic(String medialFilePic) {
        this.medialFilePic = medialFilePic;
    }
    public String getMedialFileCompressPath() {
        return this.medialFileCompressPath;
    }
    public void setMedialFileCompressPath(String medialFileCompressPath) {
        this.medialFileCompressPath = medialFileCompressPath;
    }
    public int getResourseType() {
        return this.resourseType;
    }
    public void setResourseType(int resourseType) {
        this.resourseType = resourseType;
    }

}
