package com.content.application.service

import com.content.domain.review.port.ReviewGroupCommandPort
import com.content.domain.review.port.ReviewGroupQueryPort
import com.content.domain.course.CourseGroup
import com.content.domain.review.ReviewGroup
import com.content.domain.review.ReviewGroupCommandUseCase
import com.content.domain.review.ReviewGroupUpdateRequest
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import com.content.util.lock.DistributeLock
import com.content.util.lock.DistributeLockType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ReviewGroupCommandService(
    private val reviewGroupCommandPort: ReviewGroupCommandPort,
    private val reviewGroupQueryPort: ReviewGroupQueryPort,
    private val distributeLock: DistributeLock<ReviewGroup>

) : ReviewGroupCommandUseCase {
    override fun createReviewGroupOrGet(courseGroup: CourseGroup): ReviewGroup {
        return distributeLock.withLockAtomic(
            distributeLockType = DistributeLockType.REVIEW_GROUP_CREATION,
            key = courseGroup.groupId.toString(),
            lockTime = 1_000,
            exception = ContentException(ContentExceptionCode.ALREADY_REVIEW_COURSE_GROUP),
            action = {
                reviewGroupQueryPort.getReviewGroupByCourseGroupId(courseGroupId = courseGroup.groupId) ?: run {
                    reviewGroupCommandPort.createReviewGroup(courseGroup)
                }
            }
        )
    }

    override fun updateReviewGroup(
        userId: Long,
        reviewGroupId: Long,
        reviewGroupUpdateRequest: ReviewGroupUpdateRequest
    ): ReviewGroup {
        return distributeLock.withLockAtomic(
            distributeLockType = DistributeLockType.REVIEW_GROUP_CREATION,
            key = reviewGroupId.toString(),
            lockTime = 1_000,
            exception = ContentException(ContentExceptionCode.ALREADY_REVIEW_COURSE_GROUP),
            action = {
                val reviewGroup = reviewGroupQueryPort.getReviewGroupById(reviewGroupId)
                reviewGroupCommandPort.updateReviewGroup(
                    reviewGroup.copy(
                        reviewGroupId = reviewGroupId,
                        reviewGroupName = reviewGroupUpdateRequest.reviewGroupName,
                        modifiedAt = LocalDateTime.now(),
                    )
                )
            }
        )
    }

}