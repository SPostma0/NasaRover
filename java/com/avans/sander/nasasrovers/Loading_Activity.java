package com.avans.sander.nasasrovers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avans.sander.nasasrovers.Data.API.APIHelper;
import com.avans.sander.nasasrovers.Data.API.ASyncGetDataSet;
import com.avans.sander.nasasrovers.Data.API.OnDataSetAvail;
import com.avans.sander.nasasrovers.Data.DB.DBHelper;
import com.avans.sander.nasasrovers.Domain.Picture;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class Loading_Activity extends AppCompatActivity implements OnDataSetAvail {
    private final String TAG = this.getClass().getSimpleName();

    private ArrayList<Picture> pictures;

    private ASyncGetDataSet aSyncGetDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
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


    @Override
    protected void onResume() {
        super.onResume();
        loadResources();
    }

    private void loadResources() {
        Log.d(TAG, "loadResources: called");



        this.aSyncGetDataSet = new ASyncGetDataSet(this);
        aSyncGetDataSet.execute(APIHelper.APISTRING);

    }

}
