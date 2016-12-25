package com.example.abdelrahman.spammers.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentUris;

/**
 * Created by abdelrahman on 30/11/16.
 */
public class Contract implements BaseColumns {


        public static final String TABLE_NAME = "Spam";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_NUMBER = "user_number";
        public static final String COLUMN_SPAM_NUMBER = "spam_number";
        public static final String CONTENT_AUTHORITY="com.example.abdelrahman.spammers.data";
        private static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath("Spam").build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + "Spam";
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + "Spam";

        public static Uri insertSpamUri(long id) {
                return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri getSpamUri(String user) {
         //       return ContentUris.withAppendedId(CONTENT_URI, id);
                return CONTENT_URI.buildUpon().appendPath(user).build();
        }








}
