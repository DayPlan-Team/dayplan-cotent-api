package com.content.domain.review

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.share.PlaceCategory
import com.content.util.exception.ContentException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class PossibleReviewVerifierTest : BehaviorSpec({

    given("유저 아이디가 주어져요") {
        val userId = 1L

        `when`("모두 방문, 완성된 코스가 주어지면") {
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

            then("검증을 통과해요") {
                shouldNotThrow<ContentException> {
                    PossibleReviewVerifier.verifyPossibleReviewCourses(listOf(courseA, courseB))
                }
            }
        }

        `when`("하나라도 방문 하지 않고, 완성된 코스가 주어지면") {
            val courseA = Course(
                courseId = 1L,
                userId = userId,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
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

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    PossibleReviewVerifier.verifyPossibleReviewCourses(listOf(courseA, courseB))
                }
            }
        }

        `when`("모두 방문 하지 않고, 완성된 코스가 주어지면") {
            val courseA = Course(
                courseId = 1L,
                userId = userId,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            val courseB = Course(
                courseId = 2L,
                userId = userId,
                step = 2,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    PossibleReviewVerifier.verifyPossibleReviewCourses(listOf(courseA, courseB))
                }
            }
        }

        `when`("모두 방문 하지 않고, 하나라도 완성되지 않은 코스가 주어지면") {
            val courseA = Course(
                courseId = 1L,
                userId = userId,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            val courseB = Course(
                courseId = 2L,
                userId = userId,
                step = 2,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    PossibleReviewVerifier.verifyPossibleReviewCourses(listOf(courseA, courseB))
                }
            }
        }

        `when`("모두 방문 하지 않고, 모두 완성되지 않은 코스가 주어지면") {
            val courseA = Course(
                courseId = 1L,
                userId = userId,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            val courseB = Course(
                courseId = 2L,
                userId = userId,
                step = 2,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    PossibleReviewVerifier.verifyPossibleReviewCourses(listOf(courseA, courseB))
                }
            }
        }

        `when`("하나만 방문 하지 않고, 하나만 완성되지 않은 코스가 주어지면") {
            val courseA = Course(
                courseId = 1L,
                userId = userId,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
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

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    PossibleReviewVerifier.verifyPossibleReviewCourses(listOf(courseA, courseB))
                }
            }
        }

        `when`("하나만 방문 하지 않고, 하나만 완성되지 않은 코스가 주어지면") {
            val courseA = Course(
                courseId = 1L,
                userId = userId,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
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
                visitedStatus = false,
                groupId = 1L,
            )

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    PossibleReviewVerifier.verifyPossibleReviewCourses(listOf(courseA, courseB))
                }
            }
        }
    }
})
