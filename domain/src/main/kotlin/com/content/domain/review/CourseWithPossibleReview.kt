package com.content.domain.review

import com.content.domain.course.Course
import com.content.util.share.DateTimeCustomFormatter

data class CourseWithPossibleReview(
    val courseId: Long,
    val reviewId: Long = 0L,
    val reviewTitle: String = REVIEW_DEFAULT_TITLE,
    val createdAt: String = DEFAULT_AT,
    val modifiedAt: String = DEFAULT_AT,
) {
    companion object {

        fun of(course: Course, review: Review): CourseWithPossibleReview {
            return CourseWithPossibleReview(
                courseId = course.courseId,
                createdAt = DateTimeCustomFormatter.timeToDateMinuteFormat(review.createdAt),
                modifiedAt = DateTimeCustomFormatter.timeToDateMinuteFormat(review.modifiedAt),
                reviewTitle = review.title,
                reviewId = review.reviewId,
            )
        }

        fun of(course: Course): CourseWithPossibleReview {
            return CourseWithPossibleReview(
                courseId = course.courseId,
            )
        }

        const val REVIEW_DEFAULT_TITLE = ""
        const val DEFAULT_AT = ""
    }
}