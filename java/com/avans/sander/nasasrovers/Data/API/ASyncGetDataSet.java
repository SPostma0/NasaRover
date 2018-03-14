package com.avans.sander.nasasrovers.Data.API;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.avans.sander.nasasrovers.Data.DB.DBHelper;
import com.avans.sander.nasasrovers.Domain.Picture;
import com.avans.sander.nasasrovers.Loading_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sander on 3/13/2018.
 */

public class ASyncGetDataSet extends AsyncTask<String, Void, String>  {
    private final String TAG = this.getClass().getSimpleName();
    private OnDataSetAvail listener = null;
    private DBHelper dbHelper;
    private Context context;
    private boolean dataSetAvailFromDb = false;

    public ASyncGetDataSet(OnDataSetAvail context) {
        this.listener = context;
        Log.d(TAG, "ASyncGetDataSet: instantiated");
        this.context = (Context) context;

    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: call");


        if (!dataSetAvailFromDb) {
            //////////////////Params////////////////////
            int returnCode = -1;
            InputStream inputStream = null;
            String response = "";


            //////////////////HTTP SETUP////////////////
            try {
                Log.d(TAG, "doInBackground: Setting up HTTP connection");

                URLConnection urlConnection = new URL(strings[0]).openConnection();

                if (!(urlConnection instanceof HttpURLConnection)) {
                    return null;
                }

                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setAllowUserInteraction(false);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestMethod("GET");


                ////////////GetData///////////////////
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    Log.d(TAG, "doInBackground: GetData succesfull");


                    inputStream = httpURLConnection.getInputStream();

                    Log.d(TAG, "doInBackground: Converting page to string");

                    String returnValue = convertToString(inputStream);



                    return returnValue;
                } else {
                    Log.d(TAG, "doInBackground: invalid response" + httpURLConnection.getResponseCode());
                }


            } catch (MalformedURLException mue) {
                Log.d(TAG, "doInBackground: Error in URL" + mue.getLocalizedMessage());

            } catch (IOException ioe) {
                Log.d(TAG, "doInBackground: Error on connection" + ioe.getLocalizedMessage());
            }





        }
        return null;
    }



    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute: call");
        super.onPreExecute();
        dbHelper = new DBHelper(context);

        if (dbHelper.getAllPhotos().size() > 10){
            dataSetAvailFromDb = true;
        }

    }

    @Override
    protected void onPostExecute(String responseString) {
        Log.d(TAG, "onPostExecute: call");
        Log.d(TAG, "Response" + responseString);
        super.onPostExecute(responseString);

        if (!dataSetAvailFromDb) {
            ArrayList<Picture> pictures = new ArrayList<>();

            if (responseString.equals("") || responseString == null) {
                Log.d(TAG, "onPostExecute: Empty response");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray responseArray = jsonObject.getJSONArray("photos");


                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject object = responseArray.getJSONObject(i);

                    Picture picture = new Picture();

                    picture.setImageID(object.getString("id"));
                    picture.setUrl(object.getString("img_src"));

                    picture.setcameraName(object.getJSONObject("camera").getString("full_name"));
                    picture.setRover(object.getJSONObject("rover").getString("name"));
                    picture.setShortCameraName(object.getJSONObject("camera").getString("name"));

                    pictures.add(picture);

                    Log.d(TAG, "onPostExecute: new picture stats from: " + picture.getRover() + picture.getImageID());
                }

                Collections.shuffle(pictures);

                for (Picture p: pictures
                     ) {dbHelper.insertPhoto(p);

                }


                listener.OnDataSetAvail(pictures);


            } catch (JSONException e) {
                Log.d(TAG, "onPostExecute: JSON Expection" + e.getLocalizedMessage());
            }


        }else {


            listener.OnDataSetAvail(dbHelper.getAllPhotos());


        }
    }


    ////////////////////////////////////////////////////////
    /////////////////////HELP METHODS///////////////////////
    ///////////////////////////////////////////////////////


    private String convertToString(InputStream inputStream) throws IOException{

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String nextline;
        StringBuilder sb = new StringBuilder();

        while ((nextline = bufferedReader.readLine()) != null ){
            sb.append(nextline);
        }


        bufferedReader.close();

        return sb.toString();

    }

}
