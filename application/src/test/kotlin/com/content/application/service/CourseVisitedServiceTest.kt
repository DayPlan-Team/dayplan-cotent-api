package com.content.application.service

import com.content.application.port.CourseCommandPort
import com.content.application.port.CourseQueryPort
import com.content.application.port.PlacePort
import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.course.LocationRectangleRangeCreator
import com.content.domain.location.UserLocation
import com.content.domain.place.Place
import com.content.domain.share.PlaceCategory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class CourseVisitedServiceTest(
    private val courseCommandPort: CourseCommandPort = mockk(),
    private val courseQueryPort: CourseQueryPort = mockk(),
    private val placePort: PlacePort = mockk(),
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
) : BehaviorSpec({

    val sut = CourseVisitedService(
        courseCommandPort = courseCommandPort,
        courseQueryPort = courseQueryPort,
        placePort = placePort,
        coroutineScope = coroutineScope,
    )

    given("유저가 등록한 코스와 가게 정보가 주어져요") {
        val userId = 1L
        val latitude = 3.0000
        val longitude = 4.0000
        val latitudeError = LocationRectangleRangeCreator.LATITUDE_ERROR
        val longitudeError = LocationRectangleRangeCreator.LONGITUDE_ERROR

        val courses = listOf(
            Course(
                courseId = 1L,
                userId = userId,
                step = 1,
                placeId = 1L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 1,
            ),
            Course(
                courseId = 2L,
                userId = userId,
                step = 1,
                placeId = 2L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 2,
            ),
            Course(
                courseId = 3L,
                userId = userId,
                step = 1,
                placeId = 3L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 3,
            ),
            Course(
                courseId = 4L,
                userId = userId,
                step = 1,
                placeId = 4L,
                courseStage = CourseStage.PLACE_FINISH,
                placeCategory = PlaceCategory.CAFE,
                visitedStatus = false,
                groupId = 4,
            ),
        )

        val places = listOf(
            /* 접점 */
            Place(
                placeName = "스타벅스",
                placeCategory = PlaceCategory.CAFE,
                latitude = latitude - latitudeError,
                longitude = longitude - longitudeError,
                address = "중랑구",
                roadAddress = "중랑구",
                placeId = 1L,
            ),
            /* 거점 */
            Place(
                placeName = "이디야",
                placeCategory = PlaceCategory.CAFE,
                latitude = latitude - latitudeError - 0.0001,
                longitude = longitude - longitudeError - 0.0001,
                address = "강남구",
                roadAddress = "강남구",
                placeId = 2L,
            ),
            /* 접점 */
            Place(
                placeName = "엔젤리너스",
                placeCategory = PlaceCategory.CAFE,
                latitude = latitude + latitudeError,
                longitude = longitude + longitudeError,
                address = "중랑구",
                roadAddress = "중랑구",
                placeId = 3L,
            ),
            /* 거점 */
            Place(
                placeName = "탐앤탐스",
                placeCategory = PlaceCategory.CAFE,
                latitude = latitude + latitudeError + 0.0001,
                longitude = longitude + longitudeError + 0.0001,
                address = "중랑구",
                roadAddress = "중랑구",
                placeId = 4L,
            ),
        )

        `when`("유저 위치 기반 유효 구간 경계와 밖에 있는 가게가 주어지면") {
            every { courseQueryPort.getCursesByUserIdAndVisitedStatus(any(), false) } returns courses
            coEvery { placePort.getSuspendPlaceByPlaceId(any()) } returns places

            val capturedCourses = mutableListOf<List<Course>>()
            every { courseCommandPort.upsertCourses(capture(capturedCourses)) } just Runs

            runBlocking {
                async {
                    sut.updateCourseVisitedStatus(
                        UserLocation(
                            userId = userId,
                            latitude = latitude,
                            longitude = longitude,
                        )
                    )
                }
            }

            then("유효 구간 경계 내에 있는 courseId = 1, 3만 방문 처리가 되어야 해요") {
                val updatedCourses = capturedCourses.flatten()
                updatedCourses.all { it.courseId in listOf(1L, 3L) } shouldBe true
            }
        }
    }

})