package com.content.domain.review

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.share.PlaceCategory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class PossibleReviewCourseGroupFinderTest : BehaviorSpec({

    given("그룹에 대한 코스들이 주어져요") {

        val courses = listOf(
            Course(
                courseId = 1L,
                userId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = true,
                groupId = 1L,
            ),
            Course(
                courseId = 2L,
                userId = 1L,
                step = 2,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = true,
                groupId = 1L,
            ),
            /* 거점: courseStage not Place Finish */
            Course(
                courseId = 3L,
                userId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.START,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = true,
                groupId = 2L,
            ),
            Course(
                courseId = 2L,
                userId = 1L,
                step = 2,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = true,
                groupId = 2L,
            ),
            /* 거점: VisitedStatus */
            Course(
                courseId = 5L,
                userId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = true,
                groupId = 3L,
            ),
            Course(
                courseId = 6L,
                userId = 1L,
                step = 2,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 3L,
            ),
            Course(
                courseId = 7L,
                userId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 4L,
            ),
            Course(
                courseId = 8L,
                userId = 1L,
                step = 2,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 4L,
            )
        )

        `when`("리뷰 가능한 코스를 확인하면") {
            val result = PossibleReviewCourseGroupFinder.processPossibleReviewCourseGroup(courses)

            then("courseGroupId = 1L 만 리턴되어야 해요") {
                result.size shouldBe 1
                result[0] shouldBe 1L
            }
        }

    }

})