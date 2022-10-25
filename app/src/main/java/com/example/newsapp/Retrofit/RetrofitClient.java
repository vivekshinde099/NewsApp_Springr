package com.example.newsapp.Retrofit;

import com.example.newsapp.Interface.APIService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Create this Class  for Retrofit Instance
public class RetrofitClient {
    private static Retrofit retrofit;
    private static RetrofitClient mInstance;
    private static String Base_URL = "https://newsapi.org";//https://newsapi.org/v2/sources?apiKey=d29d58aab88d4ea0b04ddb245a230068

    //Here we created Retrofit Instance
    private RetrofitClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Log.i("AccessToken", "Bearer " + datacontainer.access_token);
                        Request newRequest = chain.request().newBuilder()
//                                .addHeader("Authorization", "Bearer " + datacontainer.access_token)
//                                .addHeader("apptoken", "G96398A8KB2AB391D5D4AF57B44N8")//"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiMGJjMDYwNTcxYzU3Y2JiYzMxYmM1Y2VlMDJiMjJhYTk5NWEwYjk3NzRjZmE1ODg1NzI5NmUwNzQ0MTdkMWVkMWQzYmViYTI3ZjhhNzJiNTEiLCJpYXQiOjE2MTg1NzQwMzcuMzE4ODcyLCJuYmYiOjE2MTg1NzQwMzcuMzE4ODc1LCJleHAiOjE2NTAxMTAwMzcuMzE3MzIsInN1YiI6IjU5Iiwic2NvcGVzIjpbXX0.ak9CkF_tPbr4ggY-DP9cmOERZPi1GvenERbssaNjz9V-t16PkZEdXmVtBOBy2lZH9ybqUOU5GBHsfmRCo1yT4CiQ--GZX26d9DLjhqKtIZqiaylLbPNjyWnSbE35OsavEQAUywUleAXmskYMJPAJOh7PIEMpUtuuH2kAlcOcSWtGiwxHq5CsMkxzrrcJkW50POW8mvlHF7NZ7oYSiShn7zpZ9QspaF23r1SyaCTGqi-Sh3VciYIjWbKUH8WcF5ob_3u9KUIvvJZetIXrTqBvM3Jn6Zfl_uZB3i7uLzD88AKfwazTdFurHO6x2rFvhoPnXn2W4-D5c5GZC3Lea1XMIhIaO5985TJUfhAZ2IpZT-7V1w_wyqdYCVVxLECSeryP1OhQUMCdKVaSW6VHO8n04-RldNC-L8asmfybOAb2EDWNio_ihfhHrhSPMtoxdhjBXPB52Wy-5QPubyywj6z4E_4XF63pQmumJq4yPeJwdB90wzeFUOSYEQwbKkndoXShi6ZFGsXB4kwLG3CgADq1MIPRndeDfB1kvGQRDYr3WycCBrizFXSa3JHLlwjHhUBlcwefJNVd1EXiQn0KGUpPOj4gufVg9hrYb74knWrH3EfUeRzfccJ8hEPXTHnePVjHjpOULE28fpxlovcQt_K5H98Mm1jnkUnYW5RJdckXfW4")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new com.example.newsapp.Retrofit.RetrofitClient();
        }
        return mInstance;
    }
    public APIService getApi(){
        return retrofit.create(APIService.class);
    }


}