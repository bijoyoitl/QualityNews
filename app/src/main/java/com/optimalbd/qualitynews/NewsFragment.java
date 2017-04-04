package com.optimalbd.qualitynews;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.optimalbd.qualitynews.Adapter.NewsAdapter;
import com.optimalbd.qualitynews.NewsModel.Attachment;
import com.optimalbd.qualitynews.NewsModel.NewsMain;
import com.optimalbd.qualitynews.NewsModel.Post;
import com.optimalbd.qualitynews.Utility.AllUrl;
import com.optimalbd.qualitynews.Utility.InternetConnection;
import com.optimalbd.qualitynews.Utility.NewsSharePreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    CategoryApis categoryApis;
    ListView listView;

    int mCurrentPage = 2;
    boolean userScrolled = false;

    RelativeLayout loadMoreLayout;

    ArrayList<Post> postArrayList;
    ArrayList<Post> scrollPostArrayList;

    NewsSharePreference sharePreference;
    int catId;

    ProgressBar loading_progressBar;

    public NewsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public NewsFragment(int catId) {
        this.catId = catId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        listView = (ListView) view.findViewById(R.id.newsListView);
        loadMoreLayout = (RelativeLayout) view.findViewById(R.id.loadMoreLayout);
        loading_progressBar = (ProgressBar) view.findViewById(R.id.loading_progressBar);

        postArrayList = new ArrayList<>();
        scrollPostArrayList = new ArrayList<>();
        sharePreference = new NewsSharePreference(getActivity());

        if (InternetConnection.isInternet(getActivity())) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(AllUrl.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            categoryApis = retrofit.create(CategoryApis.class);

            getData(AllUrl.categoryNewsUrl + catId);
        } else {
            internetConnectionAlert();
        }

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (userScrolled
                        && firstVisibleItem + visibleItemCount == totalItemCount) {

                    userScrolled = false;
                    int totalPage = sharePreference.getTotalPages();

                    if (mCurrentPage <= totalPage) {

                        String link = AllUrl.categoryNewsUrl + catId + "&page=" + mCurrentPage;
                        getScrollData(link, totalItemCount);
                    }
                }

            }
        });

        return view;
    }

    private void getData(String link) {

        final Call<NewsMain> getAllNews = categoryApis.getAllNews(link);

        getAllNews.enqueue(new Callback<NewsMain>() {
            @Override
            public void onResponse(Call<NewsMain> call, Response<NewsMain> response) {
                NewsMain newsMain = response.body();
                int pages = newsMain.getPages();

                sharePreference.saveTotalPage(pages);

                postArrayList = (ArrayList<Post>) newsMain.getPosts();
                NewsAdapter newsAdapter = new NewsAdapter(getActivity(), postArrayList);
                listView.setAdapter(newsAdapter);
                loading_progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NewsMain> call, Throwable t) {
                t.printStackTrace();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String thumbnail = "";
                int newsId = postArrayList.get(position).getId();
                String heading = postArrayList.get(position).getTitlePlain();
                String details = postArrayList.get(position).getContent();
                String time = postArrayList.get(position).getDate();

                List<Attachment> attachment = postArrayList.get(position).getAttachments();
                for (Attachment medi : attachment) {
                    thumbnail = medi.getImages().getFull().getUrl();

                }
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("heading", heading);
                intent.putExtra("details", details);
                intent.putExtra("time", time);
                intent.putExtra("img", thumbnail);
                startActivity(intent);
            }
        });
    }

    private void getScrollData(String link, final int totalItem) {

        loadMoreLayout.setVisibility(View.VISIBLE);

        final Call<NewsMain> getAllNews = categoryApis.getAllNews(link);

        getAllNews.enqueue(new Callback<NewsMain>() {
            @Override
            public void onResponse(Call<NewsMain> call, Response<NewsMain> response) {
                NewsMain newsMain = response.body();

                mCurrentPage += 1;

                scrollPostArrayList = (ArrayList<Post>) newsMain.getPosts();
                postArrayList.addAll(scrollPostArrayList);

                loadMoreLayout.setVisibility(View.GONE);
                NewsAdapter newsAdapter = new NewsAdapter(getActivity(), postArrayList);
                listView.setAdapter(newsAdapter);
                listView.setSelection(totalItem - 1);
            }

            @Override
            public void onFailure(Call<NewsMain> call, Throwable t) {
                t.printStackTrace();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String thumbnail = "";
//                int newsId = postArrayList.get(position).getId();
                String heading = postArrayList.get(position).getTitlePlain();
                String details = postArrayList.get(position).getContent();
                String time = postArrayList.get(position).getDate();

                List<Attachment> attachment = postArrayList.get(position).getAttachments();
                for (Attachment medi : attachment) {
                    thumbnail = medi.getImages().getFull().getUrl();

                }
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("heading", heading);
                intent.putExtra("details", details);
                intent.putExtra("time", time);
                intent.putExtra("img", thumbnail);
                startActivity(intent);
            }
        });
    }

    private void internetConnectionAlert() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("No Internet!");
        builder.setMessage("No Internet Connection Available.");
        builder.setCancelable(false);

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (InternetConnection.isInternet(getActivity())) {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(AllUrl.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                    categoryApis = retrofit.create(CategoryApis.class);

                    getData(AllUrl.categoryNewsUrl + catId);
                } else {
                    internetConnectionAlert();
                }
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
