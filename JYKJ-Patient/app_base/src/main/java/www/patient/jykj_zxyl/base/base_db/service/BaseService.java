package www.patient.jykj_zxyl.base.base_db.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * @author qiuxinhai
 * Date:2015-09-01
 * Time: 11:12
 */
public abstract class BaseService<T, K> {
    private AbstractDao<T, K> mDao;


    public BaseService(AbstractDao dao) {
        mDao = dao;
    }


    public void save(T item) {
        mDao.insert(item);
    }

    public void save(T... items) {
        mDao.insertInTx(items);
    }

    public void save(List<T> items) {
        mDao.insertInTx(items);
    }

    public void saveOrUpdate(T item) {
        mDao.insertOrReplace(item);
    }

    public void saveOrUpdate(T... items) {
        mDao.insertOrReplaceInTx(items);
    }

    public void saveOrUpdate(List<T> items) {
        mDao.insertOrReplaceInTx(items);
    }

    public void deleteByKey(K key) {
        mDao.deleteByKey(key);
    }

    public void delete(T item) {
        mDao.delete(item);
    }

    public void delete(T... items) {
        mDao.deleteInTx(items);
    }

    public void delete(List<T> items) {
        mDao.deleteInTx(items);
    }

    public void deleteAll() {
        mDao.deleteAll();
    }


    public void update(T item) {
        mDao.update(item);
    }

    public void update(T... items) {
        mDao.updateInTx(items);
    }

    public void update(List<T> items) {
        mDao.updateInTx(items);
    }

    public T query(K key) {
        return mDao.load(key);
    }

    public List<T> queryAll() {
        return mDao.loadAll();
    }

    public List<T> query(String where, String... params) {

        return mDao.queryRaw(where, params);
    }

    public QueryBuilder<T> queryBuilder() {

        return mDao.queryBuilder();
    }

    public long count() {
        return mDao.count();
    }

    public void refresh(T item) {
        mDao.refresh(item);

    }

    public void detach(T item) {
        mDao.detach(item);
    }

    public void update(String tname, String primaryKey, String primaryKeyValue,
                       ContentValues values) {
        SQLiteDatabase db = (SQLiteDatabase) mDao.getDatabase().getRawDatabase();
        if (db.isOpen()) {
            db.update(tname, values, primaryKey + "=? ",
                    new String[]{primaryKeyValue});
        }
    }

    /**
     * 根据某字段和值查看某条数据是否存在
     *
     * @param table
     * @param field
     * @param value
     * @return
     */
    protected boolean isExistByField(String table, String field, String value) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ").append(table).append(" WHERE ")
                .append(field).append("=?");
        return isExistBySQL(sql.toString(), new String[]{value});
    }

    /**
     * 使用SQL语句查看某条数据是否存在
     *
     * @param sql
     * @param selectionArgs
     * @return
     */
    public boolean isExistBySQL(String sql, String[] selectionArgs) {
        boolean result = false;
        Database db = null;
        /// if(ProjectApplication.is_out_database){
        // db=DbUtil.imporDatabase(mContext);
        // }else{
        db = mDao.getDatabase();
        /// }
        Cursor c = db.rawQuery(sql, selectionArgs);
        try {
            if (c.moveToNext()) {
                int count = c.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } finally {
            c.close();
        }
        return result;
    }

    /**
     * 获取表的主键
     *
     * @param tableName        表名
     * @param parameColumnName 条件字段
     * @param parameValue       条件值
     * @return
     */
    protected long selectPrimarykeyId(String tableName, String parameColumnName, String parameValue) {
        Database db = mDao.getDatabase();
        Cursor c = db.rawQuery("select * from "
                + tableName + " where " + parameColumnName + "=? ", new String[]{parameValue});
        long id = 0;
        try {
            if (c.moveToNext()) {
                id = c.getLong(c.getColumnIndex("_id"));
            }
        } finally {
            c.close();

        }
        return id;
    }

    /**
     * 查询表中有一共有多少条数据
     *
     * @return
     */
    public int selectFormCount(String formName, String customerId) {
        Database db = mDao.getDatabase();
        Cursor c = null;
        int count = 0;
        try {
            c = db.rawQuery("SELECT COUNT(*) FROM " + formName + " Where customer_id=? ", new String[]{customerId});
            if (c.moveToNext()) {
                count = c.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }
    /**
     * 查询莫个字段总和
     *
     * @return
     */
    public long selectFormSum(String formName,String columnName, String customerId,String subjectId) {
        Database db = mDao.getDatabase();
        Cursor c = null;
        long count = 0;
        try {
            if(null==subjectId){
                c = db.rawQuery("SELECT sum("+columnName+") FROM " + formName + " Where customer_id=? ", new String[]{customerId});
            }else{
                c = db.rawQuery("SELECT sum("+columnName+") FROM " + formName + " Where customer_id=? and video_parent_id=? ", new String[]{customerId,subjectId});
            }
            if (c.moveToNext()) {
                count = c.getLong(0);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }



}
