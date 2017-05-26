package com.yuraha18.simplephotoviewer.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yuraha18.simplephotoviewer.R;

import java.io.InputStream;

/**
 * Created by yuraha18 on 5/23/2017.
 * this class using Picasso for loading photos and saving them in cache
 */

public class DownloadImageTask  {

    public static void loadImage(Context context, String url, ImageView imageView, View convertView)
    {
        /* progress bar is visible while photo loading*/
        ProgressBar progressBar = null;
        if (convertView != null) {
            progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        /* loading photo*/
        Picasso.with(context)
                .load(url)
                .into(imageView,  new ImageLoadedCallback(progressBar) {

                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private static class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public  ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
