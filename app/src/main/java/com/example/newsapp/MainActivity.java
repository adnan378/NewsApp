package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {
    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV=findViewById(R.id.idNews);
        categoryRV=findViewById(R.id.idCategories);
        loadingPB=findViewById(R.id.idLoading);
        articlesArrayList=new ArrayList<>();
        categoryModelArrayList=new ArrayList<>();
        newsAdapter=new NewsAdapter(articlesArrayList,this);
        categoryAdapter=new CategoryAdapter(categoryModelArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsAdapter);
        categoryRV.setAdapter(categoryAdapter);
        getCategories();
        getNews("All");
        newsAdapter.notifyDataSetChanged();

    }
    private void getCategories(){
        categoryModelArrayList.add(new CategoryModel("All","https://images.unsplash.com/photo-1558981403-c5f9899a28bc?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80"));
        categoryModelArrayList.add(new CategoryModel("Technology","https://media.istockphoto.com/id/1206796363/photo/ai-machine-learning-hands-of-robot-and-human-touching-on-big-data-network-connection.webp?b=1&s=170667a&w=0&k=20&c=WZhXgwGkg7Zfsc2df7GleOi9xDG6TaIN6MFqiV-7RCU="));
        categoryModelArrayList.add(new CategoryModel("Science","https://images.unsplash.com/photo-1516339901601-2e1b62dc0c45?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTZ8fHNjaWVuY2UlMjBiYWNrZ3JvdW5kfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60"));
        categoryModelArrayList.add(new CategoryModel("Sports","https://media.istockphoto.com/id/584768918/photo/moving-soccer-ball-around-splash-drops-on-the-stadium-field.webp?b=1&s=170667a&w=0&k=20&c=SRDQHfABU375pYZJ3qiiQLvHIVSP2OgDCD7in0q_BLQ="));
        categoryModelArrayList.add(new CategoryModel("General","https://images.unsplash.com/photo-1500462918059-b1a0cb512f1d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8Z2VuZXJhbCUyMGJhY2tncm91bmR8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModelArrayList.add(new CategoryModel("Business","https://plus.unsplash.com/premium_photo-1661281307045-edb4d54e313f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTl8fGJ1c2luZXNzJTIwYmFja2dyb3VuZHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModelArrayList.add(new CategoryModel("Entertainment","https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTR8fGVudGVydGFpbm1lbnQlMjBiYWNrZ3JvdW5kfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60"));
        categoryModelArrayList.add(new CategoryModel("Health","https://plus.unsplash.com/premium_photo-1670381251701-829177a1ae9a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aGVhbHRoJTIwYmFja2dyb3VuZHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));

        categoryAdapter.notifyDataSetChanged();

    }
    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl="https://newsapi.org/v2/top-headlines?country?=in&category="+ category+ "&apiKey=cce3cf994139455bb14aca759c7bd4f1";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortyBy=publishedAt&language=en&category=science&apiKey=cce3cf994139455bb14aca759c7bd4f1";
        String BASE_URL="https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi=retrofit.create(RetrofitApi.class);
        Call<NewsModel> call;
        if(category.equals("All")){
            call=retrofitApi.getAllNews(url);
        }else{
            call=retrofitApi.getNewsByCatgory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel=response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles= newsModel.getArticles();
                for(int i=0;i< articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrl(),articles.get(i).getUrlToImage(),articles.get(i).getContent()));
                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCategoryClick(int position) {
        String category=categoryModelArrayList.get(position).getCategory();
        getNews(category);
    }
}