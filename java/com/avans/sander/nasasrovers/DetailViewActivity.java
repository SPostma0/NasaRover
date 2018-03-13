package com.avans.sander.nasasrovers;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avans.sander.nasasrovers.Domain.Picture;
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

        this.picture = new Picture();

        this.picture.setUrl(savedInstanceState.getString("URL"));
        this.picture.setcameraName(savedInstanceState.getString("CAMNAME"));

        cameraName = (TextView) findViewById(R.id.detail_text_camera) ;
        image = (ImageButton) findViewById(R.id.detail_image_button);

        image.setOnClickListener(this);
        AsyncBindPicture asyncBindPicture = new AsyncBindPicture(this, picture);
    }

    @Override
    public void onClick(View view) {
        finishActivity(-1);
    }

    @Override
    public void onPictureAvailable(Bitmap picture) {

        image.setImageBitmap(picture);

    }
}
