package com.avans.sander.nasasrovers.Controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.avans.sander.nasasrovers.Data.API.ASyncGetDataSet;
import com.avans.sander.nasasrovers.Data.API.OnDataSubSetAvail;
import com.avans.sander.nasasrovers.Domain.Picture;
import com.avans.sander.nasasrovers.R;
import com.avans.sander.nasasrovers.UI.PhotoViewAdapter;
import com.avans.sander.nasasrovers.UI.VariableScrollSpeedLinearLayoutManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnDataSubSetAvail{
    private final String TAG = this.getClass().getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner spinner;
    private boolean isSubSet = false;



    private ArrayList<Picture> pictures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_view);
        Log.d(TAG, "onCreate: call");
        super.onCreate(savedInstanceState);


        ////////////////////
        /////INIT SPINNER///
        ////////////////////

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.cams, R.layout.support_simple_spinner_dropdown_item );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);

        Bundle extras = this.getIntent().getBundleExtra("ExtraBundle");
        pictures = (ArrayList<Picture>) extras.getSerializable("DATASET");


        /////////Determine if current activity is showing a subset////////////
        if(extras.getString("CAMNAME") != null){
            isSubSet = true;
        };

        Log.d(TAG, "onCreate: extracted pics from bundle");


        /////////////////////////////////////////
        //////////////SETUP SCREEN//////////////
        ////////////////////////////////////////


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new PhotoViewAdapter(pictures, this);

        mLayoutManager = new VariableScrollSpeedLinearLayoutManager(this,15f);
        mLayoutManager.setItemPrefetchEnabled(true);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }


/////////////////////////////SELECT ITEM FOR SPINNER
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "onItemSelected: called");
        
            if (((String)adapterView.getSelectedItem()).equals("Select Cam")){


                return;
            }else {

                String selected = (String) adapterView.getSelectedItem();
                Log.d(TAG, "onItemSelected: " + selected);

                Handler handler = new Handler();
                handler.postDelayed(new ASyncGetDataSet(this, selected), 1000);

            }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    ///////////////////Listener for datasubsets//////////////////////
    @Override
    public void OnDataSubSetAvail(ArrayList<Picture> pictures, String camName) {

        Log.d(TAG, "OnDataSubSetAvail: called");
        Intent intent = new Intent(this, MainActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("DATASET", pictures);
        args.putString("CAMNAME",camName);
        intent.putExtra("ExtraBundle", args);


        startActivity(intent);

    }
}
