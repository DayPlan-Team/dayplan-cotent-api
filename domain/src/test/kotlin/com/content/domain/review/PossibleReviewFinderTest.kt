package com.content.domain.review

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.share.PlaceCategory
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PossibleReviewFinderTest : FunSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    context("방문한 코스와 방문하지 않은 course 목록이 주어져요") {
        val courseA = Course(
            courseId = 1L,
            step = 1,
            placeId = 1L,
            courseStage = CourseStage.PLACE_FINISH,
            placeCategory = PlaceCategory.CAFE,
            visitedStatus = true,
            groupId = 1L,
        )

        val courseB = Course(
            courseId = 2L,
            step = 2,
            placeId = 1L,
            courseStage = CourseStage.PLACE_FINISH,
            placeCategory = PlaceCategory.CAFE,
            visitedStatus = true,
            groupId = 1L,
        )

        val courseC = Course(
            courseId = 3L,
            step = 1,
            placeId = 1L,
            courseStage = CourseStage.PLACE_FINISH,
            placeCategory = PlaceCategory.CAFE,
            visitedStatus = true,
            groupId = 2L,
        )

        val courseD = Course(
            courseId = 4L,
            step = 2,
            placeId = 1L,
            courseStage = CourseStage.PLACE_FINISH,
            placeCategory = PlaceCategory.CAFE,
            visitedStatus = false,
            groupId = 2L,
        )

        val result = PossibleReviewFinder.processPossibleReviewCourseGroup(
            courses = listOf(
                courseA,
                courseB,
                courseC,
                courseD,
            )
        )

        test("가능한 코스 그룹은 1개이고, 결과는 1이에요") {
            result.size shouldBe 1
            result[0] shouldBe 1L
        }
    }

    context("방문하지 않은 course 목록이 주어져요") {
        val courseA = Course(
            courseId = 1L,
            step = 1,
            placeId = 1L,
            courseStage = CourseStage.PLACE_FINISH,
            placeCategory = PlaceCategory.CAFE,
            visitedStatus = false,
            groupId = 1L,
        )

        val courseB = Course(
            courseId = 2L,
            step = 2,
            placeId = 1L,
            courseStage = CourseStage.PLACE_FINISH,
            placeCategory = PlaceCategory.CAFE,
            visitedStatus = false,
            groupId = 2L,
        )

        val result = PossibleReviewFinder.processPossibleReviewCourseGroup(
            courses = listOf(
                courseA,
                courseB,
            )
        )

        test("가능한 코스 그룹은 없어요") {
            result.size shouldBe 0
        }
    }

})
