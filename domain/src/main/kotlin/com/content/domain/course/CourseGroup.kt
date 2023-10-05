package com.content.domain.course

import com.user.util.address.CityCode
import com.user.util.address.DistrictCode

data class CourseGroup(
    val userId: Long,
    val groupId: Long = 0L,
    val groupName: String = DEFAULT_NAME,
    val cityCode: CityCode,
    val districtCode: DistrictCode,
) {
    companion object {
        const val DEFAULT_NAME = "제목 없음"
    }
}