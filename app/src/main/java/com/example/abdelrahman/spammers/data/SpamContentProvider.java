package com.example.abdelrahman.spammers.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.abdelrahman.spammers.MainActivity;

/**
 * Created by abdelrahman on 06/12/16.
 */
public class SpamContentProvider extends ContentProvider {
   private  static  UriMatcher sUriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher(){
        final String authority=Contract.CONTENT_AUTHORITY;
        sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
       sUriMatcher.addURI(authority,Contract.TABLE_NAME,1);
//       sUriMatcher.addURI(authority,Contract.TABLE_NAME+"/#",2);
       sUriMatcher.addURI(authority,Contract.TABLE_NAME+"/*",2);
return  sUriMatcher;
   }
//private     SQLiteDatabase db;
    private   DB dbHelper;
private static final SQLiteQueryBuilder spamSQLQueryBuilder;
    static {
        spamSQLQueryBuilder=new SQLiteQueryBuilder();
        spamSQLQueryBuilder.setTables(Contract.TABLE_NAME);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)){
            case 1:db.delete(Contract.TABLE_NAME,null,null);

        }
        return 0;
    }

    private static final String sSpamUserNumberSelection=Contract.COLUMN_USER_NUMBER+" = ";

    @Override
    public boolean onCreate() {
        Context context = getContext();
         dbHelper = new DB(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

       // SQLiteDatabase db = dbHelper.getWritableDatabase();
        return true;
    }
    public final String LOG_TAG=MainActivity.class.getSimpleName();

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
       // return super.query(uri, projection, selection, selectionArgs, sortOrder);
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        qb.setTables(Contract.TABLE_NAME);
//
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor retCur;
        switch (sUriMatcher.match(uri)) {
            case 1:
                retCur=db.query(Contract.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
              break;
            case 2:
                selection=sSpamUserNumberSelection;
                //uri.getPathLeafId(); momken hena getSpamUri() bas uri.getLAstPathSegment
                Log.v(LOG_TAG,uri.getLastPathSegment());
              retCur= spamSQLQueryBuilder.query(db,projection,selection,new String[]{uri.getLastPathSegment()},null,null,null);
               // retCur
                break;
default:throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }
        retCur.setNotificationUri(getContext().getContentResolver(),uri);
      //  db.close();
        return  retCur;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db=dbHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case 1:
                long id = db.insert(Contract.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri= Contract.insertSpamUri(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        //db.close();
        return returnUri;
        }


    }

