package com.optimalbd.qualitynews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.optimalbd.qualitynews.Model.Category;
import com.optimalbd.qualitynews.Model.CategoryMain;
import com.optimalbd.qualitynews.Utility.AllUrl;
import com.optimalbd.qualitynews.Utility.InternetConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    Context context;
    Retrofit retrofit;
    CategoryApis categoryApis;
    ArrayList<Category> categoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.context = this;
        categoryArrayList = new ArrayList<>();

        if (InternetConnection.isInternet(context)) {
            retrofit = new Retrofit.Builder().baseUrl(AllUrl.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            categoryApis = retrofit.create(CategoryApis.class);
            getCategoryData();
        } else {
            internetConnectionAlert();
        }
    }

    private void getCategoryData() {

        final Call<CategoryMain> categoryMainCall = categoryApis.getAllCategory(AllUrl.categoryUrl);
        categoryMainCall.enqueue(new Callback<CategoryMain>() {
            @Override
            public void onResponse(Call<CategoryMain> call, Response<CategoryMain> response) {
                CategoryMain categoryMain = response.body();
                if (categoryMain.getStatus().equals("ok")) {
                    categoryArrayList = (ArrayList<Category>) categoryMain.getCategories();

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("categoryList", categoryArrayList);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<CategoryMain> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void internetConnectionAlert() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("No Internet!");
        builder.setMessage("No Internet Connection Available.");
        builder.setCancelable(false);

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!InternetConnection.isInternet(context)) {
                    internetConnectionAlert();
                } else {
                    retrofit = new Retrofit.Builder().baseUrl(AllUrl.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                    categoryApis = retrofit.create(CategoryApis.class);
                    getCategoryData();
                }
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SplashActivity.this.finish();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
