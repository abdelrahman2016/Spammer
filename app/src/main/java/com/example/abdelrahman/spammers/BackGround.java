package com.example.abdelrahman.spammers;
import com.loopj.android.http.*;
import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by abdelrahman on 02/12/16.
 */
public class BackGround extends AsyncTask<String,Void,String> {
Activity activity;
    Datafetch datafetch;
    public BackGround(Activity act,Datafetch fetch){
        activity=act;
        datafetch=fetch;
    }
    final private String LOG_TAG=BackGround.class.getSimpleName();
    @Override
    protected String doInBackground(String... voids) {
        String data;
        HttpURLConnection connection=null;
        BufferedReader bufferedReader=null;
          try {
              connection=datafetch.connect(voids[0],voids[1],voids[2]);
              if(voids[0]==null){
            InputStreamReader input=new InputStreamReader(connection.getInputStream());
            if(input==null)
                return null;
            bufferedReader=new BufferedReader(input);
            StringBuffer buffer=new StringBuffer();
            String line;
            while((line=bufferedReader.readLine())!=null){
                buffer.append(line+"\n");
           }
            data=buffer.toString();
           return data;}
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                if (connection!=null){
                    connection.disconnect();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s!=null){
            datafetch.update(s);
        }
//        else{
//            Toast.makeText(activity.getApplicationContext(),"The internet connection is not stable",Toast.LENGTH_LONG).show();
//
//        }
    }
}

