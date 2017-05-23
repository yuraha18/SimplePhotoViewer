package com.yuraha18.simplephotoviewer.UnsplashAPI;

import com.yuraha18.simplephotoviewer.DTO.Photo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by User on 5/23/2017.
 */

public interface APIService {
    @GET(URL.GET_RANDOM_PHOTO)
    Call<Photo> getRandomPhoto();
}