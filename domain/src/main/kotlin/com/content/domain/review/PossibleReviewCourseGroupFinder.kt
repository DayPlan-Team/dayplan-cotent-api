package com.content.domain.review

import com.content.domain.course.Course
import org.springframework.stereotype.Component

@Component
object PossibleReviewCourseGroupFinder {
    fun processPossibleReviewCourseGroup(courses: List<Course>): List<Long> {
        val courseGroupMap = courses.groupBy { it.groupId }

        return courseGroupMap.entries
            .filter { entry -> entry.value.none { it.visitedStatus } }
            .mapNotNull { entry ->
                entry.value.takeIf { it.isNotEmpty() }?.let {
                    entry.key
                }
            }
    }

}