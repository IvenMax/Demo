package com.iven.app.tools;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//http://blog.csdn.net/yissan/article/details/51494667
@SuppressLint("NewApi")
public class SqliteHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "LetterSys.db";
    private final static int DB_VERSION = 1;
    private SQLiteDatabase db;

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getReadableDatabase();//获取数据库操作对象，读写权限
    }

    /**
     * @param tableName 表名
     * @param cv        字段名和值的键值对
     * @return boolean true:成功，false:失败
     */
    public boolean insert(String tableName, ContentValues cv) {
        return db.insert(tableName, null, cv) != -1;
    }

    /**
     * @param tableName 表名
     * @param cv        字段名和值的键值对
     * @param where     条件(不带where)
     * @param whereArgs 条件字符串中占位符的值
     * @return boolean true:成功，false:失败
     */
    public boolean update(String tableName, ContentValues cv, String where, String[] whereArgs) {
        return db.update(tableName, cv, where, whereArgs) > 0;
    }

    /**
     * @param tableName 表名
     * @param where     条件(不带where)
     * @param whereArgs 条件字符串中占位符的值
     * @return boolean true:成功，false:失败
     */
    public boolean delete(String tableName, ContentValues cv, String where, String[] whereArgs) {
        int r = db.delete(tableName, where, whereArgs);
        Log.e("delete", "删除结果：" + r);
        return r > 0;
    }

    /**
     * 查询
     *
     * @param table         表名
     * @param columns       列名的字符串数组
     * @param selection     查询条件
     * @param selectionArgs 查询条件字符串的占位符的值
     * @param groupBy       分组
     * @param having        分组之后的数据筛选
     * @param orderBy       排序
     * @param limit         分页
     */
    public Cursor select(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    /**
     * 将Cursor数据转换为List
     *
     * @param cursor 待转换的光标对象
     */
    public List<Map<String, Object>> cursorToList(Cursor cursor) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (cursor.moveToNext()) {
            HashMap<String, Object> hm = new HashMap<String, Object>();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Object c_value = null;
                switch (cursor.getType(i)) {
                    case Cursor.FIELD_TYPE_BLOB://byte数组
                        c_value = cursor.getBlob(i);
                        break;
                    case Cursor.FIELD_TYPE_FLOAT://float
                        c_value = cursor.getFloat(i);
                        break;
                    case Cursor.FIELD_TYPE_INTEGER://int
                        c_value = cursor.getInt(i);
                        break;
                    case Cursor.FIELD_TYPE_NULL://null
                        c_value = null;
                        break;
                    case Cursor.FIELD_TYPE_STRING://String
                        c_value = cursor.getString(i);
                        break;
                }
                hm.put(cursor.getColumnName(i), c_value);
            }
            list.add(hm);
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;

    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table if not exists Word(_id integer primary key autoincrement,name varchar(50) not null,detail varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (newVersion > oldVersion)//只有新的版本号大于旧的版本，才进行更新
        {
            db.execSQL("drop table if exists Word");
            onCreate(db);//
        }
    }

}
