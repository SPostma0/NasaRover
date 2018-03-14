package com.avans.sander.nasasrovers.Data.API;

import com.avans.sander.nasasrovers.Domain.Picture;

import java.util.ArrayList;

/**
 * Created by Sander on 3/14/2018.
 */

public interface OnDataSubSetAvail {
    public void OnDataSubSetAvail(ArrayList<Picture> pictures, String camName);
}
