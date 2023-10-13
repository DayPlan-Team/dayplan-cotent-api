package com.content.adapter.place

import com.content.adapter.client.PlaceRetrofitClient
import com.content.adapter.client.PlaceItem
import com.content.adapter.grpc.PlaceGrpcClient
import com.content.application.port.PlacePort
import com.content.domain.place.Place
import com.content.domain.share.PlaceCategory
import com.content.util.share.Logger
import org.springframework.stereotype.Component

@Component
class PlaceAdapter(
    private val placeRetrofitClient: PlaceRetrofitClient,
    private val placeGrpcClient: PlaceGrpcClient,
) : PlacePort {
    override fun getPlaceByPlaceId(placeIds: List<Long>): List<Place> {
        if (placeIds.isEmpty()) return emptyList()
        return tryGrpcPlaces(placeIds) ?: tryRetrofitPlaces(placeIds) ?: emptyList()
    }

    private fun tryGrpcPlaces(placeIds: List<Long>): List<Place>? {
        return try {
            placeGrpcClient.getPlaceResponse(placeIds)
                .map {
                    Place(
                        placeName = it.placeName,
                        placeCategory = PlaceCategory.valueOf(it.placeCategory.name),
                        latitude = it.latitude,
                        longitude = it.longitude,
                        address = it.address,
                        roadAddress = it.roadAddress,
                        placeId = it.placeId,
                    )
                }
        } catch (e: Exception) {
            log.error("[PlaceAdapter Grpc Exception]", e)
            null
        }
    }

    private fun tryRetrofitPlaces(placeIds: List<Long>): List<Place>? {
        return try {
            val call = placeRetrofitClient.getPlaceResponse(placeIds = placeIds)
            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                getPlaceItem(response.body()!!.places)
            } else null
        } catch (e: Exception) {
            log.error("[PlaceAdapter Retrofit Exception]", e)
            null
        }
    }

    override suspend fun getSuspendPlaceByPlaceId(placeIds: List<Long>): List<Place> {
        try {
            if (placeIds.isEmpty()) return emptyList()

            val response = placeRetrofitClient.getSuspendPlaceResponse(placeIds = placeIds)
            if (response.isSuccessful && response.body() != null) {
                val placeItems = response.body()!!.places

                return getPlaceItem(placeItems)
            }
        } catch (e: Exception) {
            log.error("[PlaceAdapter Retrofit Exception]", e)
        }
        return emptyList()
    }

    private fun getPlaceItem(placeItems: List<PlaceItem>): List<Place> {
        return placeItems.map {
            Place(
                placeName = it.placeName,
                placeCategory = it.placeCategory,
                latitude = it.latitude,
                longitude = it.longitude,
                address = it.address,
                roadAddress = it.roadAddress,
                placeId = it.placeId,
            )
        }
    }

    companion object : Logger()
}