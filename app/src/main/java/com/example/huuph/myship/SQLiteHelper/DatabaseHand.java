package com.example.huuph.myship.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHand {
    private final String DB_NAME = "saveID.db";
    private final String TB_NAME = "tableID";
    private final int DB_VERSION = 1;
    private SQLiteDatabase database;

    public class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String quer = "CREATE TABLE IF NOT EXISTS " +
                    "tableID(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " idPost TEXT)";
            db.execSQL(quer);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            String quer = "DROP TABLE IF EXISTS idPOST";
            db.execSQL(quer);
            onCreate(db);
        }

    }

    //insert
    public void insertIdPost(String idPost) {
        ContentValues values = new ContentValues();
        values.put("idPost", idPost);
        database.insertOrThrow(TB_NAME, null, values);
    }

    //xoa tat ca
    public void XoaAll() {
        database.delete(TB_NAME,null, null);
    }

    public Cursor selectidPost() {
        return database.query(TB_NAME, null, null, null, null, null, null);
    }

    public DatabaseHand(Context context) {
        OpenHelper helper = new OpenHelper(context);
        database = helper.getWritableDatabase();
    }
}
