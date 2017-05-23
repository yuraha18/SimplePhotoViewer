package com.yuraha18.simplephotoviewer.model.UnsplashAPI;

import com.yuraha18.simplephotoviewer.model.DTO.Photo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by User on 5/23/2017.
 */

public interface APIService {
    @GET(URL.GET_RANDOM_PHOTO)
    Call<Photo> getRandomPhoto();
}