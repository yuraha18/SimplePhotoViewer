package com.yuraha18.simplephotoviewer.model.UnsplashAPI;

import com.yuraha18.simplephotoviewer.model.DTO.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 5/23/2017.
 */

public interface APIService {

    @GET(ApiConstants.GET_RANDOM_PHOTO)
    Call<Photo> getRandomPhoto();

    @GET(ApiConstants.GET_LIST_PHOTOS)
    Call<List<Photo>> getListPhotos(@Query("page") int page, @Query("per_page") int perPage, @Query("order_by") String orderBy);

    //
}