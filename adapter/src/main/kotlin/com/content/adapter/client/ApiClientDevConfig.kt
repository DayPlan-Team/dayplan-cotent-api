package com.content.adapter.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Profile("default | local | dev")
@Configuration
class ApiClientDevConfig {

    @Bean
    fun applyUserClient(): UserClient {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/user")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserClient::class.java)
    }
}