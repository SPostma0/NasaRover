package com.avans.sander.nasasrovers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avans.sander.nasasrovers.Data.API.ASyncGetDataSet;
import com.avans.sander.nasasrovers.Data.API.OnDataSetAvail;
import com.avans.sander.nasasrovers.Domain.Picture;

import java.util.ArrayList;

public class Loading_Activity extends AppCompatActivity implements OnDataSetAvail {
    private final String TAG = this.getClass().getSimpleName();

    private ArrayList<Picture> pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadResources();
    }


    @Override
    public void OnDataSetAvail(ArrayList<Picture> pictures) {
        Log.d(TAG, "OnDataSetAvail: called: DATASET SIZE: " + pictures.size());
        this.pictures = pictures;

        Intent intent = new Intent(this, Photo_RecyclerView_Activity.class);

        Bundle args = new Bundle();

        args.putSerializable("DATASET", pictures);
        intent.putExtra("ExtraBundle", args);

        startActivity(intent);
    }

    private void loadResources() {
        Log.d(TAG, "loadResources: called");
        ASyncGetDataSet aSyncGetDataSet = new ASyncGetDataSet(this);

        String apiString = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=cuyR1dGklGiBbrzuybKB0FVVOl2aGqrsJpKQZ0n8";

        aSyncGetDataSet.execute(apiString);

    }


}
