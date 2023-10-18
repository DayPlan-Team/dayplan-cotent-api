package com.content.domain.review

import java.util.*

data class ReviewImageMeta(
    val sequence: Int,
    val reviewId: Long,
    val originalName: String,
    val reviewImageHashCode: Int,
    val reviewImageId: Long,
    val imageName: String = "${RENAME_DEFAULT}-${UUID.randomUUID()}.${originalName.parseExtension()}",
    val imageUrl: String = "/$reviewId/$sequence/$imageName",
) {
    companion object {
        const val RENAME_DEFAULT = "image"
    }
}