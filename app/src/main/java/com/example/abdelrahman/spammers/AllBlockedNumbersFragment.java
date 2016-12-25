package com.example.abdelrahman.spammers;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.abdelrahman.spammers.data.Contract;
import com.example.abdelrahman.spammers.data.DB;
import com.example.abdelrahman.spammers.data.Data;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        View rootvView= inflater.inflate(R.layout.fragment_all_blocked_numbers, container, false);
        ListView listView=(ListView) rootvView.findViewById(R.id.list_view_all);
        db=new DB(getContext());
        //SQLiteDatabase dbSql= db.getReadableDatabase();

       // Cursor c=dbSql.query(Contract.TABLE_NAME,null,null,null,null,null,null);
        Data data=new Data(getActivity());
        Cursor c=data.querySpam();

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
        Button button=(Button)rootvView.findViewById(R.id.export);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDB();
            }
        });
        return rootvView;}




    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    public final String readCsv() {
        String questionList = new String();
        AssetManager assetManager = getActivity().getAssets();

        try {
            InputStream csvStream = assetManager.open("data.csv");
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            // throw away the header
            csvReader.readNext();

            while ((questionList =questionList+ csvReader.readNext()) != null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionList;
    }
    private String exportDB() {
        String data=new String();

        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "data.csv");
        verifyStoragePermissions(getActivity());
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db1 = this.db.getReadableDatabase();
            Cursor curCSV = db1.rawQuery("SELECT * FROM "+Contract.TABLE_NAME, null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String userName=    curCSV.getString(curCSV.getColumnIndex(Contract.COLUMN_USER_NAME));
                String log= curCSV.getString(curCSV.getColumnIndex(Contract.COLUMN_USER_NUMBER));
                String time= curCSV.getString(curCSV.getColumnIndex(Contract.COLUMN_SPAM_NUMBER));
                data=data+userName+" "+log+" "+time+"\n";
                String arrStr[] = {userName,log, time};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
        return data;
    }


}

