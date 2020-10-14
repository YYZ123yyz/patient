package www.patient.jykj_zxyl.base.base_db.service;


import java.util.List;

import www.patient.jykj_zxyl.base.base_db.dao.UploadFileEntityDao;
import www.patient.jykj_zxyl.base.base_db.entity.UploadFileEntity;

/**
 * Description:上传文件查询服务
 *
 * @author: qiuxinhai
 * @date: 2019-09-16 10:53
 */
public class UploadFileService  extends BaseService<UploadFileEntity,String> {

    private UploadFileEntityDao uploadFileEntityDao;
    public UploadFileService(UploadFileEntityDao dao) {
        super(dao);
        this.uploadFileEntityDao=dao;
    }

    /**
     * 添加一个集合
     *
     * @param list 数据列表
     */
    public void inSeartDataList(List<UploadFileEntity> list) {
        uploadFileEntityDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                int len = list.size();
                for (int i = 0; i < len; i++) {
                    uploadFileEntityDao.insertOrReplace(list.get(i));
                }
            }
        });
    }

    /**
     * 插入数据
     * @param uploadFileEntity 上传实体
     */
    public void inSeartData(UploadFileEntity uploadFileEntity){
        uploadFileEntityDao.insert(uploadFileEntity);
    }

    /**
     * 查询所有数据
     * @return List<ArticleEntity>
     */
    @Override
    public List<UploadFileEntity> queryAll() {
        return super.queryAll();
    }


    @Override
    public void delete(UploadFileEntity... items) {
        super.delete(items);
    }
}

