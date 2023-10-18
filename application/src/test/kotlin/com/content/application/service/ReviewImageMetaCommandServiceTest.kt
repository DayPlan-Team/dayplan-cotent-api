package com.content.application.service

import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMeta
import com.content.domain.review.port.ReviewImageMetaCommandPort
import com.content.domain.review.port.ReviewImageMetaQueryPort
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

class ReviewImageMetaCommandServiceTest(
    private val reviewImageMetaCommandPort: ReviewImageMetaCommandPort = mockk(),
    private val reviewImageMetaQueryPort: ReviewImageMetaQueryPort = mockk(),
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val sut = ReviewImageMetaCommandService(
        reviewImageMetaCommandPort = reviewImageMetaCommandPort,
        reviewImageMetaQueryPort = reviewImageMetaQueryPort,
    )

    given("reviewImages 주어져요") {
        val reviewImageA = ReviewImage(
            image = "hello".toByteArray()
        )

        val reviewImageB = ReviewImage(
            image = "world".toByteArray()
        )

        `when`("reviewId == 0L인 메타 정보가 주어질 떄") {

            val reviewImageMetaA = ReviewImageMeta(
                sequence = 1,
                reviewId = 0L,
                originalName = "image_123.png",
                reviewImageHashCode = reviewImageA.hashCode(),
                reviewImageId = 0L,
            )

            val reviewImageMetaB = ReviewImageMeta(
                sequence = 2,
                reviewId = 0L,
                originalName = "image_124.png",
                reviewImageHashCode = reviewImageB.hashCode(),
                reviewImageId = 0L,
            )

            every { reviewImageMetaCommandPort.upsertReviewImageMetas(any()) } just Runs

            sut.upsertReviewImageMeta(
                reviewImages = listOf(
                    reviewImageA,
                    reviewImageB
                ),
                reviewImageMetas = listOf(
                    reviewImageMetaA,
                    reviewImageMetaB,
                )
            )

            then("reviewImageMeta를 검색하는 과정과, 메타정보를 삭제하는 과정은 수행되면 안돼요.") {
                verify(exactly = 0) { reviewImageMetaQueryPort.getReviewImageMetasByReviewId(any()) }
                verify(exactly = 0) { reviewImageMetaCommandPort.deleteReviewImageMetas(any()) }
            }
        }
    }

    given("reviewImages와 reviewId가 != 0L인 데이터가 주어져요") {
        val reviewImageA = ReviewImage(
            image = "hello".toByteArray()
        )

        val reviewImageB = ReviewImage(
            image = "world".toByteArray()
        )

        val reviewImageMetaA = ReviewImageMeta(
            sequence = 1,
            reviewId = 1L,
            originalName = "image_123.png",
            reviewImageHashCode = reviewImageA.hashCode(),
            reviewImageId = 0L,
        )

        val reviewImageMetaB = ReviewImageMeta(
            sequence = 2,
            reviewId = 1L,
            originalName = "image_124.png",
            reviewImageHashCode = reviewImageB.hashCode(),
            reviewImageId = 0L,
        )

        `when`("리뷰 이미지 메타 정보가 동일하면,") {

            every { reviewImageMetaQueryPort.getReviewImageMetasByReviewId(any()) } returns listOf(
                reviewImageMetaA,
                reviewImageMetaB,
            )

            sut.upsertReviewImageMeta(
                reviewImages = listOf(
                    reviewImageA,
                    reviewImageB
                ),
                reviewImageMetas = listOf(
                    reviewImageMetaA,
                    reviewImageMetaB,
                )
            )

            then("reviewImageMeta 저장되지 않아요") {
                verify(exactly = 0) { reviewImageMetaCommandPort.deleteReviewImageMetas(any()) }
                verify(exactly = 0) { reviewImageMetaCommandPort.upsertReviewImageMetas(any()) }
            }
        }

        `when`("리뷰 이미지 메타 정보가 다르면,") {

            every { reviewImageMetaQueryPort.getReviewImageMetasByReviewId(any()) } returns listOf(
                reviewImageMetaA,
                reviewImageMetaA,
            )

            every { reviewImageMetaCommandPort.deleteReviewImageMetas(any()) } just Runs
            every { reviewImageMetaCommandPort.upsertReviewImageMetas(any()) } just Runs

            sut.upsertReviewImageMeta(
                reviewImages = listOf(
                    reviewImageA,
                    reviewImageB
                ),
                reviewImageMetas = listOf(
                    reviewImageMetaA,
                    reviewImageMetaB,
                )
            )

            then("reviewImageMeta는 업데이트 되어야 해요") {
                verify(exactly = 1) { reviewImageMetaCommandPort.deleteReviewImageMetas(any()) }
                verify(exactly = 1) { reviewImageMetaCommandPort.upsertReviewImageMetas(any()) }
            }
        }
    }

})
