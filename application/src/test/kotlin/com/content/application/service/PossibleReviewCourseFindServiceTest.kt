package com.content.application.service

import com.content.domain.course.port.CourseGroupQueryPort
import com.content.domain.course.port.CourseQueryPort
import com.content.domain.course.Course
import com.content.domain.course.CourseGroup
import com.content.domain.course.CourseStage
import com.content.domain.share.PlaceCategory
import com.content.util.exception.ContentException
import com.user.util.address.CityCode
import com.user.util.address.DistrictCode
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

class PossibleReviewCourseFindServiceTest(
    private val courseQueryPort: CourseQueryPort = mockk(),
    private val courseGroupQueryPort: CourseGroupQueryPort = mockk(),
) : BehaviorSpec({

    val sut = PossibleReviewCourseFindService(
        courseQueryPort = courseQueryPort,
        courseGroupQueryPort = courseGroupQueryPort,
    )

    given("코스 그룹을 정의해요.") {

        val courseGroup = CourseGroup(
            userId = 1L,
            groupId = 1L,
            groupName = "test",
            cityCode = CityCode.SEOUL,
            districtCode = DistrictCode.SEOUL_DOBONG,
        )

        `when`("모든 곳을 전부는 방문하지 않은 경우가 주어져요") {

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
                    visitedStatus = false,
                    groupId = 1L,
                )
            )

            every { courseQueryPort.getCoursesByGroupId(any()) } returns courses

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    sut.verifyPossibleReviewCourseGroup(
                        courseGroup = courseGroup,
                    )
                }
            }
        }

        `when`("모든 곳은 방문했으나, 코스 상태가 완성되지 않은 경우") {

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
                    courseStage = CourseStage.START,
                    placeCategory = PlaceCategory.CAFE,
                    visitedStatus = true,
                    groupId = 1L,
                )
            )

            every { courseQueryPort.getCoursesByGroupId(any()) } returns courses

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    sut.verifyPossibleReviewCourseGroup(
                        courseGroup = courseGroup,
                    )
                }
            }
        }
    }

})