package com.content.application.service

import com.content.application.port.CourseQueryPort
import com.content.application.port.ReviewGroupQueryPort
import com.content.application.port.ReviewQueryPort
import com.content.domain.course.Course
import com.content.domain.review.CourseWithPossibleReview
import com.content.domain.review.Review
import com.content.domain.review.ReviewWriteUseCase
import org.springframework.stereotype.Service

@Service
class ReviewWriteService(
    private val reviewGroupQueryPort: ReviewGroupQueryPort,
    private val reviewQueryPort: ReviewQueryPort,
    private val courseQueryPort: CourseQueryPort,
) : ReviewWriteUseCase {

    override fun getAllPossibleReviewToWrite(userId: Long, reviewGroupId: Long): List<CourseWithPossibleReview> {
        val reviewGroup = reviewGroupQueryPort.getReviewGroupById(reviewGroupId = reviewGroupId)
        ReviewOwnerVerifier.verifyReviewOwner(userId = userId, reviewGroupUserId = reviewGroup.userId)

        val courses = courseQueryPort.getCoursesByGroupId(reviewGroup.courseGroupId)
            .sortedBy { it.step }

        val reviewMap = reviewQueryPort.getReviewsByCourseIds(courses.map { it.courseId })
            .associateBy { it.courseId }

        return mapToCourseWithPossibleReview(courses = courses, reviewMap = reviewMap)
    }

    private fun mapToCourseWithPossibleReview(
        courses: List<Course>,
        reviewMap: Map<Long, Review>
    ): List<CourseWithPossibleReview> {
        return courses.map { course ->
            reviewMap[course.courseId]?.let { review ->
                CourseWithPossibleReview.of(course = course, review = review)
            } ?: CourseWithPossibleReview.of(course = course)
        }
    }
}