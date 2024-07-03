package com.content.adapter.district.persistence

import com.content.adapter.district.entity.DistrictMetricsEntity
import com.content.util.address.DistrictCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DistrictMetricsEntityRepository : JpaRepository<DistrictMetricsEntity, Long> {
    fun findDistrictMetricsEntityByDistrictCode(districtCode: DistrictCode): DistrictMetricsEntity
}
