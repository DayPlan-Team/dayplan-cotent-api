package com.content.adapter.course.persistence

import com.content.adapter.course.entity.CourseGroupEntity
import com.content.util.address.CityCode
import com.content.util.address.DistrictCode
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseGroupEntityRepository : JpaRepository<CourseGroupEntity, Long> {

    fun findCourseGroupEntitiesByUserId(userId: Long): List<CourseGroupEntity>

    fun findCourseGroupEntitiesByIdIn(ids: List<Long>): List<CourseGroupEntity>

    fun findCourseGroupEntitiesByCityCodeAndDistrictCode(
        cityCode: CityCode,
        districtCode: DistrictCode,
        pageable: Pageable
    ): Slice<CourseGroupEntity>

}