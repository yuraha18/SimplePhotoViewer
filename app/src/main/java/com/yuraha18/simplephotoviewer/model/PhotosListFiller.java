package com.yuraha18.simplephotoviewer.model;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.yuraha18.simplephotoviewer.R;
import com.yuraha18.simplephotoviewer.model.DTO.Photo;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.APIService;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.ApiConstants;
import com.yuraha18.simplephotoviewer.model.auth.Authorization;
import com.yuraha18.simplephotoviewer.view.MainActivity;
import com.yuraha18.simplephotoviewer.viewmodel.ListViewAdapterForPhotos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 5/24/2017.
 */

public class PhotosListFiller {
    private int bufferItemCount = 15;
    private int page = 1;
    private int itemCount = 0;
    private boolean isLoading = true;
    GridView listView;
    MainActivity mainActivity;
    private  ListViewAdapterForPhotos adapter;

    public PhotosListFiller(Context ctx, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        adapter = new ListViewAdapterForPhotos(ctx, new ArrayList<Photo>(), mainActivity);
    }

    public void fillInListView(final Context ctx) {
        listView=(GridView)mainActivity.findViewById(R.id.photoList);
        additems(ctx, mainActivity);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (totalItemCount < itemCount) {
                    itemCount = totalItemCount;
                    if (totalItemCount == 0) {
                        isLoading = true; }
                }

                if (isLoading && (totalItemCount > itemCount)) {
                    isLoading = false;
                    itemCount = totalItemCount;
                    page++;
                    additems(ctx, mainActivity);

                }

                if (!isLoading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)) {
                    isLoading = true;
                }

            }
        });

    }

    private int countOfImageOnSingleScreen (Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float density  = metrics.density;
        float dpHeight = metrics.heightPixels / density;
        float dpWidth  = metrics.widthPixels / density;
        int countImagesWidth = (int) (dpWidth / 106);
        int countImagesHeigh = (int) (dpHeight/100);
        return countImagesHeigh * countImagesWidth;
    }

    private void additems(final Context context, final MainActivity mainActivity) {
        final APIService api =  getApi();

        final int countImagesInScreen = countOfImageOnSingleScreen(context);
       final int countPhotosForLoad = (int) (countImagesInScreen +3);

        Call<List<Photo>> response =  api.getListPhotos(page, countPhotosForLoad, ApiConstants.GROUP_BY_POPULAR, Authorization.getAccessToken(mainActivity));

        response.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
               int selection= adapter.getPhotoList().size()-countImagesInScreen;
                System.out.println(selection);
                addNewItemsToAdapter(response.body(), context, mainActivity);
                listView.setAdapter(adapter);
                setListener(context, mainActivity);
                adapter.notifyDataSetChanged();
                isLoading=false;
               listView.setSelection(selection);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                System.out.println("failure");
            }
        });
    }

    private void setListener(final Context ctx, MainActivity mainActivity) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("onitemclick");
                Photo photo = (Photo) listView.getAdapter().getItem(position);

                final Dialog nagDialog = new Dialog(ctx,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nagDialog.setCancelable(false);
               // nagDialog.setContentView(R.layout.preview_image);
               /* Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);

                //ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.first);
                //ivPreview.setBackground(dd);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        nagDialog.dismiss();
                    }
                });*/
                nagDialog.show();

                Toast.makeText(ctx, photo.getLikes(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewItemsToAdapter(List<Photo> photosList, Context ctx, MainActivity mainActivity) {

        if (adapter==null) {
            adapter = new ListViewAdapterForPhotos(ctx, new ArrayList<>(photosList), mainActivity);
        }


        else {
            List<Photo> oldList = adapter.getPhotoList();
            ArrayList newList = new ArrayList();
            newList.addAll(oldList);
            newList.addAll(photosList);
            System.out.println(newList.size());
            adapter.setPhotoList(newList);
            }


    }

    private APIService getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.CONNECTION_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        return retrofit.create(APIService.class); //Создаем объект, при помощи которого будем выполнять запросы
    }
}
