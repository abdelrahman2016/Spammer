package com.example.abdelrahman.spammers.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.abdelrahman.spammers.MainActivity;

/**
 * Created by abdelrahman on 06/12/16.
 */
public class Data {
    Context mContext;
    public Data(Context context){
        mContext=context;
    }
    public long addSpam(String userName, String userNumber) {
        long locationId;
        ContentValues locationValues = new ContentValues();

        // Then add the data, along with the corresponding name of the data type,
        // so the content provider knows what kind of value is being inserted.
        locationValues.put(Contract.COLUMN_USER_NAME, userName);
        locationValues.put(Contract.COLUMN_USER_NUMBER, userNumber);

        // Finally, insert location data into the database.
        Uri insertedUri = mContext.getContentResolver().insert(
                Contract.CONTENT_URI,
                locationValues);


        locationId = ContentUris.parseId(insertedUri);

        return locationId;
    }
    public void deleteAll(){
        mContext.getContentResolver().delete(Contract.CONTENT_URI,null,null);
    }
    public long addSpam(String userName, String userNumber,String spamNumber) {
        long locationId;
        ContentValues locationValues = new ContentValues();

        // Then add the data, along with the corresponding name of the data type,
        // so the content provider knows what kind of value is being inserted.
        locationValues.put(Contract.COLUMN_USER_NAME, userName);
        locationValues.put(Contract.COLUMN_USER_NUMBER, userNumber);
        locationValues.put(Contract.COLUMN_SPAM_NUMBER,spamNumber);
        // Finally, insert location data into the database.
        Uri insertedUri = mContext.getContentResolver().insert(
                Contract.CONTENT_URI,
                locationValues);


        locationId = ContentUris.parseId(insertedUri);
        //Log.v(LOG_TAG,"iddd addSPam"+locationId);
        return locationId;
    }
    private final static String LOG_TAG= MainActivity.class.getSimpleName().toString();
    public Cursor querySpam(String userNumber){
        Cursor cursor=mContext.getContentResolver().query(Contract.CONTENT_URI,null,Contract.COLUMN_USER_NUMBER+" = ? ",
                new String[]{userNumber},null);
        //mContext.getContentResolver().query(Contract.CONTENT_URI,)
        return cursor;
    }
    public Cursor querySpam(){
        Cursor cursor=mContext.getContentResolver().query(Contract.CONTENT_URI,null,null,null,null);
        return cursor;
    }

}
