package com.yuraha18.simplephotoviewer.model;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.yuraha18.simplephotoviewer.R;
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
 * Created by yuraha18 on 5/25/2017.
 *
 * this class make likes/unlikes
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
        /* get auth token (if not exist - authorize)*/
        final String accessToken = Authorization.getAccessTokenWithAuth(activity);
        APIService api  = getApi(ApiConstants.CONNECTION_URL);

        final Call<Photo> call = api.likePhoto(photoId, accessToken);

                call.enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call, Response<Photo> response) {
                        isResponseGet = true;
                        if (response.body()!=null)
                            result = true;

                        else {
                            result = false;
                            if (accessToken!=null && !"".equals(accessToken))
                            Toast.makeText(activity, activity.getResources().getString(R.string.request_null_exception), Toast.LENGTH_LONG).show();

                        }

                        /* method has liink on called object and call its method for making changes if user make like/unlike
                        * show it for user by +- countOfLikes in screen and fillIn by color ic_thumb*/
                        fullSizePhotoShower.updateViewsAfterLiking(result);
                    }

                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        result = false;
                        fullSizePhotoShower.updateViewsAfterLiking(result);
                        Toast.makeText(activity, activity.getResources().getString(R.string.cant_connect_to_server), Toast.LENGTH_LONG).show();
                    }
                });


        return result;
    }

    public boolean sendUnLike(String photoId, final FullSizePhotoShower fullSizePhotoShower)
    {
        /* doing the same work like method above*/
        final String accessToken = Authorization.getAccessTokenWithAuth(activity);
        APIService api  = getApi(ApiConstants.CONNECTION_URL);
        final Call<Photo> call = api.unlikePhoto(photoId, accessToken);

        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                isResponseGet = true;
                if (response.body()!=null)
                    result = true;

                else {
                    result = false;
                    if (accessToken!=null && !"".equals(accessToken))
                    Toast.makeText(activity, activity.getResources().getString(R.string.request_null_exception), Toast.LENGTH_LONG).show();
                }

                fullSizePhotoShower.updateViewsAfterLiking(result);
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                result = false;
                fullSizePhotoShower.updateViewsAfterLiking(result);
                Toast.makeText(activity, activity.getResources().getString(R.string.cant_connect_to_server), Toast.LENGTH_LONG).show();
            }
        });



        return result;
    }


}
