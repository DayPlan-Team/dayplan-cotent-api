package com.content.adapter.course

import com.content.adapter.course.persistence.CourseEntityRepository
import com.content.adapter.course.persistence.CourseGroupEntityRepository
import com.content.application.port.CourseGroupSearchPort
import com.content.application.port.UserQueryPort
import com.content.application.request.CourseGroupAdministrativeSearchRequest
import com.content.application.response.CourseGroupItem
import com.content.application.response.CourseGroupListSearchResponse
import com.content.application.response.CourseStepItem
import com.content.domain.course.CourseGroup
import com.content.domain.share.PlaceCategory
import com.content.util.share.DateTimeCustomFormatter
import com.content.util.share.Logger
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class CourseGroupSearchAdapter(
    private val courseGroupEntityRepository: CourseGroupEntityRepository,
    private val courseEntityRepository: CourseEntityRepository,
) : CourseGroupSearchPort {
    override fun findCourseGroupBy(request: CourseGroupAdministrativeSearchRequest): CourseGroupListSearchResponse {

        log.info("request = ${request}")

        val sliceCourseGroupEntity = courseGroupEntityRepository.findCourseGroupEntitiesByCityCodeAndDistrictCode(
            cityCode = request.cityCode,
            districtCode = request.districtCode,
            pageable = PageRequest.of(request.start, PAGE_SIZE)
        )

        val courseGroupIds = sliceCourseGroupEntity.content.map { it.id }
        val courseToDataMap = courseEntityRepository.findCoursesEntitiesByGroupIdIn(courseGroupIds).map {
            CourseToData(
                groupId = it.groupId,
                courseId = it.id,
                step = it.step,
                placeCategory = it.placeCategory,
            )
        }.groupBy { it.groupId }


        return CourseGroupListSearchResponse(hasNext = sliceCourseGroupEntity.hasNext(),
            courseGroupItems = sliceCourseGroupEntity.content.map {
                CourseGroupItem(
                    title = it.groupName,
                    groupId = it.id,
                    cityName = it.cityCode.koreanName,
                    districtName = it.districtCode.koreanName,
                    courseCategories = courseToDataMap[it.id]?.map { course ->
                        CourseStepItem(
                            step = course.step,
                            courseId = course.courseId,
                            category = course.placeCategory,
                        )
                    }?.sortedBy { courseStep ->
                        courseStep.step
                    } ?: emptyList(),
                    modifiedAt = DateTimeCustomFormatter.timeToDateMinuteFormat(it.modifiedAt),
                )
            }

        )
    }

    override fun findCourseGroupByGroupIds(courseGroupIds: List<Long>): List<CourseGroup> {
        return courseGroupEntityRepository.findCourseGroupEntitiesByIdIn(courseGroupIds)
            .map {
                it.toCourseGroup()
            }
    }

    data class CourseToData(
        val groupId: Long,
        val courseId: Long,
        val step: Int,
        val placeCategory: PlaceCategory,
    )


    companion object : Logger() {
        const val PAGE_SIZE = 5
    }
}