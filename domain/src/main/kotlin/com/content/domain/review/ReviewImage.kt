package com.content.domain.review

data class ReviewImage(
    val image: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReviewImage

        return image.contentEquals(other.image)
    }

    override fun hashCode(): Int {
        return image.contentHashCode()
    }
}