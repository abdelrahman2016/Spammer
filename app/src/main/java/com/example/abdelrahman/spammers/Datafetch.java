package com.example.abdelrahman.spammers;

import java.net.HttpURLConnection;

/**
 * Created by abdelrahman on 02/12/16.
 */
public interface Datafetch {
void update(String data);
    HttpURLConnection connect(String dat,String dat1,String dat2);
}
