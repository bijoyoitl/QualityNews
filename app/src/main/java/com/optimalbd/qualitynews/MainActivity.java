package com.optimalbd.qualitynews;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.optimalbd.qualitynews.Adapter.DrawerListAdapter;
import com.optimalbd.qualitynews.Model.Category;
import com.optimalbd.qualitynews.Utility.ExitDialog;
import com.optimalbd.qualitynews.Utility.NewsSharePreference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    ArrayList<Category> categoryArrayList;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView drawerListView;
    DrawerListAdapter drawerListAdapter;
    NewsSharePreference sharePreference;
    android.support.v4.app.Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerListView = (ListView) findViewById(R.id.drawerListView);


        sharePreference = new NewsSharePreference(context);
        if (sharePreference.getTextSize() == 0) {
            sharePreference.saveTextSize(20, 0, "Small");
        }

        categoryArrayList = new ArrayList<>();

        categoryArrayList = (ArrayList<Category>) getIntent().getSerializableExtra("categoryList");


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        drawerListAdapter = new DrawerListAdapter(context, categoryArrayList);
        drawerListView.setAdapter(drawerListAdapter);

        if (savedInstanceState == null) {
            int cat_id = categoryArrayList.get(0).getId();
            getSupportActionBar().setTitle(categoryArrayList.get(0).getTitle());

            fragment = new NewsFragment(cat_id);

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containt, fragment).commit();
        }


        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int cat_id = categoryArrayList.get(position).getId();
                getSupportActionBar().setTitle(categoryArrayList.get(position).getTitle());
                Log.e("MA", "cat id : " + cat_id);
                fragment = new NewsFragment(cat_id);

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.containt, fragment).commit();
                drawerLayout.closeDrawer(navigationView);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            android.app.FragmentManager manager = getFragmentManager();
            ExitDialog dialog = new ExitDialog();
            dialog.show(manager, "Exit_Dialog");
        }
    }
}
