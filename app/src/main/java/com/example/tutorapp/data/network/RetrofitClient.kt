package com.example.tutorapp.data.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.tutorapp.BuildConfig

object RetrofitClient {
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}