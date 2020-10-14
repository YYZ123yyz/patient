package www.patient.jykj_zxyl.base.base_db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:文集列表
 *
 * @author: qiuxinhai
 * @date: 2019-06-19 11:31
 */
@Entity(nameInDb = "article_entity")
public class ArticleEntity {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "user_id")
    private String userId;
    @Property(nameInDb = "ccId")
    private String ccId;
    @Property(nameInDb = "article_content_json")
    private String articleContentJson;
    @Property(nameInDb = "article_content_test")
    private String articleContentTest;
    @Generated(hash = 296392776)
    public ArticleEntity(Long id, String userId, String ccId,
            String articleContentJson, String articleContentTest) {
        this.id = id;
        this.userId = userId;
        this.ccId = ccId;
        this.articleContentJson = articleContentJson;
        this.articleContentTest = articleContentTest;
    }
    @Generated(hash = 1301498493)
    public ArticleEntity() {
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
    public String getCcId() {
        return this.ccId;
    }
    public void setCcId(String ccId) {
        this.ccId = ccId;
    }
    public String getArticleContentJson() {
        return this.articleContentJson;
    }
    public void setArticleContentJson(String articleContentJson) {
        this.articleContentJson = articleContentJson;
    }
    public String getArticleContentTest() {
        return this.articleContentTest;
    }
    public void setArticleContentTest(String articleContentTest) {
        this.articleContentTest = articleContentTest;
    }


   
  


}
