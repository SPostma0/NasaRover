package com.avans.sander.nasasrovers.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avans.sander.nasasrovers.Domain.Picture;
import com.avans.sander.nasasrovers.R;
import com.avans.sander.nasasrovers.UI.AsyncBindPicture;
import com.avans.sander.nasasrovers.UI.OnPictureAvail;

public class DetailViewActivity extends AppCompatActivity implements View.OnClickListener, OnPictureAvail{
    private final String TAG = this.getClass().getSimpleName();

    private Picture picture;
    private TextView cameraName;
    private ImageButton image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Log.d(TAG, "onCreate: called");
        Bundle bundle = getIntent().getExtras().getBundle("PIC");

        this.picture = (Picture) bundle.getSerializable("PIC");



        cameraName = (TextView) findViewById(R.id.detail_text_camera) ;
        image = (ImageButton) findViewById(R.id.detail_image_button);

        image.setOnClickListener(this);
        AsyncBindPicture asyncBindPicture = new AsyncBindPicture(this, picture);
        asyncBindPicture.execute();
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: called ");
        super.onBackPressed();
    }

    @Override
    public void onPictureAvailable(Bitmap picture) {
        Log.d(TAG, "onPictureAvailable: called");
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setImageBitmap(picture);
        this.cameraName.setText(this.picture.getcameraName());
    }
}
