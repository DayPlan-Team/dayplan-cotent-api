package com.content.domain.course

object LocationRectangleRangeCreator {
    // 위도와 경도 약 500m 오차
    const val LATITUDE_ERROR = 0.0045
    const val LONGITUDE_ERROR = 0.0055

    fun getLatitudeRange(latitude: Double): LocationStayRange {
        return LocationStayRange(
            min = latitude - LATITUDE_ERROR,
            max = latitude + LATITUDE_ERROR,
        )
    }

    fun getLongitudeRange(longitude: Double): LocationStayRange {
        return LocationStayRange(
            min = longitude - LONGITUDE_ERROR,
            max = longitude + LONGITUDE_ERROR,
        )
    }
}
