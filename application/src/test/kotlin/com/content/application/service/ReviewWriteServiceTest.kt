package com.content.application.service

import com.content.domain.course.port.CourseQueryPort
import com.content.domain.review.port.ReviewGroupQueryPort
import com.content.domain.review.port.ReviewQueryPort
import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.review.CourseWithPossibleReview
import com.content.domain.review.Review
import com.content.domain.review.ReviewGroup
import com.content.domain.review.port.ReviewCommandPort
import com.content.domain.share.PlaceCategory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class ReviewWriteServiceTest(
    private val reviewGroupQueryPort: ReviewGroupQueryPort = mockk(),
    private val reviewQueryPort: ReviewQueryPort = mockk(),
    private val courseQueryPort: CourseQueryPort = mockk(),
    private val reviewCommandPort: ReviewCommandPort = mockk(),
) : BehaviorSpec({

    val sut = ReviewWriteService(
        reviewGroupQueryPort = reviewGroupQueryPort,
        reviewQueryPort = reviewQueryPort,
        courseQueryPort = courseQueryPort,
        reviewCommandPort = reviewCommandPort,
    )

    val userId = 1L
    val reviewGroupId = 1L

    given("리뷰 그룹 및 정상적인 코스 목록이 주어져요") {

        val courseA = Course(
            courseId = 1L,
            userId = userId,
            step = 1,
            placeId = 1L,
            courseStage = CourseStage.PLACE_FINISH,
            placeCategory = PlaceCategory.CAFE,
            visitedStatus = true,
            groupId = 1L,
        )

        val courseB = Course(
            courseId = 2L,
            userId = userId,
            step = 2,
            placeId = 1L,
            courseStage = CourseStage.PLACE_FINISH,
            placeCategory = PlaceCategory.CAFE,
            visitedStatus = true,
            groupId = 1L,
        )

        val reviewGroup = ReviewGroup(
            courseGroupId = 1L,
            userId = userId,
            reviewGroupId = reviewGroupId,
            reviewGroupName = "ReviewA",
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now(),
        )

        every { reviewGroupQueryPort.getReviewGroupById(any()) } returns reviewGroup

        every { courseQueryPort.getCoursesByGroupId(any()) } returns listOf(courseA, courseB)

        `when`("빈 리뷰 목록이 주어지면") {
            every { reviewQueryPort.getReviewsByCourseIds(any()) } returns emptyList()

            val result = sut.getAllPossibleReviewToWrite(
                userId = userId,
                reviewGroupId = reviewGroupId,
            )

            then("기본 목록으로 주어져요") {
                result.size shouldBe 2

                result[0] shouldBe CourseWithPossibleReview.of(courseA)
                result[1] shouldBe CourseWithPossibleReview.of(courseB)
            }
        }

        `when`("courseId = 2L는 리뷰 작성한 목록이 주어지면") {
            val review = Review(
                reviewId = 1L,
                userId = 1L,
                reviewGroupId = 1L,
                courseId = 2L,
                title = "ReviewA",
                content = "contentA",
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )
            every { reviewQueryPort.getReviewsByCourseIds(any()) } returns listOf(review)

            val result = sut.getAllPossibleReviewToWrite(
                userId = userId,
                reviewGroupId = reviewGroupId,
            )

            then("courseId = 2는 리뷰가 있는 목록으로 주어져요") {
                result.size shouldBe 2

                result[0] shouldBe CourseWithPossibleReview.of(courseA)
                result[1] shouldBe CourseWithPossibleReview.of(course = courseB, review = review)
            }
        }

        `when`("모두 리뷰 작성한 목록이 주어지면") {
            val reviewA = Review(
                reviewId = 1L,
                userId = 1L,
                reviewGroupId = 1L,
                courseId = 1L,
                title = "ReviewA",
                content = "contentB",
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )

            val reviewB = Review(
                reviewId = 2L,
                userId = 1L,
                reviewGroupId = 1L,
                courseId = 2L,
                title = "ReviewB",
                content = "contentB",
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )

            every { reviewQueryPort.getReviewsByCourseIds(any()) } returns listOf(reviewA, reviewB)

            val result = sut.getAllPossibleReviewToWrite(
                userId = userId,
                reviewGroupId = reviewGroupId,
            )

            then("courseId = 2는 리뷰가 있는 목록으로 주어져요") {
                result.size shouldBe 2

                result[0] shouldBe CourseWithPossibleReview.of(course = courseA, review = reviewA)
                result[1] shouldBe CourseWithPossibleReview.of(course = courseB, review = reviewB)
            }
        }
    }
})
