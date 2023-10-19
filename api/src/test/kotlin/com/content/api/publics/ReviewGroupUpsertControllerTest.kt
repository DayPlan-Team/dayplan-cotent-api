package com.content.api.publics

import com.content.application.service.CourseGroupService
import com.content.application.service.PossibleReviewCourseFindService
import com.content.application.service.UserVerifyService
import com.content.domain.course.CourseGroup
import com.content.domain.review.ReviewGroup
import com.content.domain.review.ReviewGroupCommandUseCase
import com.content.domain.user.User
import com.content.domain.user.UserAccountStatus
import com.ninjasquad.springmockk.MockkBean
import com.user.util.address.CityCode
import com.user.util.address.DistrictCode
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewGroupUpsertControllerTest : FunSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userVerifyService: UserVerifyService

    @MockkBean
    private lateinit var courseGroupService: CourseGroupService

    @MockkBean
    private lateinit var possibleReviewCourseFindService: PossibleReviewCourseFindService

    @MockkBean
    private lateinit var reviewGroupCommandUseCase: ReviewGroupCommandUseCase

    init {
        this.context("ReviewGroup 생성에 대한 mockk 스텁 결과가 주어져요") {
            val userId = 1L

            every { userVerifyService.verifyNormalUserAndGet(any()) } returns User(
                userId = userId,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "UserA"
            )

            every { courseGroupService.getCourseGroup(any(), any()) } returns CourseGroup(
                userId = userId,
                groupId = 1L,
                groupName = "A",
                cityCode = CityCode.SEOUL,
                districtCode = DistrictCode.SEOUL_DOBONG,
            )

            every { reviewGroupCommandUseCase.createReviewGroupOrGet(any()) } returns ReviewGroup(
                courseGroupId = userId,
                reviewGroupId = 1L,
                reviewGroupName = "ReviewA",
                userId = 1L,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )

            every { possibleReviewCourseFindService.verifyPossibleReviewCourseGroup(any()) } just Runs

            test("올바른 json 요청으로 리뷰 그룹 생성 응답을 검증을 성공해요") {
                val reviewCreateGroupApiRequest = "{\"courseGroupId\": 12345}"

                val mockHttpServletRequestBuilder =
                    MockMvcRequestBuilders.post("https://localhost:8078/content/reviewgroups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("UserId", userId)
                        .content(reviewCreateGroupApiRequest)

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk)
                    .andExpect(
                        jsonPath("$.reviewGroupId").value("1")
                    )
            }

            test("잘못된 json 요청으로 리뷰 그룹 생성 응답을 검증을 실패해요") {
                val request = "{\"courseGroupId2\": 12345}"

                val mockHttpServletRequestBuilder =
                    MockMvcRequestBuilders.post("https://localhost:8078/content/reviewgroups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("UserId", userId)
                        .content(request)

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().is4xxClientError)
            }
        }

        this.context("ReviewGroup 업데이트에 대한 mockk 스텁 결과가 주어져요") {
            val userId = 1L

            every { userVerifyService.verifyNormalUserAndGet(any()) } returns User(
                userId = userId,
                userAccountStatus = UserAccountStatus.NORMAL,
                nickName = "UserA"
            )

            every { reviewGroupCommandUseCase.updateReviewGroup(any(), any(), any()) } returns ReviewGroup(
                courseGroupId = userId,
                reviewGroupId = 1L,
                reviewGroupName = "ReviewA",
                userId = 1L,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )

            test("올바른 json 요청으로 리뷰 그룹 생성 응답을 검증을 성공해요") {
                val request = "{\"reviewGroupName\": \"ReviewA\"}"

                val mockHttpServletRequestBuilder =
                    MockMvcRequestBuilders.put("https://localhost:8078/content/reviewgroups/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("UserId", userId)
                        .content(request)

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk)
                    .andExpect(
                        jsonPath("$.reviewGroupId").value("1")
                    )
            }

            test("잘못된 json 요청으로 리뷰 그룹 생성 응답을 검증을 실패해요") {
                val request = "{\"reviewGroupName2\": \"ReviewA\"}"

                val mockHttpServletRequestBuilder =
                    MockMvcRequestBuilders.put("https://localhost:8078/content/reviewgroups/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("UserId", userId)
                        .content(request)

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().is4xxClientError)
            }
        }

    }
}
