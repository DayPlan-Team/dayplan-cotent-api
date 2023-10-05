package com.content.application.port

import com.content.domain.place.Place
import org.springframework.stereotype.Component

@Component
interface PlacePort {

    fun getPlaceByPlaceId(placeIds: List<Long>): List<Place>

    suspend fun getSuspendPlaceByPlaceId(placeIds: List<Long>): List<Place>
}