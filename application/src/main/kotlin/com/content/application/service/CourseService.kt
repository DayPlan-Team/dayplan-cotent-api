package com.content.application.service

import com.content.application.port.CourseCommandPort
import com.content.application.port.CourseQueryPort
import com.content.application.port.PlacePort
import com.content.application.request.CourseUpsertRequest
import com.content.application.response.DetailCourse
import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(
    private val courseQueryPort: CourseQueryPort,
    private val courseCommandPort: CourseCommandPort,
    private val placePort: PlacePort,
) {

    @Transactional
    fun upsertCourse(request: CourseUpsertRequest) {

        val course = if (request.courseId == 0L && request.placeId == 0L) {
            Course(
                userId = request.userId,
                courseId = 0L,
                groupId = request.groupId,
                step = request.step,
                placeCategory = request.placeCategory,
                placeId = 0L,
                courseStage = CourseStage.START,
            )
        } else if (request.courseId != 0L && request.placeId == 0L) {
            Course(
                userId = request.userId,
                courseId = 0L,
                groupId = request.groupId,
                step = request.step,
                placeCategory = request.placeCategory,
                placeId = request.placeId,
                courseStage = CourseStage.CATEGORY_FINISH,
            )
        } else if (request.courseId != 0L) {
            Course(
                userId = request.userId,
                courseId = request.courseId,
                groupId = request.groupId,
                step = request.step,
                placeCategory = request.placeCategory,
                placeId = request.placeId,
                courseStage = CourseStage.PLACE_FINISH,
            )
        } else {
            throw ContentException(ContentExceptionCode.CONTENT_COURSE_BAD_REQUEST)
        }

        courseCommandPort.upsertCourse(course)
    }

    fun getCoursesByGroup(groupId: Long): List<Course> {
        return courseQueryPort
            .getCoursesByGroupId(groupId = groupId)
            .sortedBy { it.step }
    }

    fun getDetailCoursesByGroup(groupId: Long): List<DetailCourse> {

        val courses = courseQueryPort
            .getCoursesByGroupId(groupId = groupId)
            .sortedBy { it.step }

        val courseIds = courses.map { it.placeId }

        val placeMap = placePort.getPlaceByPlaceId(courseIds)
            .associateBy { it.placeId }

        return courses.map {

            val place = placeMap[it.placeId]

            DetailCourse(
                step = it.step,
                placeCategory = it.placeCategory,
                latitude = place?.latitude,
                longitude = place?.longitude,
                courseId = it.courseId,
                placeId = it.placeId,
                courseStage = it.courseStage,
                placeName = place?.placeName ?: "",
                address = place?.address ?: "",
                roadAddress = place?.roadAddress ?: "",
            )
        }
    }
}