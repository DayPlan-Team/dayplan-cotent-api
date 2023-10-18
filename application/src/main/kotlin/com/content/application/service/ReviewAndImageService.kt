package com.content.application.service

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
        verifyReviewOwner(
            reviewId = review.reviewId,
            userId = review.userId,
        )
        verifyInvalidReviewCourse(
            courseGroupId = reviewGroup.courseGroupId,
            courseId = review.courseId,
        )
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
        require(courseQueryPort
            .getCoursesByGroupId(courseGroupId).any { courseId == it.courseId }) {
            throw ContentException(ContentExceptionCode.BAD_REQUEST_REVIEW)
        }
    }
}