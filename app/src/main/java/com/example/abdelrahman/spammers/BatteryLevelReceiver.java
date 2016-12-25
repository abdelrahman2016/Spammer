package com.example.abdelrahman.spammers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by abdelrahman on 07/12/16.
 */
public class BatteryLevelReceiver extends BroadcastReceiver {

    int scale = -1;
    int level = -1;
    int voltage = -1;
    int temp = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
//        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//        temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
//        voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
        MainActivity.check=true;
        Toast.makeText(context, "BAttery's dying!!", Toast.LENGTH_LONG).show();
        Log.e("", "BATTERY LOW!!");
    }
}

