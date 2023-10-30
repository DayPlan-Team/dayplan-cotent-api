package com.content.application.service

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.course.port.CourseQueryPort
import com.content.domain.review.Review
import com.content.domain.review.ReviewCreationRequest
import com.content.domain.review.ReviewGroup
import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMeta
import com.content.domain.review.ReviewImageMetaCommandUseCase
import com.content.domain.review.ReviewWriteUseCase
import com.content.domain.review.port.ReviewGroupQueryPort
import com.content.domain.review.port.ReviewQueryPort
import com.content.domain.share.PlaceCategory
import com.content.domain.user.User
import com.content.domain.user.UserAccountStatus
import com.content.util.exception.ContentException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ReviewAndImageServiceTest(
    private val reviewGroupQueryPort: ReviewGroupQueryPort = mockk(),
    private val courseQueryPort: CourseQueryPort = mockk(),
    private val reviewWriteUseCase: ReviewWriteUseCase = mockk(),
    private val reviewImageMetaCommandUseCase: ReviewImageMetaCommandUseCase = mockk(),
    private val reviewQueryPort: ReviewQueryPort = mockk(),
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val sut = ReviewAndReviewImageService(
        reviewGroupQueryPort = reviewGroupQueryPort,
        courseQueryPort = courseQueryPort,
        reviewWriteUseCase = reviewWriteUseCase,
        reviewImageMetaCommandUseCase = reviewImageMetaCommandUseCase,
        reviewQueryPort = reviewQueryPort,
    )

    given("reviewGroup 주어져요") {

        val reviewImages = emptyList<ReviewImage>()

        val reviewImageMetas = emptyList<ReviewImageMeta>()

        every { reviewGroupQueryPort.getReviewGroupById(any()) } returns ReviewGroup(
            courseGroupId = 1L,
            reviewGroupId = 1L,
            reviewGroupName = "reviewA",
            userId = 1L,
        )

        `when`("요청한 userId가 reviewGroup의 userId와 다르면") {
            val reviewCreationRequest = ReviewCreationRequest(
                reviewGroupId = 1L,
                courseId = 1L,
                content = "reviewA"
            )

            val user = User(
                userId = 100L,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "nickName",
            )

            then("예외가 발생해요!") {
                shouldThrow<ContentException> {
                    sut.writeReview(
                        user = user,
                        reviewCreationRequest = reviewCreationRequest,
                    )
                }
            }
        }


        `when`("요청한 코스가 카테고리가 완성되지 않아서, 리뷰 작성 불가능한 경우라면") {
            val reviewCreationRequest = ReviewCreationRequest(
                reviewGroupId = 1L,
                courseId = 1L,
                content = "reviewA"
            )

            val review = Review(
                reviewId = 1L,
                reviewGroupId = 1L,
                courseId = 1L,
                content = "reviewA"
            )

            val user = User(
                userId = 1L,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "nickName",
            )

            every { reviewQueryPort.getReviewByCourseId(any()) } returns review
            every { reviewWriteUseCase.writeReview(any()) } returns review

            then("예외가 발생해요(1)!") {
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


                shouldThrow<ContentException> {
                    sut.writeReview(
                        user = user,
                        reviewCreationRequest = reviewCreationRequest,
                    )
                }
            }

            then("예외가 발생해요(2)!") {
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
                        courseStage = CourseStage.CATEGORY_FINISH,
                        placeCategory = PlaceCategory.CAFE,
                        visitedStatus = true,
                        groupId = 1L,
                    )
                )

                shouldThrow<ContentException> {
                    sut.writeReview(
                        user = user,
                        reviewCreationRequest = reviewCreationRequest,
                    )
                }
            }
        }

        `when`("요청한 코스가 작성 가능하지만, 요청한 코스가 없는 경우") {
            val reviewCreationRequest = ReviewCreationRequest(
                reviewGroupId = 1L,
                courseId = 1L,
                content = "reviewA"
            )

            val review = Review(
                reviewId = 1L,
                reviewGroupId = 1L,
                courseId = 3L,
                content = "reviewA"
            )

            val user = User(
                userId = 1L,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "nickName",
            )

            every { reviewQueryPort.getReviewByCourseId(any()) } returns review
            every { reviewWriteUseCase.writeReview(any()) } returns review

            then("예외가 발생해요!") {
                every { courseQueryPort.getCoursesByGroupId(any()) } returns listOf(
                    Course(
                        courseId = 3L,
                        step = 1,
                        placeId = 1L,
                        courseStage = CourseStage.PLACE_FINISH,
                        placeCategory = PlaceCategory.CAFE,
                        visitedStatus = true,
                        groupId = 1L,
                    ),
                    Course(
                        courseId = 4L,
                        step = 1,
                        placeId = 1L,
                        courseStage = CourseStage.PLACE_FINISH,
                        placeCategory = PlaceCategory.CAFE,
                        visitedStatus = true,
                        groupId = 1L,
                    )
                )

                shouldThrow<ContentException> {
                    sut.writeReview(
                        user = user,
                        reviewCreationRequest = reviewCreationRequest,
                    )
                }
            }
        }


        `when`("요청한 코스가 리뷰 작성이 가능하다면") {
            val reviewCreationRequest = ReviewCreationRequest(
                reviewGroupId = 1L,
                courseId = 1L,
                content = "reviewA"
            )

            val review = Review(
                reviewId = 1L,
                reviewGroupId = 1L,
                courseId = 1L,
                content = "reviewA"
            )

            val user = User(
                userId = 1L,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "nickName",
            )

            every { reviewQueryPort.getReviewByCourseId(any()) } returns review

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

            every { reviewWriteUseCase.writeReview(any()) } returns review
            every { reviewImageMetaCommandUseCase.upsertReviewImageMeta(any(), any()) } returns emptyList()

            then("예외 발생 없이 정상 실행 되어야 해요") {
                sut.writeReview(
                    user = user,
                    reviewCreationRequest = reviewCreationRequest,
                )

                verify(exactly = 1) { reviewWriteUseCase.writeReview(any()) }
            }
        }


        `when`("요청한 코스가 리뷰 작성이 가능하다면") {
            val reviewCreationRequest = ReviewCreationRequest(
                reviewGroupId = 1L,
                courseId = 1L,
                content = "reviewA"
            )

            val review = Review(
                reviewId = 1L,
                reviewGroupId = 1L,
                courseId = 1L,
                content = "reviewA"
            )

            val user = User(
                userId = 1L,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "nickName",
            )

            every { reviewQueryPort.getReviewByCourseId(any()) } returns review

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

            every { reviewWriteUseCase.writeReview(any()) } returns review
            every { reviewImageMetaCommandUseCase.upsertReviewImageMeta(any(), any()) } returns emptyList()

            then("예외 발생 없이 정상 실행 되어야 해요") {
                sut.writeReview(
                    user = user,
                    reviewCreationRequest = reviewCreationRequest,
                )

                verify(exactly = 1) { reviewWriteUseCase.writeReview(any()) }
            }
        }

        `when`("리뷰 이미지 메타 정보 저장 요청을 수행하면") {
            every { reviewImageMetaCommandUseCase.upsertReviewImageMeta(any(), any()) } returns emptyList()

            then("예외 발생 없이 정상 실행 되어야 해요") {
                sut.saveReviewImageMetas(
                    reviewImages = reviewImages,
                    reviewImageMetas = reviewImageMetas,
                )

                verify(exactly = 1) { reviewImageMetaCommandUseCase.upsertReviewImageMeta(any(), any()) }
            }
        }
    }

})
