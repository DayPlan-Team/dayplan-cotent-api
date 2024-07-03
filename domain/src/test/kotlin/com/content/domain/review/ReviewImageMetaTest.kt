package com.content.domain.review

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ReviewImageMetaTest : FunSpec({

    context("ReviewImageMeta가 주어져요") {
        val reviewImageMeta =
            ReviewImageMeta(
                sequence = 2,
                reviewId = 1L,
                originalName = "image_124.png",
                reviewImageHashCode = "Hello".hashCode(),
                reviewImageId = 0L,
            )

        test("ReviewImageMeta를 검증해요") {
            println(reviewImageMeta.imageName)
            reviewImageMeta.imageUrl shouldBe "/${reviewImageMeta.reviewId}/${reviewImageMeta.sequence}/${reviewImageMeta.imageName}"
            reviewImageMeta.imageName.split(".").size shouldBe 2
        }
    }
})
