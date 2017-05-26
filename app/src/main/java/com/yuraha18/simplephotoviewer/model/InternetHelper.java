package com.yuraha18.simplephotoviewer.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yuraha18 on 5/25/2017.
 */

public class InternetHelper {

    /* check internet connection*/
        public static boolean hasActiveInternetConnection(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected;

            if (activeNetwork!=null)
                isConnected = activeNetwork.isConnectedOrConnecting();

            else
                isConnected = false;

            return isConnected;
        }
    }

