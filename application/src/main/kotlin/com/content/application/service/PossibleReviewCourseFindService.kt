package com.content.application.service

import com.content.domain.course.port.CourseGroupQueryPort
import com.content.domain.course.port.CourseQueryPort
import com.content.domain.course.CourseGroup
import com.content.domain.review.PossibleReviewFinder
import com.content.domain.review.PossibleReviewVerifier
import org.springframework.stereotype.Service

@Service
class PossibleReviewCourseFindService(
    private val courseQueryPort: CourseQueryPort,
    private val courseGroupQueryPort: CourseGroupQueryPort,
) {

    fun getPossibleReviewCourseGroup(userId: Long): List<CourseGroup> {
        val courseGroups = courseGroupQueryPort.getCourseGroupByUserId(userId)

        val courses = courseQueryPort.getCoursesByGroupIds(courseGroups.map { it.groupId })

        val courseGroupIds =
            PossibleReviewFinder.processPossibleReviewCourseGroup(courses)

        return courseGroupQueryPort.getCourseGroupByIds(courseGroupIds)
    }

    fun verifyPossibleReviewCourseGroup(courseGroup: CourseGroup) {
        PossibleReviewVerifier.verifyPossibleReviewCourses(courseQueryPort.getCoursesByGroupId(courseGroup.groupId))
    }
}