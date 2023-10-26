package com.content.adapter.district.entity

import com.content.adapter.share.BaseEntity
import com.content.domain.district.DistrictCountType
import com.content.domain.district.DistrictMetrics
import com.content.util.address.DistrictCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "district_metrics",
    indexes = [
        Index(name = "idx_districtMetrics_districtCode", columnList = "districtCode"),
    ]
)
data class DistrictMetricsEntity(

    @Column
    @Enumerated(EnumType.STRING)
    val districtCode: DistrictCode,

    @Column
    val visitedCount: Long = 0L,

    @Column
    val reviewCount: Long = 0L,

    @Column
    val recommendCount: Long = 0L,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {

    fun toDomainModel(): DistrictMetrics {
        return DistrictMetrics(
            districtCode = districtCode,
            visitedCount = visitedCount,
            reviewCount = reviewCount,
            recommendCount = recommendCount,
            districtMetricsId = id,
        )
    }

    fun updateDistrictMetrics(districtCountType: DistrictCountType): DistrictMetricsEntity {
        return when(districtCountType) {
            DistrictCountType.VISITED -> this.copy(visitedCount = this.visitedCount + 1)
            DistrictCountType.REVIEW -> this.copy(reviewCount = this.reviewCount + 1)
            DistrictCountType.RECOMMEND -> this.copy(recommendCount = this.recommendCount + 1)
        }
    }

    companion object {
        fun from(districtMetrics: DistrictMetrics): DistrictMetricsEntity {
            return DistrictMetricsEntity(
                districtCode = districtMetrics.districtCode,
                visitedCount = districtMetrics.visitedCount,
                reviewCount = districtMetrics.reviewCount,
                recommendCount = districtMetrics.recommendCount,
                id = districtMetrics.districtMetricsId,
            )
        }
    }

}