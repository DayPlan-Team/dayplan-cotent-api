package com.content.adapter.client

import com.content.domain.share.PlaceCategory

data class PlaceResponse(
    val places: List<PlaceItem>
)

data class PlaceItem(
    val placeName: String,
    val placeCategory: PlaceCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val roadAddress: String,
    val placeId: Long,
)