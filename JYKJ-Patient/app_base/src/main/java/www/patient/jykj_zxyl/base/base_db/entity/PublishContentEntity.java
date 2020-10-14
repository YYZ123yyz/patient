package www.patient.jykj_zxyl.base.base_db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:发布内容实体
 *
 * @author: qiuxinhai
 * @date: 2020-04-20 10:58
 */
@Entity(nameInDb = "publish_content_entity")
public class PublishContentEntity {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "user_id")
    private String userId;
    @Property(nameInDb = "content_json")
    private String contentJson;
    @Generated(hash = 1570008557)
    public PublishContentEntity(Long id, String userId, String contentJson) {
        this.id = id;
        this.userId = userId;
        this.contentJson = contentJson;
    }
    @Generated(hash = 854411374)
    public PublishContentEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getContentJson() {
        return this.contentJson;
    }
    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

}
