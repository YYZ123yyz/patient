package www.patient.jykj_zxyl.base.base_db;

import android.content.Context;


import org.greenrobot.greendao.query.QueryBuilder;

import www.patient.jykj_zxyl.base.base_db.dao.DaoMaster;
import www.patient.jykj_zxyl.base.base_db.dao.DaoSession;
import www.patient.jykj_zxyl.base.base_db.util.DaoOpenHelper;


/**
 * Description:数据库核心类
 *
 * @author: qiuxinhai
 * @date: 2019-06-19 11:27
 */
public class DbCore {
    public static final String DEFAULT_DB_NAME = "jykj_doctor.db";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static Context mContext;
    private static String DB_NAME;

    public static void init(Context context) {
        init(context, DEFAULT_DB_NAME);
    }

    public static void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context.getApplicationContext();
        DB_NAME = dbName;
    }

    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoOpenHelper daoOpenHelper = new DaoOpenHelper(mContext, DB_NAME);
            daoMaster = new DaoMaster(daoOpenHelper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static void enableQueryBuilderLog() {

        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }


}
