package com.content.domain.district

import com.content.util.address.DistrictCode

data class DistrictMetrics(
    val districtCode: DistrictCode,
    val visitedCount: Long = 0L,
    val reviewCount: Long = 0L,
    val recommendCount: Long = 0L,
    val districtMetricsId: Long = 0L,
)