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
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
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
public class FullSizePhotoShower extends DialogFragment  {
    TextView authorView;
    TextView countOfLikes;
    ImageButton likeButton;
    boolean isLikedByUser;
    ImageButton backBtn;
    boolean updateLikesResult;
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
        // Use the Builder class for convenient dialog construction
       final Dialog mDialog = new Dialog(getActivity(), R.style.AppTheme);
       // mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(230);
        mDialog.getWindow().setBackgroundDrawable(d);


        mDialog.setContentView(R.layout.full_screen_image);
        Bundle args = getArguments();
        String url = args.getString("url");

String author = args.getString("author");
        isLikedByUser = args.getBoolean("is_liked_by_user", false);
        String photoId = args.getString("id");
        int likes = args.getInt("likes");
        System.out.println(photoId);

        authorView = (TextView) mDialog.findViewById(R.id.author);
        countOfLikes = (TextView) mDialog.findViewById(R.id.countOfLikes);

        countOfLikes.setText(likes+"");
        authorView.setText(author);
        likeButton = (ImageButton) mDialog.findViewById(R.id.likeButton);

        setLikeButtonColor();
        setOnClickListenerForLikeButton( photoId);

        initializeImageView(mDialog, url);


        return mDialog;
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


    private void setOnClickListenerForLikeButton(final String photoId) {

        likeButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              if (!isLikedByUser)
                                                  updateLikesResult = new Liker(getActivity()).sendLike(photoId, FullSizePhotoShower.this);

                                              else
                                                  updateLikesResult = new Liker(getActivity()).sendUnLike(photoId, FullSizePhotoShower.this);

                                          }
                                      });



    }

    public void updateViewsAfterLiking() {
            isLikedByUser = !isLikedByUser;
            updateLikesCounter();
            setLikeButtonColor();
        updateLikesResult = false;
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
            if (isLikedByUser)
                updCount = curCount+1;

            else
            updCount = curCount-1;

            countOfLikes.setText(updCount+"");
        }
        catch (Exception e){e.printStackTrace();}


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
