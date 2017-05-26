package com.yuraha18.simplephotoviewer.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.yuraha18.simplephotoviewer.R;
import com.yuraha18.simplephotoviewer.model.DTO.Photo;
import com.yuraha18.simplephotoviewer.model.DownloadImageTask;
import com.yuraha18.simplephotoviewer.model.Liker;
import com.yuraha18.simplephotoviewer.viewmodel.ListViewAdapterForPhotos;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FullSizePhotoShower.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */

/* dialog shows big image
* whowing likes button and start work of liking/disliking
* also can save its state thts why after rotating nothing bad gonna happen*/
public class FullSizePhotoShower extends DialogFragment  {
    TextView authorView;
    TextView countOfLikes;
    ImageButton likeButton;
    boolean isLikedByUser;
    boolean updateLikesResult;
    String author;
    String url;
    int likes;
    String photoId;
    ListViewAdapterForPhotos calledObject;
    int posInList;

    public FullSizePhotoShower(ListViewAdapterForPhotos calledObject, int position) {
        this.calledObject = calledObject;
        this.posInList = position;
    }

    public FullSizePhotoShower() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       final Dialog mDialog = new Dialog(getActivity(), R.style.AppTheme);

        /* set backgroung color and transienty of dialog window*/
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(230);
        mDialog.getWindow().setBackgroundDrawable(d);
        mDialog.setContentView(R.layout.full_screen_image);

        initData(mDialog, savedInstanceState);

        return mDialog;
    }

    private void initData(Dialog mDialog, Bundle savedInstanceState) {
        Bundle args;
        /* if screen was rotated get data from saved bundle*/
        if (savedInstanceState!=null)
            args = savedInstanceState;

        // else: get from bundle was send when dialog call
        else
            args = getArguments();

        url = args.getString("url");
        author = args.getString("author");
        isLikedByUser = args.getBoolean("is_liked_by_user", false);
        photoId = args.getString("id");
        likes = args.getInt("likes");
        System.out.println(photoId);

        authorView = (TextView) mDialog.findViewById(R.id.author);
        countOfLikes = (TextView) mDialog.findViewById(R.id.countOfLikes);
        countOfLikes.setText(likes+"");
        authorView.setText(author);
        likeButton = (ImageButton) mDialog.findViewById(R.id.likeButton);

        setLikeButtonColor();
        setOnClickListenerForLikeButton( photoId);
        initializeImageView(mDialog, url);
    }

    private void initializeImageView(final Dialog mDialog, String url) {
        ImageView imageView = (ImageView) mDialog.findViewById(R.id.fullScreenImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                if (calledObject!=null) {
                    List<Photo> list = calledObject.getPhotoList();
                    Photo photo = list.get(posInList);

                    if (photo.isLikedByUser()!=isLikedByUser){
                        if (isLikedByUser)
                            photo.setLikes(photo.getLikes()+1);

                        else
                            photo.setLikes(photo.getLikes()-1);
                    }
                    photo.setLikedByUser(isLikedByUser);
                    list.set(posInList, photo);
                    calledObject.setPhotoList(new ArrayList<Photo>(list));
                }

            }
        });
        DownloadImageTask.loadImage(getContext(), url, imageView, getView());
    }

    /* save instance*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("url", url);
        outState.putString("id", photoId);
        outState.putInt("likes", likes);
        outState.getInt("position_in_list", posInList);
        outState.putBoolean("is_liked_by_user", isLikedByUser);
        outState.putString("author", author);
    }

    /* cal Liker methods when user wanna send like/unlike*/
    private void setOnClickListenerForLikeButton(final String photoId) {

        likeButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              // if user didnt like this photo before - send like
                                              if (!isLikedByUser)
                                                  updateLikesResult = new Liker(getActivity()).sendLike(photoId, FullSizePhotoShower.this);
                                               // else: unlike
                                              else
                                                  updateLikesResult = new Liker(getActivity()).sendUnLike(photoId, FullSizePhotoShower.this);

                                          }
                                      });

    }

    /* this method calls outside (by object link) when we get result from server about liking/unliking this photo
    * if request successfull - change count of likes and button drawable color*/
    public void updateViewsAfterLiking(boolean result) {
        if (result) {
            isLikedByUser = !isLikedByUser;
            updateLikesCounter();
            setLikeButtonColor();
            updateLikesResult = false;
        }
    }


    private void setLikeButtonColor() {
        int color = Color.parseColor("#730AFB"); //blue color

        if (!isLikedByUser)
            color = Color.parseColor("#FCF3F3");// white color

        likeButton.setColorFilter(color);
    }

    private void updateLikesCounter() {
        String currentCount = countOfLikes.getText().toString();
        try {
            int curCount = Integer.parseInt(currentCount);
            int updCount;
            // if user liked photo +1 to whole count
            if (isLikedByUser)
                updCount = curCount+1;

            else
            updCount = curCount-1;

            likes = updCount;
            countOfLikes.setText(updCount+"");
        }
        catch (Exception e){e.printStackTrace();}


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
