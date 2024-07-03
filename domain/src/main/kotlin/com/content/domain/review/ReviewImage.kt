package com.content.domain.review

import java.security.MessageDigest

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
        return sha256Digest.digest(image).contentHashCode()
    }

    companion object {
        private const val SHA256 = "SHA-256"

        val sha256Digest: MessageDigest by lazy {
            MessageDigest.getInstance(SHA256)
        }
    }
}
