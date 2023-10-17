package com.content.application.service

import com.content.domain.course.port.CourseCommandPort
import com.content.domain.course.port.CourseQueryPort
import com.content.application.port.PlacePort
import com.content.application.request.CourseUpsertRequest
import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.place.Place
import com.content.domain.share.PlaceCategory
import com.content.util.exception.ContentException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

class CourseServiceTest(
    private val courseQueryPort: CourseQueryPort = mockk(),
    private val courseCommandPort: CourseCommandPort = mockk(),
    private val placePort: PlacePort = mockk(),
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerTest

    val sut = CourseService(
        courseQueryPort = courseQueryPort,
        courseCommandPort = courseCommandPort,
        placePort = placePort,
    )

    given("courseId = 0, placeId = 0인 저장 요청이 주어져요") {
        val courseUpsertRequest = CourseUpsertRequest(
            userId = 1L,
            courseId = 0L,
            groupId = 1L,
            step = 1,
            placeCategory = PlaceCategory.CAFE,
            placeId = 0L,
        )

        `when`("코스 저장 요청을 수행하면") {
            every { courseCommandPort.upsertCourse(any()) } just Runs

            sut.upsertCourse(courseUpsertRequest)

            then("코스 검색 요청, 장소 검색 요청은 호출되지 않아야 해요") {
                verify(exactly = 0) { courseQueryPort.getCourseById(any()) }
                verify(exactly = 0) { placePort.getPlaceByPlaceId(any()) }
                verify(exactly = 1) { courseCommandPort.upsertCourse(any()) }
            }
        }
    }

    given("courseId = 0, placeId = 1인 저장 요청이 주어져요") {
        val courseUpsertRequest = CourseUpsertRequest(
            userId = 1L,
            courseId = 0L,
            groupId = 1L,
            step = 1,
            placeCategory = PlaceCategory.CAFE,
            placeId = 1L,
        )

        `when`("코스 저장 요청을 수행하면") {
            every { courseCommandPort.upsertCourse(any()) } just Runs

            then("에러가 발생해야 해요") {
                shouldThrow<ContentException> {
                    sut.upsertCourse(courseUpsertRequest)
                }
            }
        }
    }

    given("courseId = 1, placeId = 0인 저장 요청이 주어져요") {
        val courseUpsertRequest = CourseUpsertRequest(
            userId = 1L,
            courseId = 1L,
            groupId = 1L,
            step = 1,
            placeCategory = PlaceCategory.CAFE,
            placeId = 0L,
        )
        every { courseCommandPort.upsertCourse(any()) } just Runs

        `when`("코스를 만든 유저와 동일한 userId면") {
            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
                userId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            sut.upsertCourse(courseUpsertRequest)

            then("코스가 정상 저장되어야 해요") {
                verify(exactly = 1) { courseQueryPort.getCourseById(any()) }
                verify(exactly = 0) { placePort.getPlaceByPlaceId(any()) }
                verify(exactly = 1) { courseCommandPort.upsertCourse(any()) }
            }
        }

        `when`("코스를 만든 유저와 userId가 다르면") {
            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
                userId = 2L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    sut.upsertCourse(courseUpsertRequest)
                }
            }
        }
    }

    given("courseId = 1, placeId = 1인 저장 요청이 주어져요") {
        val courseUpsertRequest = CourseUpsertRequest(
            userId = 1L,
            courseId = 1L,
            groupId = 1L,
            step = 1,
            placeCategory = PlaceCategory.CAFE,
            placeId = 1L,
        )
        every { courseCommandPort.upsertCourse(any()) } just Runs

        `when`("코스를 만든 유저와 동일한 userId고, place 정보가 존재해요") {
            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
                userId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            every { placePort.getPlaceByPlaceId(any()) } returns listOf(
                Place(
                    placeName = "",
                    placeCategory = PlaceCategory.CAFE,
                    latitude = 100.00,
                    longitude = 100.00,
                    address = "중랑구",
                    roadAddress = "중랑구",
                    placeId = 1L,
                )
            )

            sut.upsertCourse(courseUpsertRequest)

            then("코스가 정상 저장되어야 해요") {
                verify(exactly = 1) { courseQueryPort.getCourseById(any()) }
                verify(exactly = 1) { placePort.getPlaceByPlaceId(any()) }
                verify(exactly = 1) { courseCommandPort.upsertCourse(any()) }
            }
        }

        `when`("코스를 만든 유저와 userId가 다르면") {
            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
                userId = 2L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    sut.upsertCourse(courseUpsertRequest)
                }
            }
        }

        `when`("코스를 만든 유저와 userId같은데, 플레이스 정보가 비어있으면") {
            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
                userId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            every { placePort.getPlaceByPlaceId(any()) } returns emptyList()

            then("예외가 발생해요") {
                shouldThrow<ContentException> {
                    sut.upsertCourse(courseUpsertRequest)
                }
            }
        }
    }
})