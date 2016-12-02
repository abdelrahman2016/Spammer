package com.example.abdelrahman.spammers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.abdelrahman.spammers.data.Contract;
import com.example.abdelrahman.spammers.data.DB;

public class HomePage extends Activity {
    Button block,myblockednumbers,allblockednumbers,signout;
    EditText ed1;
    final  String LOG_TAG=HomePage.class.getSimpleName();
        DB db;
            public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.article_view, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view);
                Log.v(LOG_TAG,"ONCreate HomePage Activity");

        db=new DB(this);
        final SQLiteDatabase dbSql=db.getWritableDatabase();
        block = (Button)findViewById(R.id.button5);
        ed1 = (EditText)findViewById(R.id.editText6);
        signout=(Button)findViewById(R.id.sign_out);
        myblockednumbers = (Button)findViewById(R.id.button2);
        allblockednumbers = (Button)findViewById(R.id.button3);
                final Activity act=this;
        block.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String spam=ed1.getText().toString();
                ContentValues contentValues=new ContentValues();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userNu=preferences.getString("userNumber",null);
                String userNa=preferences.getString("userName",null);

                contentValues.put(Contract.COLUMN_USER_NUMBER,userNu);
                contentValues.put(Contract.COLUMN_USER_NAME,userNa);
                contentValues.put(Contract.COLUMN_SPAM_NUMBER,spam);
                dbSql.insert(Contract.TABLE_NAME,null,contentValues);
            }
            });
        myblockednumbers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // setContentView(R.layout.myblockednumbers);
                Intent intent=new Intent(act,MyBlocked.class);
                startActivity(intent);
            }
        });
        allblockednumbers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //setContentView(R.layout.allblockednumbers);
                Intent intent=new Intent(act,AllBlockedNumbers.class);
                startActivity(intent);

            }
        });
              //  Activity act=this;
                signout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        preferences.edit().putString("userName","userName").commit();
                        preferences.edit().putString("userNumber","userNumber").commit();
                        Intent intent=new Intent(act,MainActivity.class);
                        startActivity(intent);
                    }
                });
    }


}
