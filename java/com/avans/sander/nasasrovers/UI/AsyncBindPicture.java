package com.avans.sander.nasasrovers.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.avans.sander.nasasrovers.Domain.Picture;

import java.net.URL;

/**
 * Created by Sander on 3/13/2018.
 */

public class AsyncBindPicture extends AsyncTask<String, Void, String>{
    private final String TAG = this.getClass().getSimpleName();

    private ImageView imageView;
    private Picture picture;
    private Bitmap bmp;
    private OnPictureAvail listener = null;

    public AsyncBindPicture(OnPictureAvail context, Picture picture) {

        this.picture = picture;
        this.listener = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        Log.d(TAG, "doInBackground: instanced");
        try{
            URL url = new URL(picture.getUrl());
            this.bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.d(TAG, "doInBackground: image captured");
        }catch (Exception e){
            e.getLocalizedMessage();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (bmp != null){
            Log.d(TAG, "onPostExecute: called ");
            this.listener.onPictureAvailable(bmp);
        }

    }
}
