package com.example.abdelrahman.spammers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
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
import com.example.abdelrahman.spammers.data.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends Activity implements Datafetch{
   // private DB db;

    Button b1,b2;
    EditText ed1,ed2;
    private BackGround background;
    TextView tx1;
    private final static int MY_LOADER_ID=1;
    static boolean check;



    @Override
    public void update(String data) {
        if(check==false) {
            Log.v(LOG_TAG,"da5lt ahoo fel check");
            Data data1 = new Data(this);
            data1.deleteAll();

            Log.v(LOG_TAG, data);
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String username = obj.getString("username");
                    String usernumber = obj.getString("user_number");
                    String spammednumber = obj.getString("spammed_number");

                    long k = data1.addSpam(username, usernumber, spammednumber);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public HttpURLConnection connect(String userName,String userNUmber,String spammed) {
        HttpURLConnection connection=null;

        try {
            URL uri=new URL("http://192.168.122.1:8000/spammed/");
            // URL uri=new URL("http://10.0.2.2:8000");
            connection=(HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");

            connection.connect();
            return connection;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        background=new BackGround(this,(Datafetch) this);
        background.execute(null,null,null);

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
public final String LOG_TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // db=new DB(this);
        Log.v(LOG_TAG,"ON Create MAinActivity");
        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b2 = (Button)findViewById(R.id.button2);
        tx1 = (TextView)findViewById(R.id.textView3);
            tx1.setVisibility(View.GONE);
        //final SQLiteDatabase dbSql=db.getWritableDatabase();
        final Activity act=this;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userNumber=ed2.getText().toString();
                //String [] selection=new String[]{userNumber};

                //Cursor c=dbSql.query(Contract.TABLE_NAME,null,Contract.COLUMN_USER_NUMBER+" = ?",selection,null,null,null);
                   Data data=new Data(act);
                Cursor c=data.querySpam(userNumber);
                    if(c.moveToFirst()==true){
                        Log.v(LOG_TAG,c.getColumnIndex(Contract.COLUMN_USER_NAME)+" userNamee");
                         String userName=c.getString(c.getColumnIndex(Contract.COLUMN_USER_NAME));
                        ed1.setText(userName);
                        ed2.setText(userNumber);


                    }
                else{
                       String userName= ed1.getText().toString();

                        //Data data=new Data(act);
                     long k=   data.addSpam(userName,userNumber);
                        Log.v(LOG_TAG,"Hanshof 3aml insert wala la2"+k);

//                        ContentValues values=new ContentValues();
//                        values.put(Contract.COLUMN_USER_NAME,userName);
//                        values.put(Contract.COLUMN_USER_NUMBER,userNumber);
                       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        preferences.edit().putString("userName",userName).commit();
                        preferences.edit().putString("userNumber",userNumber).commit();
//                        dbSql.insert(Contract.TABLE_NAME,null,values);
                    }
                Toast.makeText(getApplicationContext(),
                           "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(act,HomePage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//    }
//    public class CustomAdapter extends CursorAdapter {
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//
//        }
    }
    }
