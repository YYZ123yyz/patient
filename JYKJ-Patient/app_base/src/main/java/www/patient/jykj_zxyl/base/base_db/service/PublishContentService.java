package www.patient.jykj_zxyl.base.base_db.service;


import org.greenrobot.greendao.AbstractDao;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import www.patient.jykj_zxyl.base.base_db.dao.PublishContentEntityDao;
import www.patient.jykj_zxyl.base.base_db.entity.PublishContentEntity;
import www.patient.jykj_zxyl.base.base_utils.AndroidThreadExecutor;

/**
 * Description:发布内容存储service
 *
 * @author: qiuxinhai
 * @date: 2020-04-20 11:09
 */
public class PublishContentService extends BaseService<PublishContentEntity,String> {
    private PublishContentEntityDao publishContentEntityDao;
    public PublishContentService(AbstractDao dao) {
        super(dao);
        this.publishContentEntityDao= (PublishContentEntityDao) dao;
    }

    /**
     * 插入数据
     *
     * @param publishContentEntity 插入数据
     */
    public void inSeartData(PublishContentEntity publishContentEntity) {
        AndroidThreadExecutor.getInstance().executeOnWorkThread(new Runnable() {
            @Override
            public void run() {
                publishContentEntityDao.insert(publishContentEntity);
            }
        });

    }


    /**
     * 查询所有数据
     *
     * @return List<ArticleEntity>
     */
    @Override
    public List<PublishContentEntity> queryAll() {
        return super.queryAll();
    }

    /**
     * 根据用户Id查询数据
     * @param userId 用户Id
     * @return List
     */
    public List<PublishContentEntity> queryForUserId(long userId){
       return publishContentEntityDao.queryBuilder()
                .where(PublishContentEntityDao.Properties.UserId.eq(userId)).list();
    }

    /**
     * 删除多个
     *
     * @param items 多个发布实体
     */
    @Override
    public void delete(PublishContentEntity... items) {
        super.delete(items);
    }

    /**
     * 删除所有
     */
    @Override
    public void deleteAll() {
        super.deleteAll();
    }
}
