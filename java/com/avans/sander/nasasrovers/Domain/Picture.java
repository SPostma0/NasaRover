package com.avans.sander.nasasrovers.Domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.media.Image;
import android.util.Log;

import java.io.Serializable;
import java.net.URL;

import static android.graphics.BitmapFactory.*;

/**
 * Created by Sander on 3/13/2018.
 */

public class Picture implements Serializable {
    private static final String TAG = Picture.class.getSimpleName();

    private int id;
    private String cameraName;
    private String shortCameraName;
    private String imageID;
    private String url;
    private String rover;

    public Picture(){


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcameraName() {
        return cameraName;
    }

    public void setcameraName(String name) {
        this.cameraName = name;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRover() {
        return rover;
    }

    public void setRover(String rover) {
        this.rover = rover;
    }

    public String getShortCameraName() {
        return shortCameraName;
    }

    public void setShortCameraName(String shortCameraName) {
        this.shortCameraName = shortCameraName;
    }
}
