package com.content.domain.review

fun List<ReviewImageMeta>.isEqual(other: List<ReviewImageMeta>): Boolean {
    if (this.size != other.size) return false

    return this.zip(other).all { (a, b) ->
        a.reviewId == b.reviewId && a.sequence == b.sequence && a.reviewImageHashCode == b.reviewImageHashCode
    }
}

fun String.parseExtension(): String? {
    return this.split(".").takeIf { it.size > 1 }?.last()
}
