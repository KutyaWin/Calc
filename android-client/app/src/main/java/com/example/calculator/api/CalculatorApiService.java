package com.example.service;

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CalculatorApiService {
    @GET("calculate")
    suspend fun calculate(
            @Query("num1") num1: String,
            @Query("op") operation: String,
            @Query("num2") num2: String
    ): String

    companion object {
        private const val BASE_URL = "http://YOUR_RASPBERRY_PI_IP:8888/"

        fun create(): CalculatorApiService {
            val client = OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()

            return retrofit.create(CalculatorApiService::class.java)
        }
    }
}