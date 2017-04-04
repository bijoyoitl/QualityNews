package com.optimalbd.qualitynews;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
    Context context;

    int id = 0;
    String heading = "";
    String time = "";
    String details = "";
    String img = "";

    TextView idTextView;
    TextView headingTextView;
    TextView timeTextView;
    WebView detailsWebView;

    ImageView imageView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        this.context = this;

        idTextView = (TextView) findViewById(R.id.idDeTextView);
        headingTextView = (TextView) findViewById(R.id.idOfHeading);
        timeTextView = (TextView) findViewById(R.id.d_timeTextView);
        detailsWebView = (WebView) findViewById(R.id.detailsNewaWebView);
        imageView = (ImageView) findViewById(R.id.d_imageView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News Details");

        heading = getIntent().getStringExtra("heading");
        time = getIntent().getStringExtra("time");
        details = getIntent().getStringExtra("details");
        img = getIntent().getStringExtra("img");

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
//        Log.e("ND", " details : " + details);
//        detailsWebView.getSettings().setLoadsImagesAutomatically(false);
        detailsWebView.loadData(details, "text/html; charset=utf-8", "utf-8");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
