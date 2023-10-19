package com.content.application.request

import com.content.util.address.CityCode
import com.content.util.address.DistrictCode

data class CourseGroupAdministrativeSearchRequest(
    val cityCode: CityCode,
    val districtCode: DistrictCode,
    val start: Int,
)
