package com.example.abdelrahman.spammers.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abdelrahman on 30/11/16.
 */

/**
 * Created by abdelrahman on 28/08/16.
 */
public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    static final String DB_NAME="spams.db";

    public DB(Context context){
        super(context,DB_NAME,null,DATABASE_VERSION);
    }
    @Override

    public void onCreate(SQLiteDatabase db) {

        String createMovieTable="CREATE TABLE "+Contract.TABLE_NAME+" ( "+Contract._ID+
                " INTEGER AUTO_INCREMENT PRIMARY KEY, " +Contract.COLUMN_USER_NAME+ " TEXT NOT NULL, "+
                Contract.COLUMN_USER_NUMBER+" TEXT NOT NULL, "+Contract.COLUMN_SPAM_NUMBER+
                " TEXT, "
                +" UNIQUE ( "+Contract.COLUMN_USER_NUMBER+" ) ON CONFLICT REPLACE );";
        db.execSQL(createMovieTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Contract.TABLE_NAME);
        onCreate(db);
    }
}



