package com.yuraha18.simplephotoviewer.view;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.yuraha18.simplephotoviewer.R;
import com.yuraha18.simplephotoviewer.model.DownloadImageTask;


public class FullSizePhotoShower extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
       final Dialog mDialog = new Dialog(getActivity(), R.style.AppTheme);
       // mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(R.layout.full_screen_image);
        Bundle args = getArguments();
        String url = args.getString("url");

        ImageView imageView = (ImageView) mDialog.findViewById(R.id.fullScreenImage);
        DownloadImageTask.loadImage(getContext(), url, imageView, getView());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });




        return mDialog;
    }

}
