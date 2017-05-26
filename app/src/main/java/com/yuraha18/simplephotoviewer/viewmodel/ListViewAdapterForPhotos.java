package com.yuraha18.simplephotoviewer.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.yuraha18.simplephotoviewer.R;
import com.yuraha18.simplephotoviewer.model.DTO.Photo;
import com.yuraha18.simplephotoviewer.model.DownloadImageTask;
import com.yuraha18.simplephotoviewer.view.FullSizePhotoShower;
import com.yuraha18.simplephotoviewer.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuraha18 on 5/23/2017.
 *
 * custom list view adapter for photos
 */

public class ListViewAdapterForPhotos extends BaseAdapter {
    private ArrayList<Photo> photoList;
    private LayoutInflater inflater;
    private Context ctx;
    ImageView photoView ;

    MainActivity mainActivity;

    public ListViewAdapterForPhotos(Context ctx, ArrayList<Photo> photoList, MainActivity mainActivity) {
        this.ctx = ctx;
        this.mainActivity = mainActivity;
        this.photoList = photoList;
       inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.photo_item, null);
        photoView = (ImageView) convertView.findViewById(R.id.photo);
        setOnClickListenerForPhotoView(position);
        fillInViews(position, convertView);
        return convertView;
    }

    /* when user clicks on some photo create dialog shows full photo with possibility like/unlike
    * and send info about photo to this dialogFragment*/
    private void setOnClickListenerForPhotoView(final int position) {
        final Photo photo = getPhotoList().get(position);
        final String fullUrl = photo.getUrls().getSmall();
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FullSizePhotoShower newFragment = new FullSizePhotoShower(ListViewAdapterForPhotos.this, position);
                Bundle args = new Bundle();
                args.putString("url", fullUrl);
                args.putString("id", photo.getId());
                args.putInt("likes", photo.getLikes());
                args.getInt("position_in_list", position);
                args.putBoolean("is_liked_by_user", photo.isLikedByUser());
                args.putString("author", photo.getUser().getUsername());
                newFragment.setArguments(args);
                Intent intent = new Intent();
                intent.putExtras(args);
                newFragment.show(mainActivity.getSupportFragmentManager(), "bbb");
            }
        });
    }

    /* load images */
    private void fillInViews(int position, View convertView) {
        Photo photo = photoList.get(position);
        String photoUrl =  photo.getUrls().getSmall();
        DownloadImageTask.loadImage(ctx, photoUrl, photoView, convertView);
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(ArrayList<Photo> photoList) {
        this.photoList = photoList;
    }


}
