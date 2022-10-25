package com.example.newsapp.Interface;

import com.example.newsapp.Pojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    //-------------------------------------------Get Response -----------------------------------------
    //By this @Get annotation we can get the Data from API
    @GET("/v2/sources?apiKey=d29d58aab88d4ea0b04ddb245a230068")
    //By this function getAllData() we can get all the data  from the API
    Call<Pojo> getAllData();
}
