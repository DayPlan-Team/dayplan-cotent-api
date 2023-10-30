package com.content.application.service

import com.content.domain.course.CourseStage
import com.content.domain.course.port.CourseQueryPort
import com.content.domain.review.Review
import com.content.domain.review.ReviewCreationRequest
import com.content.domain.review.ReviewGroup
import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMeta
import com.content.domain.review.ReviewImageMetaCommandUseCase
import com.content.domain.review.ReviewImageStorageData
import com.content.domain.review.ReviewWriteUseCase
import com.content.domain.review.port.ReviewGroupQueryPort
import com.content.domain.review.port.ReviewQueryPort
import com.content.domain.user.User
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewAndReviewImageService(
    private val reviewGroupQueryPort: ReviewGroupQueryPort,
    private val reviewQueryPort: ReviewQueryPort,
    private val courseQueryPort: CourseQueryPort,
    private val reviewWriteUseCase: ReviewWriteUseCase,
    private val reviewImageMetaCommandUseCase: ReviewImageMetaCommandUseCase,
) {

    fun writeReview(
        user: User,
        reviewCreationRequest: ReviewCreationRequest,
    ): Review {
        val reviewGroup = getReviewGroup(reviewCreationRequest.reviewGroupId)

        verifyInvalidReviewWrite(
            user = user,
            reviewGroup = reviewGroup,
            courseId = reviewCreationRequest.courseId
        )

        val review = reviewCreationRequest.toReview(
            review = reviewQueryPort.getReviewByCourseId(courseId = reviewCreationRequest.courseId),
        )

        return reviewWriteUseCase.writeReview(review = review)
    }

    fun saveReviewImageMetas(
        reviewImages: List<ReviewImage>,
        reviewImageMetas: List<ReviewImageMeta>,
    ): List<ReviewImageStorageData> {
        return reviewImageMetaCommandUseCase.upsertReviewImageMeta(
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

    private fun verifyInvalidReviewWrite(
        user: User,
        reviewGroup: ReviewGroup,
        courseId: Long,
    ) {
        verifyReviewGroupOwner(reviewGroup.userId, user.userId)

        verifyInvalidReviewCourse(
            courseGroupId = reviewGroup.courseGroupId,
            courseId = courseId,
        )
    }

    private fun verifyReviewGroupOwner(reviewGroupUserId: Long, userId: Long) {
        require(reviewGroupUserId == userId) { throw ContentException(ContentExceptionCode.USER_INVALID) }
    }

    /* A 코스 리뷰를 쓰려면, 동일한 코스 그룹에 있는 B, C 모두 리뷰를 쓸 수 있는 상태여야 해요!*/
    private fun verifyInvalidReviewCourse(courseGroupId: Long, courseId: Long) {
        val courses = courseQueryPort
            .getCoursesByGroupId(courseGroupId)

        require(
            courses.all { it.visitedStatus && it.courseStage == CourseStage.PLACE_FINISH }
                    && courses.any { it.courseId == courseId }
        ) {
            throw ContentException(ContentExceptionCode.BAD_REQUEST_REVIEW)
        }
    }
}