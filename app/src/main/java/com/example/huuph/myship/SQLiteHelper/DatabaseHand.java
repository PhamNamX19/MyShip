package com.example.huuph.myship.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHand extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "saveID";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tableID";
    private static final String KEY_IDs = "ids";
    private static final String KEY_IDPost = "idPost";

    public DatabaseHand(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String querry = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + KEY_IDs + "integer primary key," + KEY_IDPost + "TEXT)";
        sqLiteDatabase.execSQL(querry);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void saveIDPost(String IDPost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IDPost, IDPost);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<String> getAllIDPost() {
        List<String> IDPosts = new ArrayList<>();
        String QUERRY = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERRY, null);

        while(cursor.isAfterLast() == false) {

            String IDPost = cursor.getString(1);
            IDPosts.add(IDPost);

            Log.d("TAGids",IDPosts.toString());
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return IDPosts;
    }
}
