package www.patient.jykj_zxyl.base.base_db.service;


import java.util.List;

import www.patient.jykj_zxyl.base.base_db.dao.ArticleEntityDao;
import www.patient.jykj_zxyl.base.base_db.entity.ArticleEntity;


/**
 * Description:文章查询服务
 *
 * @author: qiuxinhai
 * @date: 2019-06-19 11:54
 */
public class ArticleEntityService extends BaseService<ArticleEntity,String> {

    ArticleEntityDao articleEntityDao;
    public ArticleEntityService(ArticleEntityDao dao) {
        super(dao);
        this.articleEntityDao=dao;
    }


    /**
     * 添加一个集合
     *
     * @param list 数据列表
     */
    public void addDataList(List<ArticleEntity> list) {
        articleEntityDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                int len = list.size();
                for (int i = 0; i < len; i++) {
                    String ccId = list.get(i).getCcId();
                    if (!isExistByField(ArticleEntityDao.TABLENAME,
                            ArticleEntityDao.Properties.CcId.columnName, ccId)) {
                        articleEntityDao.insertOrReplace(list.get(i));
                    }
                }
            }
        });
    }

    /**
     * 查询所有数据
     * @return List<ArticleEntity>
     */
    @Override
    public List<ArticleEntity> queryAll() {
        return super.queryAll();
    }

    /**
     * 分页加载
     *
     * @param userId    用户Id
     * @param pageIndex 启始位置
     * @param pageSize  每页大小
     * @return List<ArticleEntity>
     */
    public List<ArticleEntity> queryDataByPageNumber(String userId, int pageIndex, int pageSize) {
        return articleEntityDao.queryBuilder().where(ArticleEntityDao.Properties.UserId
                .eq(userId)).offset(pageIndex * pageSize).limit(pageSize).list();
    }

}
