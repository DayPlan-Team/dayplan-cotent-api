package com.content.application.service

import com.content.domain.course.CourseStage
import com.content.domain.course.port.CourseQueryPort
import com.content.domain.review.Review
import com.content.domain.review.ReviewGroup
import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMeta
import com.content.domain.review.ReviewImageMetaCommandUseCase
import com.content.domain.review.ReviewWriteUseCase
import com.content.domain.review.port.ReviewGroupQueryPort
import com.content.domain.review.port.ReviewQueryPort
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewAndImageService(
    private val reviewQueryPort: ReviewQueryPort,
    private val reviewGroupQueryPort: ReviewGroupQueryPort,
    private val courseQueryPort: CourseQueryPort,
    private val reviewWriteUseCase: ReviewWriteUseCase,
    private val reviewImageMetaCommandUseCase: ReviewImageMetaCommandUseCase,
) {

    fun writeReviewAndImage(review: Review, reviewImages: List<ReviewImage>, reviewImageMetas: List<ReviewImageMeta>) {
        val reviewGroup = getReviewGroup(review.reviewGroupId)

        verifyInvalidReviewWrite(
            review = review,
            reviewGroup = reviewGroup,
        )

        reviewWriteUseCase.writeReview(review = review)

        reviewImageMetaCommandUseCase.upsertReviewImageMeta(
            reviewImages = reviewImages,
            reviewImageMetas = reviewImageMetas
        )
    }

    private fun getReviewGroup(reviewGroupId: Long): ReviewGroup {
        return reviewGroupQueryPort
            .getReviewGroupById(
                reviewGroupId = reviewGroupId,
            )
    }

    private fun verifyInvalidReviewWrite(review: Review, reviewGroup: ReviewGroup) {
        verifyReviewGroupOwner(reviewGroup.userId, review.userId)

        verifyReviewOwner(
            reviewId = review.reviewId,
            userId = review.userId,
        )
        verifyInvalidReviewCourse(
            courseGroupId = reviewGroup.courseGroupId,
            courseId = review.courseId,
        )
    }

    private fun verifyReviewGroupOwner(reviewGroupUserId: Long, userId: Long) {
        require(reviewGroupUserId == userId) { throw ContentException(ContentExceptionCode.USER_INVALID) }
    }

    private fun verifyReviewOwner(reviewId: Long, userId: Long) {
        if (reviewId != 0L) {
            require(reviewQueryPort.getReviewById(reviewId).userId == userId) {
                throw ContentException(
                    ContentExceptionCode.USER_INVALID
                )
            }
        }
    }

    private fun verifyInvalidReviewCourse(courseGroupId: Long, courseId: Long) {
        val courses = courseQueryPort
            .getCoursesByGroupId(courseGroupId)

        require(
            courses.any { courseId == it.courseId && it.visitedStatus && it.courseStage == CourseStage.PLACE_FINISH }
        ) {
            throw ContentException(ContentExceptionCode.BAD_REQUEST_REVIEW)
        }
    }
}