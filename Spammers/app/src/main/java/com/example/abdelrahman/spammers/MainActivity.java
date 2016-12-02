package com.example.abdelrahman.spammers;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelrahman.spammers.data.Contract;
import com.example.abdelrahman.spammers.data.DB;

public class MainActivity extends Activity  {
    private DB db;

    Button b1,b2;
    EditText ed1,ed2;

    TextView tx1;
    int counter = 3;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userN=preferences.getString("userName",null);
        String userNu=preferences.getString("userNumber",null);
        if(userN!=null){
        if(!userN.equals("userName")&&!userNu.equals("userNumber")){
            ed1 = (EditText)findViewById(R.id.editText);
            ed2 = (EditText)findViewById(R.id.editText2);
            ed1.setText(userN);
            ed2.setText(userNu);

        }}
    }
final String LOG_TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DB(this);
        Log.v(LOG_TAG,"ON Create MAinActivity");
        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b2 = (Button)findViewById(R.id.button2);
        tx1 = (TextView)findViewById(R.id.textView3);
            tx1.setVisibility(View.GONE);
        final SQLiteDatabase dbSql=db.getWritableDatabase();
        final Activity act=this;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userNumber=ed2.getText().toString();
                String [] selection=new String[]{userNumber};

                Cursor c=dbSql.query(Contract.TABLE_NAME,null,Contract.COLUMN_USER_NUMBER+" = ?",selection,null,null,null);
                    if(c.moveToFirst()==true){
                         String userName=c.getString(c.getColumnIndex(Contract.COLUMN_USER_NAME));
                        ed1.setText(userName);
                        ed2.setText(userNumber);
                    }
                else{
                       String userName= ed1.getText().toString();
                        ContentValues values=new ContentValues();
                        values.put(Contract.COLUMN_USER_NAME,userName);
                        values.put(Contract.COLUMN_USER_NUMBER,userNumber);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        preferences.edit().putString("userName",userName).commit();
                        preferences.edit().putString("userNumber",userNumber).commit();
                        dbSql.insert(Contract.TABLE_NAME,null,values);
                    }
                Toast.makeText(getApplicationContext(),
                           "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(act,HomePage.class);
                startActivity(intent);

//
//
//
//                if(ed1.getText().toString().equals("admin") &&
//                        ed2.getText().toString().equals("admin")) {
//                    Toast.makeText(getApplicationContext(),
//                            "Redirecting...",Toast.LENGTH_SHORT).show();
//                    setContentView(R.layout.article_view);
//
//                }else{
//                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
////
////                            tx1.setVisibility(View.VISIBLE);
////                    tx1.setBackgroundColor(Color.RED);
////                    counter--;
////                    tx1.setText(Integer.toString(counter));
//
////                    if (counter == 0) {
////                        b1.setEnabled(false);
////                    }
//                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
