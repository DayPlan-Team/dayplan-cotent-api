package com.content.adapter.client

import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

@Component
interface PlaceRetrofitClient {
    @Headers(ApiClientUtil.CONTENT_TYPE_APPLICATION_JSON)
    @GET("/user/internal/place")
    fun getPlaceResponse(
        @Query("placeId") placeIds: List<Long>,
    ): Call<PlaceResponse>

    @Headers(ApiClientUtil.CONTENT_TYPE_APPLICATION_JSON)
    @GET("/user/internal/place")
    suspend fun getSuspendPlaceResponse(
        @Query("placeId") placeIds: List<Long>,
    ): Response<PlaceResponse>
}
