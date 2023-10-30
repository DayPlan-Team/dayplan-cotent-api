package com.content.domain.place

import com.content.domain.share.PlaceCategory

data class Place(
    val placeName: String,
    val placeCategory: PlaceCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val roadAddress: String,
    val placeId: Long = 0L,
)