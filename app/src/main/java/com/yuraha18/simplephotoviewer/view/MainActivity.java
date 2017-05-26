package com.yuraha18.simplephotoviewer.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.yuraha18.simplephotoviewer.R;
import com.yuraha18.simplephotoviewer.model.InternetHelper;
import com.yuraha18.simplephotoviewer.model.PhotosListFiller;
import com.yuraha18.simplephotoviewer.model.UnsplashAPI.ApiConstants;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FullSizePhotoShower.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        loadData();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /* start loading data and caught uncaughted exceptions*/
    private void loadData() {
        try {
            if (InternetHelper.hasActiveInternetConnection(getApplicationContext()))
                setUpSpinner();

            else {
                TextView textView = (TextView)findViewById(R.id.exceptionText);
                textView.setText(R.string.cant_connect_to_server);
            }
        }
        catch (Exception e){
            Toast.makeText(this, getResources().getString(R.string.unknownException), Toast.LENGTH_LONG).show();
        }
    }

    /* setup spinner and load data by this choose*/
    private void setUpSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        new PhotosListFiller(MainActivity.this).fillInListView(ApiConstants.GROUP_BY_LATEST);break;
                    case 1:
                        new PhotosListFiller(MainActivity.this).fillInListView(ApiConstants.GROUP_BY_POPULAR);break;
                    case 2:
                        new PhotosListFiller(MainActivity.this).fillInListView(ApiConstants.GROUP_BY_OLDEST);break;
                    case 3:
                        new PhotosListFiller(MainActivity.this).fillInListView(ApiConstants.RANDOM_PHOTO);break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.readme) {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.readme) {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}
