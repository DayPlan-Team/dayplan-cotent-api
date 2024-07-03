package com.content.application.service

import com.content.application.port.PlacePort
import com.content.application.response.DetailCourse
import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.course.CourseUpsertRequest
import com.content.domain.course.port.CourseCommandPort
import com.content.domain.course.port.CourseGroupQueryPort
import com.content.domain.course.port.CourseQueryPort
import com.content.domain.place.Place
import com.content.domain.review.port.ReviewQueryPort
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(
    private val courseQueryPort: CourseQueryPort,
    private val courseGroupQueryPort: CourseGroupQueryPort,
    private val courseCommandPort: CourseCommandPort,
    private val reviewQueryPort: ReviewQueryPort,
    private val placePort: PlacePort,
) {
    @Transactional
    fun upsertCourse(request: CourseUpsertRequest) {
        verifyPossibleUpdateCourse(request)

        val course =
            when {
                request.courseId == 0L && request.placeId == 0L -> createCurseStageStart(request)
                request.courseId != 0L && request.placeId == 0L -> createCourseCategoryFinish(request)
                request.courseId != 0L -> createCoursePlaceFinish(request)
                else -> throw ContentException(ContentExceptionCode.CONTENT_COURSE_BAD_REQUEST)
            }

        courseCommandPort.upsertCourse(course)
    }

    private fun verifyPossibleUpdateCourse(request: CourseUpsertRequest) {
        verifyCourseGroupOwner(
            courseGroupId = request.groupId,
            userId = request.userId,
        )

        verifyReviewNotExist(
            courseGroupId = request.groupId,
        )
    }

    private fun verifyCourseGroupOwner(
        courseGroupId: Long,
        userId: Long,
    ) {
        require(courseGroupQueryPort.getCourseGroupById(courseGroupId = courseGroupId).userId == userId) {
            throw ContentException(ContentExceptionCode.USER_INVALID)
        }
    }

    private fun verifyReviewNotExist(courseGroupId: Long) {
        require(reviewQueryPort.getReviewsByCourseGroupId(courseGroupId).isEmpty()) {
            throw ContentException(
                ContentExceptionCode.BAD_REQUEST_COURSE_NOT_UPDATE_ALREADY_REVIEW,
            )
        }
    }

    private fun createCurseStageStart(request: CourseUpsertRequest): Course {
        return Course.from(
            courseId = 0L,
            placeId = 0L,
            courseStage = CourseStage.START,
            courseUpsertRequest = request,
        )
    }

    private fun createCourseCategoryFinish(request: CourseUpsertRequest): Course {
        return Course.from(
            courseId = 0L,
            placeId = request.placeId,
            courseStage = CourseStage.CATEGORY_FINISH,
            courseUpsertRequest = request,
        )
    }

    private fun createCoursePlaceFinish(request: CourseUpsertRequest): Course {
        val place = verifyAndGetPlace(request.groupId)

        return Course.from(
            place = place,
            courseStage = CourseStage.PLACE_FINISH,
            courseUpsertRequest = request,
        )
    }

    private fun verifyAndGetPlace(placeId: Long): Place {
        val places = placePort.getPlaceByPlaceId(listOf(placeId))

        require(places.isNotEmpty()) { throw ContentException(ContentExceptionCode.CONTENT_COURSE_BAD_REQUEST) }

        return places.first()
    }

    fun getDetailCoursesByGroup(groupId: Long): List<DetailCourse> {
        val courses =
            courseQueryPort
                .getCoursesByGroupId(groupId = groupId)
                .sortedBy { it.step }

        val courseIds = courses.map { it.placeId }

        val placeMap =
            placePort.getPlaceByPlaceId(courseIds)
                .associateBy { it.placeId }

        return courses.map {
            val place = placeMap[it.placeId]

            DetailCourse.from(
                course = it,
                place = place,
            )
        }
    }
}
