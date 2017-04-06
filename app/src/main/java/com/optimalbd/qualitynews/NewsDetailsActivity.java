package com.optimalbd.qualitynews;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.optimalbd.qualitynews.Adapter.NewsAdapter;
import com.optimalbd.qualitynews.NewsModel.Attachment;
import com.optimalbd.qualitynews.NewsModel.Post;
import com.optimalbd.qualitynews.Utility.CurrentData;
import com.optimalbd.qualitynews.Utility.NewsSharePreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailsActivity extends AppCompatActivity {
    Context context;

    int id = 0;
    String heading = "";
    int newsId;
    String time = "";
    String details = "";
    String img = "";

    TextView idTextView;
    TextView headingTextView;
    TextView timeTextView;
    WebView detailsWebView;

    ImageView imageView;

    Toolbar toolbar;
    WebSettings webSettings;
    NewsSharePreference sharePreference;

    ArrayList<Post> postArrayList;

    ListView relatedNewsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        this.context = this;

        sharePreference = new NewsSharePreference(context);

        idTextView = (TextView) findViewById(R.id.idDeTextView);
        headingTextView = (TextView) findViewById(R.id.idOfHeading);
        timeTextView = (TextView) findViewById(R.id.d_timeTextView);
        detailsWebView = (WebView) findViewById(R.id.detailsNewaWebView);
        imageView = (ImageView) findViewById(R.id.d_imageView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        relatedNewsListView = (ListView) findViewById(R.id.relatedNewsListView);
        webSettings = detailsWebView.getSettings();
        postArrayList = new ArrayList<>();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News Details");

        newsId = getIntent().getIntExtra("newsId", 0);
        heading = getIntent().getStringExtra("heading");
        time = getIntent().getStringExtra("time");
        details = getIntent().getStringExtra("details");
        img = getIntent().getStringExtra("img");
        postArrayList = CurrentData.postArrayList;

        if (Build.VERSION.SDK_INT >= 24) {
            headingTextView.setText(Html.fromHtml(heading, Html.FROM_HTML_MODE_LEGACY));
        } else {
            headingTextView.setText(Html.fromHtml(heading));
        }

        timeTextView.setText(time);
        if (!img.equals("")) {
            Picasso.with(context).load(img).error(R.drawable.news_lead).placeholder(R.drawable.news_lead).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

//        detailsWebView.getSettings().setLoadsImagesAutomatically(false);
        detailsWebView.getSettings().setJavaScriptEnabled(true);
        detailsWebView.setScrollbarFadingEnabled(false);
        detailsWebView.loadData(details, "text/html; charset=utf-8", "utf-8");

        webSettings.setTextZoom(webSettings.getTextZoom() + sharePreference.getTextSize());


        NewsAdapter newsAdapter = new NewsAdapter(context, postArrayList);
        relatedNewsListView.setAdapter(newsAdapter);

        relatedNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String thumbnail = "";
                int newsId = postArrayList.get(position).getId();
                String heading = postArrayList.get(position).getTitlePlain();
                String details = postArrayList.get(position).getContent();
                String time = postArrayList.get(position).getDate();

                List<Attachment> attachment = postArrayList.get(position).getAttachments();
                for (Attachment medi : attachment) {
                    if (medi.getImages().getFull() != null) {
                        thumbnail = medi.getImages().getFull().getUrl();
                    }

                }
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("newsId", newsId);
                Log.e("D", " nes id : " + newsId);
                intent.putExtra("heading", heading);
                intent.putExtra("details", details);
                intent.putExtra("time", time);
                intent.putExtra("img", thumbnail);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
