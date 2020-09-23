package www.patient.jykj_zxyl.base.base_db.service;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import www.patient.jykj_zxyl.base.base_db.dao.StationEntityDao;
import www.patient.jykj_zxyl.base.base_db.entity.StationEntity;

public class StationEntryService extends BaseService<StationEntity,String>{
    StationEntityDao stationEntityDao;
    public StationEntryService(StationEntityDao dao) {
        super(dao);
        this.stationEntityDao = dao;
    }
    /**
     * 添加一个集合
     * */
    public void addDataList(List<StationEntity> list) {
        stationEntityDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                int len = list.size();
                for (int i = 0; i < len; i++) {
                    String shortId = list.get(i).getShortId();
                    if (!isExistByField(StationEntityDao.TABLENAME,
                            StationEntityDao.Properties.ShortId.columnName, shortId)) {
                        stationEntityDao.insertOrReplace(list.get(i));
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
    public List<StationEntity> queryAll() {
        return super.queryAll();
    }
}
