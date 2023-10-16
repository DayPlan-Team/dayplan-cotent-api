package com.content.domain.review

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import org.springframework.stereotype.Component

@Component
object PossibleReviewFinder {
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
}