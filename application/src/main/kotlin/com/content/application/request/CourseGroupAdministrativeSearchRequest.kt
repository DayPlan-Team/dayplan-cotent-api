package com.content.application.request

import com.user.util.address.CityCode
import com.user.util.address.DistrictCode

data class CourseGroupAdministrativeSearchRequest(
    val cityCode: CityCode,
    val districtCode: DistrictCode,
    val start: Int,
)
