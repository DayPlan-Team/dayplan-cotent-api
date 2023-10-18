package com.content.application.service

import com.content.domain.course.port.CourseQueryPort
import com.content.domain.review.port.ReviewCommandPort
import com.content.domain.review.port.ReviewGroupQueryPort
import com.content.domain.review.port.ReviewQueryPort
import com.content.domain.course.Course
import com.content.domain.review.CourseWithPossibleReview
import com.content.domain.review.PossibleReviewVerifier
import com.content.domain.review.Review
import com.content.domain.review.ReviewGroup
import com.content.domain.review.ReviewOwnerVerifier
import com.content.domain.review.ReviewWriteUseCase
import org.springframework.stereotype.Service

@Service
class ReviewWriteService(
    private val reviewGroupQueryPort: ReviewGroupQueryPort,
    private val reviewQueryPort: ReviewQueryPort,
    private val courseQueryPort: CourseQueryPort,
    private val reviewCommandPort: ReviewCommandPort,
) : ReviewWriteUseCase {

    override fun getAllPossibleReviewToWrite(userId: Long, reviewGroupId: Long): List<CourseWithPossibleReview> {
        val reviewGroup = getReviewGroupAndVerify(
            userId = userId,
            reviewGroupId = reviewGroupId,
        )

        val courses = getCoursesAndVerifyPossibleReviewCourses(reviewGroup.courseGroupId)

        val reviewMap = reviewQueryPort.getReviewsByCourseIds(courses.map { it.courseId })
            .associateBy { it.courseId }

        return mapToCourseWithPossibleReview(courses = courses, reviewMap = reviewMap)
    }

    private fun getReviewGroupAndVerify(userId: Long, reviewGroupId: Long): ReviewGroup {
        val reviewGroup = reviewGroupQueryPort.getReviewGroupById(reviewGroupId = reviewGroupId)
        ReviewOwnerVerifier.verifyReviewOwner(userId = userId, reviewGroupUserId = reviewGroup.userId)

        return reviewGroup
    }

    private fun getCoursesAndVerifyPossibleReviewCourses(courseGroupId: Long): List<Course> {
        val courses = courseQueryPort.getCoursesByGroupId(courseGroupId)
            .sortedBy { it.step }
        PossibleReviewVerifier.verifyPossibleReviewCourses(courses)

        return courses
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

    override fun writeReview(review: Review): Review {
        return reviewCommandPort.upsertReview(review)
    }
}