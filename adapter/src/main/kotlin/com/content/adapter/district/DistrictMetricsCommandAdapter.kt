package com.content.adapter.district

import com.content.adapter.district.persistence.DistrictMetricsEntityRepository
import com.content.domain.district.DistrictCountType
import com.content.domain.district.port.DistrictMetricsCommandPort
import com.content.util.address.DistrictCode
import org.springframework.stereotype.Component

@Component
class DistrictMetricsCommandAdapter(
    private val districtMetricsEntityRepository: DistrictMetricsEntityRepository,
) : DistrictMetricsCommandPort {

    override fun upsertDistrict(districtCode: DistrictCode, districtCountType: DistrictCountType) {
        val newDistrictMetrics = districtMetricsEntityRepository
            .findDistrictMetricsEntityByDistrictCode(districtCode)
            .updateDistrictMetrics(districtCountType)

        districtMetricsEntityRepository.save(newDistrictMetrics)
    }

}