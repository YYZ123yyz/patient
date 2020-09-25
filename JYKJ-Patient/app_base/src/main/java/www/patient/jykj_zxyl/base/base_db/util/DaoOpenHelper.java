package www.patient.jykj_zxyl.base.base_db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import org.greenrobot.greendao.database.Database;

import www.patient.jykj_zxyl.base.base_db.dao.ArticleEntityDao;
import www.patient.jykj_zxyl.base.base_db.dao.CheckDoctorNumEntityDao;
import www.patient.jykj_zxyl.base.base_db.dao.CheckNumEntityDao;
import www.patient.jykj_zxyl.base.base_db.dao.DaoMaster;
import www.patient.jykj_zxyl.base.base_db.dao.PublishContentEntityDao;
import www.patient.jykj_zxyl.base.base_db.dao.UploadFileEntityDao;


/**
 * Description:数据库升级帮助类
 *
 * @author: qiuxinhai
 * @date: 2019-06-19 11:27
 */
public class DaoOpenHelper extends DaoMaster.OpenHelper {
    public DaoOpenHelper(Context context, String name) {
        super(context, name, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                    @Override
                    public void onCreateAllTables(Database db, boolean ifNotExists) {
                        DaoMaster.createAllTables(db, ifNotExists);
                    }
                    @Override
                    public void onDropAllTables(Database db, boolean ifExists) {
                        DaoMaster.dropAllTables(db, ifExists);
                    }
                },
                ArticleEntityDao.class,
                UploadFileEntityDao.class,
                PublishContentEntityDao.class,
                CheckNumEntityDao.class,
                CheckDoctorNumEntityDao.class
                );
    }

}
