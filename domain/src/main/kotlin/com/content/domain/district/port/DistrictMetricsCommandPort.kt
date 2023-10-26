package com.content.domain.district.port

import com.content.domain.district.DistrictCountType
import com.content.util.address.DistrictCode
import org.springframework.stereotype.Component

@Component
interface DistrictMetricsCommandPort {

    fun upsertDistrict(districtCode: DistrictCode, districtCountType: DistrictCountType)

}