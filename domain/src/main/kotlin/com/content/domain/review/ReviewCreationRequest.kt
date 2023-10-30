package com.content.domain.review

data class ReviewCreationRequest(
    val reviewGroupId: Long,
    val courseId: Long,
    val content: String,
) {

    fun toReview(
        review: Review? = null,
    ): Review {
        return Review.from(
            reviewId = review?.reviewId ?: 0L,
            reviewGroupId = review?.reviewGroupId ?: reviewGroupId,
            courseId = review?.courseId ?: courseId,
            content = content,
        )
    }

    companion object {

        fun from(
            reviewGroupId: Long,
            courseId: Long,
            content: String
        ): ReviewCreationRequest {
            return ReviewCreationRequest(
                reviewGroupId = reviewGroupId,
                courseId = courseId,
                content = content,
            )
        }

    }
}