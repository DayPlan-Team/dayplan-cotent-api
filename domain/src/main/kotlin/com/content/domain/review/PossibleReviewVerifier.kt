package com.content.domain.review

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
object PossibleReviewVerifier {

    fun verifyPossibleReviewCourses(courses: List<Course>) {
        require(
            courses.all { it.visitedStatus && it.courseStage == CourseStage.PLACE_FINISH }
        ) {
            throw ContentException(
                ContentExceptionCode.NOT_POSSIBLE_REVIEW_COURSE_GROUP
            )
        }
    }

}