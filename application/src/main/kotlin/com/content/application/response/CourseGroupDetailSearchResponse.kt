package com.content.application.response

import com.user.util.address.CityCode
import com.user.util.address.DistrictCode

data class CourseGroupDetailSearchResponse(
    val groupName: String,
    val cityCode: CityCode,
    val districtCode: DistrictCode,
    val modifiedAt: String,
    val courseItems: List<CourseItem>,
)

data class CourseItem(
    val courseId: Long,
)