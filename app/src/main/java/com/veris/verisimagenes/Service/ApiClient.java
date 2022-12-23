package com.veris.verisimagenes.Service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {

    private static Endpoints single_instance = null;
    // Url prueba local

    //public static String baseurl = "https://api-phantomx.veris.com.ec/";//BuildConfig.baseurl;
     public static String baseurl = "https://api.phantomx.com.ec/";

    public static Endpoints getInstance()
    {
        if (single_instance == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseurl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            single_instance = retrofit.create(Endpoints.class);
        }
        return single_instance;
    }

}
