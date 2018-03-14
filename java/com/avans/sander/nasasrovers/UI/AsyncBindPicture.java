package com.avans.sander.nasasrovers.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.avans.sander.nasasrovers.Controller.Loading_Activity;
import com.avans.sander.nasasrovers.Domain.Picture;
import com.avans.sander.nasasrovers.R;

import java.net.URL;

/**
 * Created by Sander on 3/13/2018.
 */



/////////////////Class for loading the pics onto the imageview on a bg thread.

public class AsyncBindPicture extends AsyncTask<String, Void, String>{
    private final String TAG = this.getClass().getSimpleName();
    private Picture picture;
    private Bitmap bmp;
    private OnPictureAvail listener = null;

    public AsyncBindPicture(OnPictureAvail context, Picture picture) {
        Log.d(TAG, "AsyncBindPicture: called");
        this.picture = picture;
        this.listener = context;

        //////placeholder white image until good image arrives
        if (context.getClass().isInstance(PhotoViewAdapter.ViewHolder.class)){
        this.listener.onPictureAvailable(BitmapFactory.decodeResource(((PhotoViewAdapter.ViewHolder) listener).itemView.getResources(), R.drawable.ic_launcher_background));
    }}

    @Override
    protected String doInBackground(String... strings) {

        Log.d(TAG, "doInBackground: instanced");

        try{
            URL url = new URL(picture.getUrl());
            this.bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.d(TAG, "doInBackground: image pulled");
        }catch (Exception e){
            e.getLocalizedMessage();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: called");
        super.onPostExecute(s);

        if (bmp != null){
            Log.d(TAG, "onPostExecute: called ");
            this.listener.onPictureAvailable(bmp);
        }

    }
}
