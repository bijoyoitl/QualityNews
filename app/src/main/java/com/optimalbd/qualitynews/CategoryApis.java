package com.optimalbd.qualitynews;

import com.optimalbd.qualitynews.Model.CategoryMain;
import com.optimalbd.qualitynews.NewsModel.NewsMain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ripon on 4/4/2017.
 */

public interface CategoryApis {
    @GET()
    Call<CategoryMain> getAllCategory(@Url String s);

    @GET()
    Call<NewsMain> getAllNews(@Url String s);
}
