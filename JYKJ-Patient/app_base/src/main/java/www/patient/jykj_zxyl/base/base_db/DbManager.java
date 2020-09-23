package www.patient.jykj_zxyl.base.base_db;


import www.patient.jykj_zxyl.base.base_db.service.ArticleEntityService;
import www.patient.jykj_zxyl.base.base_db.service.PublishContentService;
import www.patient.jykj_zxyl.base.base_db.service.StationEntryService;
import www.patient.jykj_zxyl.base.base_db.service.UploadFileService;

/**
 * Description:数据库管理类
 *
 * @author: qiuxinhai
 * @date: 2019-06-19 11:50
 */
public class DbManager {
    private static class InstanceHolder{
        private static final DbManager INSTANCE=new DbManager();
    }

    /**
     * 获取数据库管理对象
     *
     * @return DbManager
     */
    public static DbManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 文章存储service
     */
    private ArticleEntityService articleEntityService;

    /**
     * 上传文件service
     */
    private UploadFileService uploadFileService;

    /**
     * 发布内容存储服务
     */
    private PublishContentService publishContentService;
    /**
     * StationService
     * */
    private StationEntryService stationEntryService;
    /**
     * 获取文章操作service
     *
     * @return service
     */
    public ArticleEntityService getArticleEntityService() {
        if (articleEntityService == null) {
            articleEntityService = new ArticleEntityService(DbCore.getDaoSession()
                    .getArticleEntityDao());
        }
        return articleEntityService;
    }

    /**
     * 获取上传文件service
     * @return service
     */
    public UploadFileService getUploadFileService(){
        if (uploadFileService==null) {
            uploadFileService=new UploadFileService(DbCore.getDaoSession().getUploadFileEntityDao());
        }
        return uploadFileService;
    }

    /**
     * 获取发布内容存储服务
     * @return service
     */
    public PublishContentService getPublishContentService(){
        if(publishContentService==null){
            publishContentService=new PublishContentService(DbCore.getDaoSession().getPublishContentEntityDao());
        }
        return publishContentService;
    }
    /**
     * 获取发布内容存储服务
     * @return service
     */
    public StationEntryService getStationEntryService(){
        if(stationEntryService==null){
            stationEntryService=new StationEntryService(DbCore.getDaoSession().getStationEntityDao());
        }
        return stationEntryService;
    }
}
