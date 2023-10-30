package com.content.adapter.review

import com.content.adapter.review.entity.ReviewEntity
import com.content.adapter.review.persistence.ReviewEntityRepository
import com.content.domain.review.port.ReviewCommandPort
import com.content.domain.review.Review
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ReviewCommandAdapter(
    private val reviewEntityRepository: ReviewEntityRepository,
) : ReviewCommandPort {
    override fun upsertReview(review: Review): Review {
        return reviewEntityRepository
            .save(ReviewEntity.from(review))
            .toDomainModel()
    }
}