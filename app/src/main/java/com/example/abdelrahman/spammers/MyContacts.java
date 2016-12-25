package com.example.abdelrahman.spammers;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.abdelrahman.spammers.data.Contract;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyContacts extends AppCompatActivity {
    final String LOG_TAG=MyContacts.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);
        ContentResolver resolver=getContentResolver();
        ArrayList<String> number=new ArrayList<String>();
        ArrayList<String> name=new ArrayList<String>();
        Cursor cursor=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
//        if(cursor.moveToFirst()){
//            name.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)));
//            //cursor.getString(cursor.getColumnIndex())
//           String contactID= cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//            Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
//
//                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
//                            ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
//                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
//
//                    new String[]{contactID},
//                    null);
//
//            if (cursorPhone.moveToFirst()) {
//               number.add(cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.
//                       Phone.NUMBER)));
//            }
//            cursorPhone.close();
//while(cursor.moveToNext()){
//    name.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)));
//
//    //cursor.getString(cursor.getColumnIndex())
//    String contactID1= cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//    Cursor cursorPhone1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
//
//            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
//                    ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
//                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
//
//            new String[]{contactID1},
//            null);
//
//    if (cursorPhone1.moveToFirst()) {
//        number.add(cursorPhone.getString(cursorPhone.
//                getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//    }
// cursorPhone1.close();
//}
//
//        }
//        cursor.close();
        String[]disp=new String[]{ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
        int[]text=new int[]{android.R.id.text1};
      ListView list=  (ListView)findViewById(android.R.id.list);
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(this,R.layout.contacts_list_item,
                cursor,disp ,
               text,0);
        list.setAdapter(simpleCursorAdapter);
        Log.v(LOG_TAG,name.toString()+number.toString());
        }



//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//    }
}
