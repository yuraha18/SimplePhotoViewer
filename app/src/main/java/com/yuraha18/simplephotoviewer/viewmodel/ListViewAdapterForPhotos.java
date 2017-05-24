package com.yuraha18.simplephotoviewer.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.yuraha18.simplephotoviewer.R;
import com.yuraha18.simplephotoviewer.model.DTO.Photo;
import com.yuraha18.simplephotoviewer.model.DownloadImageTask;
import com.yuraha18.simplephotoviewer.view.FullSizePhotoShower;
import com.yuraha18.simplephotoviewer.view.MainActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 5/23/2017.
 */

public class ListViewAdapterForPhotos extends BaseAdapter {
    private ArrayList<Photo> photoList;
    private LayoutInflater inflater;
    private Context ctx;
    boolean isImageFitToScreen;

    ImageView photoView ;
    TextView author;
    TextView countOfLikes;
    ImageButton likeButton;
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
         author = (TextView) convertView.findViewById(R.id.author);
         countOfLikes = (TextView) convertView.findViewById(R.id.countOfLikes);
         likeButton = (ImageButton) convertView.findViewById(R.id.likeButton);
        setOnClickListenerForLikeButton();
        setOnClickListenerForPhotoView(position);

        fillInViews(position, convertView);

        return convertView;
    }

    private void setOnClickListenerForPhotoView(int position) {
        Photo photo = getPhotoList().get(position);
        final String fullUrl = photo.getUrls().getFull();
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullSizePhotoShower newFragment = new FullSizePhotoShower();
                Bundle args = new Bundle();
                args.putString("url", fullUrl);
                newFragment.setArguments(args);

                newFragment.show(mainActivity.getSupportFragmentManager(), "bbb");
            }
        });
    }

    private void fillInViews(int position, View convertView) {
        Photo photo = photoList.get(position);
        String photoUrl =  photo.getUrls().getSmall();
        author.setText(photo.getUser().getUsername());
        DownloadImageTask.loadImage(ctx, photoUrl, photoView, convertView);
        //countOfLikes.setText(photo.getLikes());

    }

    private void setOnClickListenerForLikeButton() {
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked on like button");
            }
        });
    }


    public void setNewData(ArrayList<Photo> list)
    {
        photoList = list;
        notifyDataSetChanged();
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(ArrayList<Photo> photoList) {
        this.photoList = photoList;
    }
}
