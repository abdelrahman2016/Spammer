package com.example.abdelrahman.spammers;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.abdelrahman.spammers.data.Contract;
import com.example.abdelrahman.spammers.data.DB;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class AllBlockedNumbersFragment extends Fragment {
    DB db;
final String LOG_TAG=AllBlockedNumbersFragment.class.getSimpleName();
    public AllBlockedNumbersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG,"ONCreate allBlockedNumbersFragment");

        View rootvView= inflater.inflate(R.layout.fragment_my_blocked, container, false);
        ListView listView=(ListView) rootvView.findViewById(R.id.list_view);
        db=new DB(getContext());
        SQLiteDatabase dbSql= db.getReadableDatabase();

        Cursor c=dbSql.query(Contract.TABLE_NAME,null,null,null,null,null,null);
        ArrayList<String> arrayList=new ArrayList<String>();
        if(c.moveToFirst()){
            if((c.getString(c.getColumnIndex(Contract.COLUMN_SPAM_NUMBER)))!=null) {
                arrayList.add("User Name: " + c.getString(c.getColumnIndex(Contract.COLUMN_USER_NAME)) +
                        " User Number: " + c.getString(c.getColumnIndex(Contract.COLUMN_USER_NUMBER)) + " Spammer: " + c.getString(c.getColumnIndex(Contract.COLUMN_SPAM_NUMBER)));
            }
            while (c.moveToNext()){
                if((c.getString(c.getColumnIndex(Contract.COLUMN_SPAM_NUMBER)))!=null) {

                    arrayList.add("User Name: " + c.getString(c.getColumnIndex(Contract.COLUMN_USER_NAME)) +
                            " User Number: " + c.getString(c.getColumnIndex(Contract.COLUMN_USER_NUMBER)) + " Spammer: " + c.getString(c.getColumnIndex(Contract.COLUMN_SPAM_NUMBER)));

                }
            }
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_text_view_my_blocked_number,arrayList);
        listView.setAdapter(arrayAdapter);
        return rootvView;}
    }

