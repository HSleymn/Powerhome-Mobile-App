
 package com.example.projetmobile.api;

 import okhttp3.OkHttpClient;
 import retrofit2.Retrofit;
 import retrofit2.converter.gson.GsonConverterFactory;

 public class RetrofitClient {
     private static Retrofit retrofit;
     private static final String BASE_URL = "http://192.168.1.13:80"; // Remplacez par l'adresse IP de votre serveur

     public static Retrofit getInstance() {
             retrofit = new Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .client(new OkHttpClient.Builder().build())
                     .build();

         return retrofit;
     }
 }
