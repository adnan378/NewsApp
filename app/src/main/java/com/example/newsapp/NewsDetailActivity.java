package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    String title,desc,content,imageURL,url;
    private TextView titleTv,subDescTv,contentTv;
    private ImageView newsIv;
    private Button readNewsBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("desc");
        content=getIntent().getStringExtra("content");
        imageURL=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");

        titleTv=findViewById(R.id.titleID);
        subDescTv=findViewById(R.id.tvDiscription);
        contentTv=findViewById(R.id.contentTV);
        newsIv=findViewById(R.id.idIVnews);
        readNewsBtn=findViewById(R.id.idBtnReadMore);
        titleTv.setText(title);
        subDescTv.setText(desc);
        contentTv.setText(content);
        Picasso.get().load(imageURL).into(newsIv);
        readNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }
}