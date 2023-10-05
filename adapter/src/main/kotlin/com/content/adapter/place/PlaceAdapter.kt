package com.content.adapter.place

import com.content.adapter.client.PlaceClient
import com.content.adapter.client.PlaceItem
import com.content.application.port.PlacePort
import com.content.domain.place.Place
import com.content.util.share.Logger
import org.springframework.stereotype.Component

@Component
class PlaceAdapter(
    private val placeClient: PlaceClient,
) : PlacePort {
    override fun getPlaceByPlaceId(placeIds: List<Long>): List<Place> {
        try {
            if (placeIds.isEmpty()) return emptyList()

            val call = placeClient.getPlaceResponse(placeIds = placeIds)

            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                val placeItems = response.body()!!.places

                return getPlaceItem(placeItems)
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
        return emptyList()
    }

    override suspend fun getSuspendPlaceByPlaceId(placeIds: List<Long>): List<Place> {
        try {
            if (placeIds.isEmpty()) return emptyList()

            val response = placeClient.getSuspendPlaceResponse(placeIds = placeIds)
            if (response.isSuccessful && response.body() != null) {
                val placeItems = response.body()!!.places

                return getPlaceItem(placeItems)
            }
        } catch (e: Exception) {
            log.error(e.message)
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