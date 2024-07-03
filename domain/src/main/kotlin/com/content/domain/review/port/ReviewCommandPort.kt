package com.content.domain.review.port

import com.content.domain.review.Review
import org.springframework.stereotype.Component

@Component
interface ReviewCommandPort {
    fun upsertReview(review: Review): Review
}
