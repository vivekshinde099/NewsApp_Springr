package com.example.newsapp;

import static android.graphics.Bitmap.CompressFormat.PNG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.Format;

public class ViewNewsDetails extends AppCompatActivity {
    TextView desc,desc1,name,category;
    ImageButton image;
    ImageView newsImage;
    Button saveImage;
    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news_details);
        desc = findViewById(R.id.desc);
        desc1 = findViewById(R.id.desc1);
        image = findViewById(R.id.image);
        newsImage = findViewById(R.id.newsImage);
        name = findViewById(R.id.name);
        saveImage = findViewById(R.id.saveImage);
        category = findViewById(R.id.category);

        desc.setText(Common.description);
        desc1.setText(Common.description);
        name.setText(Common.name);

        Glide.with(ViewNewsDetails.this) //1
                .load("https://ranartblog.com/images/depth-drawing.jpg")
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .placeholder(R.drawable.newslogo)
                .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .into(newsImage);


        Glide.with(ViewNewsDetails.this) //1
                .load(Common.link)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .placeholder(R.drawable.ic_baseline_share_24)
                .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .into(image);
        category.setText(Common.category);

        ActivityCompat.requestPermissions(ViewNewsDetails.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(ViewNewsDetails.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Common.link);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveImageToGallery();
            }
        });

    }

    private void saveImageToGallery() {


        try {


            ActivityCompat.requestPermissions(ViewNewsDetails.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);



            String url = "https://ranartblog.com/images/depth-drawing.jpg";

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            String title = URLUtil.guessFileName(url, null, null);
            request.setTitle(title);
            request.setDescription("Downloading File please wait.....");
            String cookie = CookieManager.getInstance().getCookie(url);
            request.addRequestHeader("cookie", cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

            DownloadManager downloadManager = (DownloadManager) ViewNewsDetails.this.getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);

            Toast.makeText(ViewNewsDetails.this, "Downloading started...", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Toast.makeText(ViewNewsDetails.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    }
