package com.content.domain.review

data class ReviewImageMeta(
    val sequence: Int,
    val imageUrl: String,
    val reviewId: Long,
    val originalName: String,
    val rename: String = "${RENAME_DEFAULT}_${reviewId}_${sequence}",
    val reviewImageId: Long,
) {
    companion object {
        const val RENAME_DEFAULT = "image"
    }
}