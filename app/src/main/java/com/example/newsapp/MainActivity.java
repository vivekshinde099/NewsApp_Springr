package com.example.newsapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.example.newsapp.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    newsAdapter newsAdapter;
    TextView title;
    List<Pojo.Source> pojoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rcvAllNotes);
        recyclerView.setAdapter(newsAdapter);
        title=findViewById(R.id.title);
        Load();
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);

    }
    private void Load() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<Pojo> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllData();
        //calling the api
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                progressDialog.dismiss();
                if(response.code()==200)
                {
                   Pojo pojo=response.body();
                    newsAdapter = new newsAdapter(MainActivity.this, pojo);
                    try {
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                        recyclerView.setHasFixedSize(false);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setAdapter(newsAdapter);
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Server not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

}