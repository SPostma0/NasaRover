package com.avans.sander.nasasrovers.Data.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sander on 3/13/2018.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.avans.sander.nasasrovers.Data.API.APIHelper;
import com.avans.sander.nasasrovers.Data.API.ASyncGetDataSet;
import com.avans.sander.nasasrovers.Data.API.OnDataSetAvail;
import com.avans.sander.nasasrovers.Domain.Picture;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "NasaRover.db";
    private static final String PHOTOS_TABLE_NAME = "photos";
    private static final String PHOTOS_COLUMN_ID = "ID";
    private static final String PHOTOS_COLUMN_PHOTO_ID = "photoID";
    private static final String PHOTOS_COLUMN_LONG_CAM_NAME = "longCamName";
    private static final String PHOTOS_COLUMN_SHORT_CAM_NAME = "shortCamName";
    private static final String PHOTOS_COLUMN_IMAGE_URL = "img_url";

    private static final int DB_V = 1;

    private OnDataSetAvail listener;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DB_V);
        Log.d(TAG, "DBHelper: Constructor called");
        this.listener =(OnDataSetAvail) context;
        listener.OnDataSetAvail(getAllPhotos());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: called");
        db.execSQL(            //////Maak tabel aan
                "CREATE TABLE "     +   PHOTOS_TABLE_NAME             +" "                                        +
                        "( `"       +   PHOTOS_COLUMN_ID              +"` INTEGER PRIMARY KEY AUTOINCREMENT, "    +
                        "`"         +   PHOTOS_COLUMN_PHOTO_ID        + "` INTEGER NOT NULL, "                    +
                        "`"         +   PHOTOS_COLUMN_LONG_CAM_NAME   + "` TEXT NOT NULL, "                       +
                        "`"         +   PHOTOS_COLUMN_SHORT_CAM_NAME  + "` TEXT NOT NULL, "                       +
                        "`"         +   PHOTOS_COLUMN_IMAGE_URL       + "` TEXT NOT NULL )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: called");
        db.execSQL("DROP TABLE IF EXISTS " + PHOTOS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPhoto (Picture picture) {
        Log.d(TAG, "insertPhoto: called");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PHOTOS_COLUMN_PHOTO_ID, picture.getImageID());
        contentValues.put(PHOTOS_COLUMN_SHORT_CAM_NAME, picture.getShortCameraName());
        contentValues.put(PHOTOS_COLUMN_LONG_CAM_NAME, picture.getcameraName());
        contentValues.put(PHOTOS_COLUMN_IMAGE_URL, picture.getUrl());
        Log.d(TAG, "insertPhoto: " + picture.getImageID());


        db.insert(  PHOTOS_TABLE_NAME,
                    null,
                    contentValues);

        return true;
    }



    public ArrayList<Picture> getAllPhotos() {
        Log.d(TAG, "getAllPhotos: called");
        ArrayList<Picture> array_list = new ArrayList<Picture>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+PHOTOS_TABLE_NAME+";", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Picture picture = new Picture();

            //////////Putting values in the picture

            picture.setId(res.getInt(res.getColumnIndex(PHOTOS_COLUMN_ID)));
            picture.setShortCameraName(res.getString(res.getColumnIndex(PHOTOS_COLUMN_SHORT_CAM_NAME)));
            picture.setImageID("" + res.getInt(res.getColumnIndex(PHOTOS_COLUMN_PHOTO_ID)));
            picture.setcameraName(res.getString(res.getColumnIndex(PHOTOS_COLUMN_LONG_CAM_NAME)));
            picture.setUrl(res.getString(res.getColumnIndex(PHOTOS_COLUMN_IMAGE_URL)));

            array_list.add(picture);
            res.moveToNext();

        }
        return array_list;
    }

}