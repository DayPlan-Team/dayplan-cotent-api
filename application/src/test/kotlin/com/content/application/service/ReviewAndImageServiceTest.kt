package com.content.application.service

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.course.port.CourseQueryPort
import com.content.domain.review.Review
import com.content.domain.review.ReviewGroup
import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMeta
import com.content.domain.review.ReviewImageMetaCommandUseCase
import com.content.domain.review.ReviewWriteUseCase
import com.content.domain.review.port.ReviewGroupQueryPort
import com.content.domain.share.PlaceCategory
import com.content.domain.user.User
import com.content.domain.user.UserAccountStatus
import com.content.util.exception.ContentException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

class ReviewAndImageServiceTest(
    private val reviewGroupQueryPort: ReviewGroupQueryPort = mockk(),
    private val courseQueryPort: CourseQueryPort = mockk(),
    private val reviewWriteUseCase: ReviewWriteUseCase = mockk(),
    private val reviewImageMetaCommandUseCase: ReviewImageMetaCommandUseCase = mockk(),
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val sut = ReviewAndImageService(
        reviewGroupQueryPort = reviewGroupQueryPort,
        courseQueryPort = courseQueryPort,
        reviewWriteUseCase = reviewWriteUseCase,
        reviewImageMetaCommandUseCase = reviewImageMetaCommandUseCase,
    )

    given("reviewGroup 주어져요") {

        val review = Review(
            reviewId = 1L,
            reviewGroupId = 1L,
            courseId = 1L,
            content = "reviewA"
        )

        val reviewImages = emptyList<ReviewImage>()

        val reviewImageMetas = emptyList<ReviewImageMeta>()

        every { reviewGroupQueryPort.getReviewGroupById(any()) } returns ReviewGroup(
            courseGroupId = 1L,
            reviewGroupId = 1L,
            reviewGroupName = "reviewA",
            userId = 1L,
        )

        `when`("요청한 userId가 reviewGroup의 userId와 다르면") {
            val user = User(
                userId = 100L,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "nickName",
            )

            then("예외가 발생해요!") {
                shouldThrow<ContentException> {
                    sut.writeReviewAndImage(
                        user = user,
                        review = review,
                        reviewImages = reviewImages,
                        reviewImageMetas = reviewImageMetas,
                    )
                }
            }
        }

        `when`("요청한 코스가 리뷰 작성 불가능한 경우라면") {
            val user = User(
                userId = 1L,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "nickName",
            )

            every { courseQueryPort.getCoursesByGroupId(any()) } returns listOf(
                Course(
                    courseId = 1L,
                    step = 1,
                    placeId = 1L,
                    courseStage = CourseStage.CATEGORY_FINISH,
                    placeCategory = PlaceCategory.CAFE,
                    visitedStatus = true,
                    groupId = 1L,
                ),
                Course(
                    courseId = 2L,
                    step = 1,
                    placeId = 1L,
                    courseStage = CourseStage.CATEGORY_FINISH,
                    placeCategory = PlaceCategory.CAFE,
                    visitedStatus = true,
                    groupId = 1L,
                )
            )

            then("예외가 발생해요!") {
                shouldThrow<ContentException> {
                    sut.writeReviewAndImage(
                        user = user,
                        review = review,
                        reviewImages = reviewImages,
                        reviewImageMetas = reviewImageMetas,
                    )
                }
            }
        }
        `when`("요청한 코스가 리뷰 작성이 가능하다면") {
            val user = User(
                userId = 1L,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "nickName",
            )

            every { courseQueryPort.getCoursesByGroupId(any()) } returns listOf(
                Course(
                    courseId = 1L,
                    step = 1,
                    placeId = 1L,
                    courseStage = CourseStage.PLACE_FINISH,
                    placeCategory = PlaceCategory.CAFE,
                    visitedStatus = true,
                    groupId = 1L,
                ),
                Course(
                    courseId = 2L,
                    step = 1,
                    placeId = 1L,
                    courseStage = CourseStage.PLACE_FINISH,
                    placeCategory = PlaceCategory.CAFE,
                    visitedStatus = true,
                    groupId = 1L,
                )
            )

            every { reviewWriteUseCase.writeReview(any()) } just Runs
            every { reviewImageMetaCommandUseCase.upsertReviewImageMeta(any(), any()) } just Runs

            then("예외 발생 없이 정상 실행 되어야 해요") {
                sut.writeReviewAndImage(
                    user = user,
                    review = review,
                    reviewImages = reviewImages,
                    reviewImageMetas = reviewImageMetas,
                )

                verify(exactly = 1) { reviewWriteUseCase.writeReview(any()) }
                verify(exactly = 1) { reviewImageMetaCommandUseCase.upsertReviewImageMeta(any(), any()) }
            }
        }
    }

})
