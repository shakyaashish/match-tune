package com.studio.matchtune.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class DBManager {

    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String tag) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.TAGS, tag);
        database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {DBHelper.TAGS };
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TAGS, desc);
        int i = database.update(DBHelper.TABLE_NAME, contentValues, DBHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(String tagName) {
        database.delete(DBHelper.TABLE_NAME, DBHelper.TAGS + "=?", new String[]{tagName});
       //  database.close();
       // database.delete(DBHelper.TABLE_NAME, DBHelper.TAGS + "=" + tagName, null);
    }

    public void deleteUserTag() {
        database.delete(DBHelper.TABLE_NAME,null,null );
        //  database.close();
        // database.delete(DBHelper.TABLE_NAME, DBHelper.TAGS + "=" + tagName, null);
    }
}