package com.content.domain.review

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
object PossibleReviewCourseGroupFinder {
    fun processPossibleReviewCourseGroup(courses: List<Course>): List<Long> {
        val courseGroupMap = courses.groupBy { it.groupId }

        return courseGroupMap.entries
            .filter { entry -> entry.value.all { it.visitedStatus && it.courseStage == CourseStage.PLACE_FINISH } }
            .mapNotNull { entry ->
                entry.value.takeIf { it.isNotEmpty() }?.let {
                    entry.key
                }
            }
    }

    fun verifyPossibleReviewCourseGroup(courses: List<Course>) {
        require(
            courses.all { it.visitedStatus && it.courseStage == CourseStage.PLACE_FINISH }
        ) {
            throw ContentException(
                ContentExceptionCode.NOT_POSSIBLE_REVIEW_COURSE_GROUP
            )
        }
    }

}