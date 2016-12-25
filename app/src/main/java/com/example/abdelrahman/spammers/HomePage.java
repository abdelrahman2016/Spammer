package com.example.abdelrahman.spammers;

import android.app.Activity;

import cz.msebera.android.httpclient.Header;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.abdelrahman.spammers.data.Contract;
import com.example.abdelrahman.spammers.data.DB;
import com.example.abdelrahman.spammers.data.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class HomePage extends Activity  implements Datafetch {
    Button block, myblockednumbers, allblockednumbers, signout, contacts;
    EditText ed1;
   BackGround background;
    public static AsyncHttpClient syncHttpClient = new SyncHttpClient();
    public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    final String LOG_TAG = HomePage.class.getSimpleName();
    DB db;

    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.article_view, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view);
        Log.v(LOG_TAG, "ONCreate HomePage Activity");

        db = new DB(this);
        //final SQLiteDatabase dbSql=db.getWritableDatabase();
        block = (Button) findViewById(R.id.button5);
        ed1 = (EditText) findViewById(R.id.editText6);
        signout = (Button) findViewById(R.id.sign_out);
        contacts = (Button) findViewById(R.id.contacts);
        myblockednumbers = (Button) findViewById(R.id.button2);
        allblockednumbers = (Button) findViewById(R.id.button3);
        final Activity act = this;
        block.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String spam = ed1.getText().toString();
                //      ContentValues contentValues=new ContentValues();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userNu = preferences.getString("userNumber", null);
                String userNa = preferences.getString("userName", null);
                Data data = new Data(act);
                data.addSpam(userNa, userNu, spam);
                    background=new BackGround(act,(Datafetch) act);
                background.execute(userNa,userNu,spam);

                //contentValues.put(Contract.COLUMN_USER_NUMBER,userNu);
                // contentValues.put(Contract.COLUMN_USER_NAME,userNa);
                // contentValues.put(Contract.COLUMN_SPAM_NUMBER,spam);
                //dbSql.insert(Contract.TABLE_NAME,null,contentValues);
            }
        });
        myblockednumbers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // setContentView(R.layout.myblockednumbers);
                Intent intent = new Intent(act, MyBlocked.class);
                startActivity(intent);
            }
        });
        allblockednumbers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //setContentView(R.layout.allblockednumbers);
                Intent intent = new Intent(act, AllBlockedNumbers.class);
                startActivity(intent);

            }
        });
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, MyContacts.class);
                startActivity(intent);
            }
        });
        //  Activity act=this;
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                preferences.edit().putString("userName", "userName").commit();
                preferences.edit().putString("userNumber", "userNumber").commit();
                Intent intent = new Intent(act, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public HttpURLConnection connect(String userName,String userNumber,String spam) {
        HttpURLConnection connection;
        try {
            Log.v(LOG_TAG,userName+userNumber+spam);
            URL uri = new URL("http://192.168.122.1:8000/spammed/");
            // URL uri=new URL("http://10.0.2.2:8000");
            connection = (HttpURLConnection) uri.openConnection();
            //  connection.setRequestMethod("GET");
            connection.setRequestMethod("POST");
//
//
//

            connection.connect();
            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams params = new RequestParams();
            params.put("username", userName);
            params.put("user_number", userNumber);
            params.put("spammed_number", spam);
            getClient().post("http://192.168.122.1:8000/spammed/", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.e("yaraaaab", statusCode + "");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("yaraaaab", statusCode + "");
                }
            });
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

    private static AsyncHttpClient getClient() {
        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null)
            return syncHttpClient;
        return asyncHttpClient;
    }

    @Override
    public void update(String data) {
    return;
    }
}


