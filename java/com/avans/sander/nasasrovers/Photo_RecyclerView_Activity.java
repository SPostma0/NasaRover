package com.avans.sander.nasasrovers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.avans.sander.nasasrovers.Domain.Picture;
import com.avans.sander.nasasrovers.UI.OnClickForDetails;
import com.avans.sander.nasasrovers.UI.PhotoViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo_RecyclerView_Activity extends AppCompatActivity implements OnClickForDetails{
    private final String TAG = this.getClass().getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Picture> pictures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: call");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo__list_view);


        Bundle extras = this.getIntent().getBundleExtra("ExtraBundle");
        pictures = (ArrayList<Picture>) extras.getSerializable("DATASET");

        Log.d(TAG, "onCreate: extracted pics from bundle");


        this.setTitle("Curiosity");

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new PhotoViewAdapter(pictures, this);
        mLayoutManager = new LinearLayoutManager(this);



        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void OnCliCkForDetails(Picture picture) {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("URL", picture.getUrl());
        intent.putExtra("CAMNAME", picture.getcameraName());


        startActivity(intent);
    }
}
