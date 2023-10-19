package com.content.domain.review

import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ReviewOwnerVerifierTest : FunSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    context("reviewGroupUserId, userId가 다르게 주어져요") {
        val reviewGroupUserId = 1L
        val userId = 2L

        test("에러가 발생해요") {
            shouldThrow<ContentException> {
                ReviewOwnerVerifier.verifyReviewOwner(reviewGroupUserId, userId)
            }.errorCode shouldBe ContentExceptionCode.USER_INVALID.errorCode
        }
    }

    context("reviewGroupUserId, userId가 동일하게 주어져요") {
        val reviewGroupUserId = 1L
        val userId = 1L

        test("에러가 발생하지 않아요") {
            shouldNotThrow<ContentException> {
                ReviewOwnerVerifier.verifyReviewOwner(reviewGroupUserId, userId)
            }
        }
    }
})
