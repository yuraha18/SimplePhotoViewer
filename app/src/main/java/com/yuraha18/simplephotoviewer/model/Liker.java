package com.yuraha18.simplephotoviewer.model;

import android.app.Activity;
import android.content.Context;

import com.yuraha18.simplephotoviewer.model.DTO.Photo;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.APIService;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.ApiConstants;
import com.yuraha18.simplephotoviewer.model.auth.Authorization;
import com.yuraha18.simplephotoviewer.view.FullSizePhotoShower;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yuraha18.simplephotoviewer.model.auth.Authorization.getApi;

/**
 * Created by User on 5/25/2017.
 */

public class Liker {
    Activity activity;
    boolean result;
    boolean isResponseGet;

    public Liker(Activity activity) {
        this.activity = activity;
    }

    public boolean sendLike(String photoId, final FullSizePhotoShower fullSizePhotoShower)
    {
        String accessToken = Authorization.getAccessTokenWithAuth(activity);
        APIService api  = getApi(ApiConstants.CONNECTION_URL);

        final Call<Photo> call = api.likePhoto(photoId, accessToken);

                call.enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call, Response<Photo> response) {
                        isResponseGet = true;
                        if (response.body()!=null)
                            result = true;

                        else
                            result = false;

                        fullSizePhotoShower.updateViewsAfterLiking();
                    }

                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        result = false;
                        fullSizePhotoShower.updateViewsAfterLiking();
                    }
                });


        return result;
    }

    public boolean sendUnLike(String photoId, final FullSizePhotoShower fullSizePhotoShower)
    {
        String accessToken = Authorization.getAccessTokenWithAuth(activity);
        APIService api  = getApi(ApiConstants.CONNECTION_URL);
        final Call<Photo> call = api.unlikePhoto(photoId, accessToken);

        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                isResponseGet = true;
                if (response.body()!=null)
                    result = true;

                else
                    result = false;

                fullSizePhotoShower.updateViewsAfterLiking();
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                result = false;
                fullSizePhotoShower.updateViewsAfterLiking();
            }
        });



        return result;
    }


}
