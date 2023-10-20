package com.content.adapter.client

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Profile("!prod")
@Configuration
class ApiClientLocalConfig {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)    // 연결 타임아웃
        .readTimeout(30, TimeUnit.SECONDS)       // 데이터 읽기 타임아웃
        .build()

    @Bean
    fun applyUserClient(): UserClient {
        return Retrofit.Builder()
            .baseUrl(USER_SERVER_DEV_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserClient::class.java)
    }

    @Bean
    fun applyPlaceClient(): PlaceRetrofitClient {
        return Retrofit.Builder()
            .baseUrl(USER_SERVER_DEV_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlaceRetrofitClient::class.java)
    }

    companion object {
        const val USER_SERVER_DEV_URL = "http://localhost:8080"
    }
}