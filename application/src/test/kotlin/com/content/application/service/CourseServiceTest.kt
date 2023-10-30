package com.content.application.service

import com.content.domain.course.port.CourseCommandPort
import com.content.domain.course.port.CourseQueryPort
import com.content.application.port.PlacePort
import com.content.domain.course.CourseUpsertRequest
import com.content.domain.course.Course
import com.content.domain.course.CourseGroup
import com.content.domain.course.CourseStage
import com.content.domain.course.port.CourseGroupQueryPort
import com.content.domain.place.Place
import com.content.domain.review.Review
import com.content.domain.review.port.ReviewQueryPort
import com.content.domain.share.PlaceCategory
import com.content.util.address.CityCode
import com.content.util.address.DistrictCode
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
    private val courseGroupQueryPort: CourseGroupQueryPort = mockk(),
    private val reviewQueryPort: ReviewQueryPort = mockk(),
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val sut = CourseService(
        courseQueryPort = courseQueryPort,
        courseCommandPort = courseCommandPort,
        placePort = placePort,
        courseGroupQueryPort = courseGroupQueryPort,
        reviewQueryPort = reviewQueryPort,
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

        every { reviewQueryPort.getReviewsByCourseGroupId(any()) } returns emptyList()

        `when`("코스 저장 요청을 수행하면") {
            every { courseGroupQueryPort.getCourseGroupById(any()) } returns CourseGroup(
                userId = 1L,
                groupId = 1L,
                groupName = "그룹",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
            )
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

        every { reviewQueryPort.getReviewsByCourseGroupId(any()) } returns emptyList()

        `when`("코스 저장 요청을 수행하면") {
            every { courseGroupQueryPort.getCourseGroupById(any()) } returns CourseGroup(
                userId = 1L,
                groupId = 1L,
                groupName = "그룹",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
            )

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

        every { reviewQueryPort.getReviewsByCourseGroupId(any()) } returns emptyList()

        `when`("코스를 만든 유저와 동일한 userId면") {
            every { courseGroupQueryPort.getCourseGroupById(any()) } returns CourseGroup(
                userId = 1L,
                groupId = 1L,
                groupName = "그룹",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
            )

            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            sut.upsertCourse(courseUpsertRequest)

            then("코스가 정상 저장되어야 해요") {
                verify(exactly = 0) { placePort.getPlaceByPlaceId(any()) }
                verify(exactly = 1) { courseCommandPort.upsertCourse(any()) }
            }
        }

        `when`("코스를 만든 유저와 userId가 다르면") {
            every { courseGroupQueryPort.getCourseGroupById(any()) } returns CourseGroup(
                userId = 2L,
                groupId = 1L,
                groupName = "그룹",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
            )

            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
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

            every { reviewQueryPort.getReviewsByCourseGroupId(any()) } returns emptyList()

            every { courseGroupQueryPort.getCourseGroupById(any()) } returns CourseGroup(
                userId = 1L,
                groupId = 1L,
                groupName = "그룹",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
            )

            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
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
                verify(exactly = 1) { placePort.getPlaceByPlaceId(any()) }
                verify(exactly = 1) { courseCommandPort.upsertCourse(any()) }
            }
        }

        `when`("코스를 만든 유저와 동일한 userId고, place 정보가 존재하지만, 이미 리뷰가 작성되었어요") {

            every { reviewQueryPort.getReviewsByCourseGroupId(any()) } returns listOf(
                Review(
                    reviewId = 1L,
                    reviewGroupId = 1L,
                    courseId = 1L,
                    content = "리뷰를 작성해요!",
                )
            )

            every { courseGroupQueryPort.getCourseGroupById(any()) } returns CourseGroup(
                userId = 1L,
                groupId = 1L,
                groupName = "그룹",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
            )

            then("이미 리뷰가 작성된 코스는 코스 수정이 불가해요!") {
                shouldThrow<ContentException> {
                    sut.upsertCourse(courseUpsertRequest)
                }
            }
        }

        `when`("코스를 만든 유저와 userId가 다르면") {

            every { reviewQueryPort.getReviewsByCourseGroupId(any()) } returns emptyList()

            every { courseGroupQueryPort.getCourseGroupById(any()) } returns CourseGroup(
                userId = 2L,
                groupId = 1L,
                groupName = "그룹",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
            )

            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
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

            every { reviewQueryPort.getReviewsByCourseGroupId(any()) } returns emptyList()

            every { courseQueryPort.getCourseById(any()) } returns Course(
                courseId = 1L,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.CATEGORY_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1L,
            )

            every { courseGroupQueryPort.getCourseGroupById(any()) } returns CourseGroup(
                userId = 1L,
                groupId = 1L,
                groupName = "그룹",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
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