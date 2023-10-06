package com.content.adapter.course

import com.content.adapter.course.persistence.CourseGroupEntityRepository
import com.content.application.port.CourseGroupSearchPort
import com.content.application.request.CourseGroupAdministrativeSearchRequest
import com.content.application.response.CourseGroupItem
import com.content.application.response.CourseGroupSearchResponse
import com.content.util.share.Logger
import org.springframework.data.domain.PageRequest

class CourseGroupSearchAdapter(
    private val courseGroupEntityRepository: CourseGroupEntityRepository,
) : CourseGroupSearchPort {
    override fun findCourseGroupBy(request: CourseGroupAdministrativeSearchRequest): CourseGroupSearchResponse {
        val sliceCourseGroupEntity = courseGroupEntityRepository.findCourseGroupEntitiesByCityCodeAndDistrictCode(
            cityCode = request.cityCode,
            districtCode = request.districtCode,
            pageable = PageRequest.of(request.start, PAGE_SIZE)
        )

        return CourseGroupSearchResponse(
            hasNext = sliceCourseGroupEntity.hasNext(),
            courseGroupItems = sliceCourseGroupEntity.content
                .map {
                    CourseGroupItem(
                        title = it.groupName,
                        groupId = it.id,
                        cityName = it.cityCode.koreanName,
                        districtName = it.districtCode.koreanName,
                        modifiedAt = it.modifiedAt,
                    )
                }
        )
    }

    companion object : Logger() {
        const val PAGE_SIZE = 10
    }
}