package com.content.application.service

import com.content.adapter.review.entity.ReviewGroupEntity
import com.content.adapter.review.persistence.ReviewGroupEntityRepository
import com.content.application.ApplicationTestConfiguration
import com.content.domain.course.CourseGroup
import com.content.domain.review.ReviewGroupUpdateRequest
import com.content.util.address.CityCode
import com.content.util.address.DistrictCode
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@ActiveProfiles("test")
@SpringBootTest(classes = [ApplicationTestConfiguration::class])
class ReviewGroupCommandServiceTest(
    @Autowired private val reviewGroupCommandService: ReviewGroupCommandService,
    @Autowired private val reviewGroupEntityRepository: ReviewGroupEntityRepository,
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    afterTest {
        reviewGroupEntityRepository.deleteAll()
    }

    given("코스 그룹이 주어져요") {

        val courseGroup = CourseGroup(
            userId = 1L,
            groupId = 1L,
            groupName = "최고의 코스",
            cityCode = CityCode.SEOUL,
            districtCode = DistrictCode.SEOUL_DOBONG,
        )

        `when`("리뷰 가능한 코스로 리뷰 그룹을 생성해요") {
            val threadCount = 5
            val latch = CountDownLatch(threadCount)
            val executorService = Executors.newFixedThreadPool(32)

            repeat(threadCount) {
                executorService.submit {
                    try {
                        reviewGroupCommandService.createReviewGroupOrGet(courseGroup)
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()

            val result = reviewGroupEntityRepository.findAll().map { it.toDomainModel() }

            then("생성된 리뷰 그룹인 한개여야 해요") {
                result.size shouldBe 1L
            }
        }
    }

    given("리뷰 그룹이 주어져요") {

        val reviewGroupEntity = ReviewGroupEntity(
            userId = 1L,
            courseGroupId = 1L,
            reviewGroupName = "리뷰 그룹",
        )

        val reviewGroupId = reviewGroupEntityRepository.save(reviewGroupEntity).id

        val reviewGroupUpdateRequest = ReviewGroupUpdateRequest(
            reviewGroupName = "리뷰 그룹 수정"
        )


        `when`("리뷰 그룹의 변경 요청을 수행해요") {
            val threadCount = 5
            val latch = CountDownLatch(threadCount)
            val executorService = Executors.newFixedThreadPool(32)

            repeat(threadCount) {
                executorService.submit {
                    try {
                        reviewGroupCommandService.updateReviewGroup(
                            userId = 1L,
                            reviewGroupId = reviewGroupId,
                            reviewGroupUpdateRequest = reviewGroupUpdateRequest,
                        )
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()

            then("리뷰 그룹의 이름은 변경되어야 해요") {
                val result = reviewGroupEntityRepository.findById(reviewGroupId).orElseThrow().toDomainModel()
                result.reviewGroupName shouldBe "리뷰 그룹 수정"
            }
        }
    }

})