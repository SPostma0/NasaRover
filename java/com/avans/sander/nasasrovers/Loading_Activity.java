package com.avans.sander.nasasrovers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avans.sander.nasasrovers.Data.API.APIHelper;
import com.avans.sander.nasasrovers.Data.API.ASyncGetDataSet;
import com.avans.sander.nasasrovers.Data.API.OnDataSetAvail;
import com.avans.sander.nasasrovers.Data.DB.DBHelper;
import com.avans.sander.nasasrovers.Domain.Picture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class Loading_Activity extends AppCompatActivity implements OnDataSetAvail {
    private final String TAG = this.getClass().getSimpleName();
    private Handler delay;
    private ArrayList<Picture> pictures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        delay = new Handler();



    }

    @Override
    protected void onResume() {
        super.onResume();

        delay.postDelayed(new ASyncGetDataSet(this),1000);
    }

    @Override
    public void OnDataSetAvail(ArrayList<Picture> pictures) {
        Log.d(TAG, "OnDataSetAvail: called: DATASET SIZE: " + pictures.size());
        this.pictures = pictures;

        Intent intent = new Intent(this, MainActivity.class);



        Bundle args = new Bundle();

        args.putSerializable("DATASET", pictures);
        intent.putExtra("ExtraBundle", args);

        startActivity(intent);
    }



        ////////////////////////////////////////////////////////
        /////////////////////HELP METHODS///////////////////////
        ///////////////////////////////////////////////////////


        private String convertToString(InputStream inputStream) throws IOException {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String nextline;
            StringBuilder sb = new StringBuilder();

            while ((nextline = bufferedReader.readLine()) != null) {
                Log.d(TAG, "convertToString: "+ nextline);
                sb.append(nextline);
            }


            bufferedReader.close();

            return sb.toString();

        }

    }



