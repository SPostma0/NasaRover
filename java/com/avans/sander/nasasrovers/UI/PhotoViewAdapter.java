package com.avans.sander.nasasrovers.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.avans.sander.nasasrovers.DetailViewActivity;
import com.avans.sander.nasasrovers.Domain.Picture;
import com.avans.sander.nasasrovers.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Sander on 3/13/2018.
 */

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private ArrayList<Picture> pictureArrayList;
    private Context context;


    public PhotoViewAdapter(ArrayList<Picture> pictureArrayList, Context context) {
        this.pictureArrayList = pictureArrayList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.photo_row
                , parent
                , false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picture picture = pictureArrayList.get(position);
        holder.IDView.setText(picture.getImageID());


        AsyncBindPicture asyncBindPicture = new AsyncBindPicture(holder, pictureArrayList.get(position));
        asyncBindPicture.execute();
    }


    @Override
    public int getItemCount() {
        return pictureArrayList.size();
    }










    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, OnPictureAvail {
        private View view;
        private TextView IDView;
        private ImageView imageView;
        private String url;
        private Picture picture;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.IDView = (TextView) view.findViewById(R.id.listview_row_imageid);
            this.imageView = (ImageView) view.findViewById(R.id.listview_row_image);

            imageView.setOnClickListener(this);
        }


        @Override
        public void onPictureAvailable(Bitmap picture) {

            imageView.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            this.imageView.setImageBitmap(picture);




        }



        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailViewActivity.class);

            this.picture = pictureArrayList.get(getAdapterPosition());


            Bundle bundle = new Bundle();
            bundle.putSerializable("PIC", this.picture);
            intent.putExtra("PIC", bundle);

            view.getContext().startActivity(intent);


         //   ((OnClickForDetails) view.getContext()).OnCliCkForDetails(this.picture);

        }



        public Picture getPicture() {
            return picture;
        }
    }


}
