package com.yuraha18.simplephotoviewer.model.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuraha18.simplephotoviewer.R;
import com.yuraha18.simplephotoviewer.model.DTO.AccessToken;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.APIService;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.ApiConstants;
import com.yuraha18.simplephotoviewer.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuraha18 on 5/25/2017.
 * this class authorize user in Unsplash by opening webview and getting access_token (oauth2)
 * also there are static class for getting access_token from sharedPreference if user authorized
 */

public class Authorization {

    WebView web;
    SharedPreferences pref;
    String authCode;
    Activity mainActivity;
    Dialog auth_dialog;

    public Authorization(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void authorize()
    {
         pref = mainActivity.getSharedPreferences(ApiConstants.APP_PREFERENCES, Context.MODE_PRIVATE);

        //create dialog for webView
        auth_dialog = new Dialog(mainActivity);
        auth_dialog.setContentView(R.layout.auth_dialog);
        web = (WebView)auth_dialog.findViewById(R.id.webv);
        web.getSettings().setJavaScriptEnabled(true);

        //getting URL from constants. Warning: client_id and secret must matches
        web.loadUrl(ApiConstants.OAUTH_URL+"?redirect_uri="+ApiConstants.REDIRECT_URI+"&response_type=code&client_id="+ApiConstants.CLIENT_ID+"&scope="+ApiConstants.OAUTH_SCOPE);
        web.setWebViewClient(new WebViewClient() {
            boolean authComplete = false;
            Intent resultIntent = new Intent();

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon){
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        String[] urls = url.split(ApiConstants.OAUTH_URL);

                        // if url doesnt have code)
                        if (urls.length<=1)
                            return;

                        authCode = urls[1];

                        if (authCode.startsWith("/") && authComplete != true) {
                            authCode = authCode.substring(1);
                            authComplete = true;
                            resultIntent.putExtra("code", authCode);
                            mainActivity.setResult(Activity.RESULT_OK, resultIntent);
                            mainActivity.setResult(Activity.RESULT_CANCELED, resultIntent);

                            SharedPreferences.Editor edit = pref.edit();
                            edit.putString("Code", authCode);
                            edit.commit();
                            auth_dialog.dismiss();
                            getToken();
                        }else if(url.contains("error=access_denied")){
                            Log.i("", "ACCESS_DENIED_HERE");
                            resultIntent.putExtra("code", authCode);
                            authComplete = true;
                            mainActivity.setResult(Activity.RESULT_CANCELED, resultIntent);
                            Toast.makeText(mainActivity.getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();

                            auth_dialog.dismiss();
                        }
                    }
                });
                auth_dialog.show();
                auth_dialog.setTitle(mainActivity.getResources().getString(R.string.authorize));
                auth_dialog.setCancelable(true);
                auth_dialog.setCanceledOnTouchOutside(true);


            }


    /* when user send pass and login, and allow enter we must get access_token*/
    private void getToken() {
        APIService api = getApi(ApiConstants.AUTH_BASIC_URL);

        //send code we get before
        Call<AccessToken> call = api.getToken(authCode);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.body()!=null)
                {
                    String tokenValue = response.body().getAccessToken();

                    /* save token in sharedpreference for using in future*/
                    SharedPreferences mSettings = mainActivity.getSharedPreferences(ApiConstants.APP_PREFERENCES, Context.MODE_PRIVATE);
                    mSettings.edit().putString(ApiConstants.ACCESS_TOKEN, tokenValue).apply();
                }
                else
                {
                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.authorizeException), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.authorizeException), Toast.LENGTH_LONG).show();
            }
        });

    }

    public static APIService getApi(String baseUrl) {
        Gson  gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) //base url
                .addConverterFactory(GsonConverterFactory.create(gson)) //converter for converting JSON'а in objects
                .build();
        return retrofit.create(APIService.class);
    }

    /* this method getToken from preferences, if it doesn't exist - call method for authorizing
    * it using when user wanna send like/unlike. When user getting photos access_token also using, but if it not exist
    * app doesnt want authorize (use method getAccessToken)
     * access_token using in whole requests*/
    public static String getAccessTokenWithAuth (Activity activity){
        String accessToken = getAccessToken(activity);

        if ("".equals(accessToken) || accessToken==null)
        {
             new Authorization(activity).authorize();
            accessToken = getAccessToken(activity);
        }

        return accessToken;
    }

    public static String getAccessToken (Activity activity){
        SharedPreferences mSettings = activity.getSharedPreferences(ApiConstants.APP_PREFERENCES, Context.MODE_PRIVATE);
        return  mSettings.getString(ApiConstants.ACCESS_TOKEN, "");
    }

}
