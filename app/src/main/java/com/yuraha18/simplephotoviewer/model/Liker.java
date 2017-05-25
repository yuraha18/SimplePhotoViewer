package com.yuraha18.simplephotoviewer.model;

import android.app.Activity;
import android.content.Context;

import com.yuraha18.simplephotoviewer.model.DTO.Photo;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.APIService;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.ApiConstants;
import com.yuraha18.simplephotoviewer.model.auth.Authorization;

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

    public boolean sendLike(String photoId)
    {
        String accessToken = Authorization.getAccessTokenWithAuth(activity);
        APIService api  = getApi(ApiConstants.CONNECTION_URL);

        Call<Photo> call = api.likePhoto(photoId, accessToken);
        try {
            Photo photo = call.execute().body();
            isResponseGet = true;
            if (photo!=null)
                result = true;

            else
                result = false;
        }
         catch (Exception e) {result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean sendUnLike(String photoId)
    {
        String accessToken = Authorization.getAccessTokenWithAuth(activity);
        APIService api  = getApi(ApiConstants.CONNECTION_URL);
        Call<Photo> call = api.unlikePhoto(photoId, accessToken);
        try {
            Photo photo = call.execute().body();
            isResponseGet = true;
            if (photo!=null)
                result = true;

            else
                result = false;
        }
        catch (Exception e) {result = false;
            e.printStackTrace();
        }


        return result;
    }


}
