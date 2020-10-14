package www.patient.jykj_zxyl.base.base_db.service;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

import www.patient.jykj_zxyl.base.base_db.dao.CheckDoctorNumEntityDao;
import www.patient.jykj_zxyl.base.base_db.entity.CheckDoctorNumEntity;

/**
 * Created by G on 2020/9/25 15:22
 */
public class CheckDocNumService extends BaseService<CheckDoctorNumEntity, String> {
    private CheckDoctorNumEntityDao checkNumEntityDao;

    public CheckDocNumService(AbstractDao dao) {
        super(dao);
        this.checkNumEntityDao= (CheckDoctorNumEntityDao) dao;
    }

    /**
     * 插入数据
     *
     * @param checkNumEntity 插入数据
     */
    public void inSeartData(CheckDoctorNumEntity checkNumEntity) {
        if (!isExistByField(CheckDoctorNumEntityDao.TABLENAME, CheckDoctorNumEntityDao.Properties.DocId.columnName,checkNumEntity.getDocId())){
            checkNumEntityDao.insertOrReplace(checkNumEntity);
        }else {
            List<CheckDoctorNumEntity> checkNumEntities = queryForUserId(checkNumEntity.getDocId());
            checkNumEntity.setDocId(checkNumEntities.get(0).getDocId());
            checkNumEntityDao.update(checkNumEntity);
        }

    }


    /**
     * 查询所有数据
     *
     * @return List<ArticleEntity>
     */
    @Override
    public List<CheckDoctorNumEntity> queryAll() {
        return super.queryAll();
    }

    /**
     * 根据用户Id查询数据
     * @param userId 用户Id
     * @return List
     */
    public List<CheckDoctorNumEntity> queryForUserId(String  userId){
        return checkNumEntityDao.queryBuilder()
                .where(CheckDoctorNumEntityDao.Properties.DocId.eq(userId)).list();
    }

    /**
     * 删除多个
     *
     * @param items 多个发布实体
     */
    @Override
    public void delete(CheckDoctorNumEntity... items) {
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
