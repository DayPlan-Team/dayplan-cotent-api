package com.content.adapter.client

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Profile("prod")
@Configuration
class ApiClientProdConfig {
    @Value("\${user.server.url}")
    private lateinit var userServer: String

    val okHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    @Bean
    fun applyUserClient(): UserClient {
        return Retrofit.Builder()
            .baseUrl(userServer)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserClient::class.java)
    }

    @Bean
    fun applyPlaceClient(): PlaceRetrofitClient {
        return Retrofit.Builder()
            .baseUrl(userServer)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlaceRetrofitClient::class.java)
    }
}
