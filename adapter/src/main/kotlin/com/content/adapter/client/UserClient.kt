package com.content.adapter.client

import org.springframework.stereotype.Component
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

@Component
interface UserClient {

    @Headers(ApiClientUtil.CONTENT_TYPE_APPLICATION_JSON)
    @GET("/internal/verify")
    fun getUserResponse(
        @Query("UserId") userId: Long,
    ): Response<UserResponse>

}