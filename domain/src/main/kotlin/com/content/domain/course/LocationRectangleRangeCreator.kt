package com.content.domain.course

object LocationRectangleRangeCreator {

    private const val LATITUDE_ERROR = 0.0045
    private const val LONGITUDE_ERROR = 0.0056

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