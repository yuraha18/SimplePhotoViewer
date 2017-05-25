package com.yuraha18.simplephotoviewer.model.UnsplashAPI;

import com.yuraha18.simplephotoviewer.model.DTO.AccessToken;
import com.yuraha18.simplephotoviewer.model.DTO.Photo;
import com.yuraha18.simplephotoviewer.model.DTO.PostToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 5/23/2017.
 */

public interface APIService {

    @GET(ApiConstants.GET_RANDOM_PHOTO)
    Call<Photo> getRandomPhoto( @Query("access_token") String accessToken);

    @GET(ApiConstants.GET_LIST_PHOTOS)
    Call<List<Photo>> getListPhotos(@Query("page") int page, @Query("per_page") int perPage, @Query("order_by") String orderBy,  @Query("access_token") String accessToken);

    @POST(ApiConstants.GET_AUTH_TOKEN_URL)
    Call<AccessToken> getToken(@Query("code") String code);

    @POST(ApiConstants.LIKE_PHOTO)
    Call<Photo> likePhoto(@Path("id") String id, @Query("access_token") String accessToken);

    @DELETE(ApiConstants.UNLIKE_PHOTO)
    Call<Photo> unlikePhoto(@Path("id") String id, @Query("access_token") String accessToken);
}