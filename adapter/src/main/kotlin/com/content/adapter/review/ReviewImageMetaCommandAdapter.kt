package com.content.adapter.review

import com.content.adapter.review.entity.ReviewImageMetaEntity
import com.content.adapter.review.persistence.ReviewImageEntityRepository
import com.content.domain.review.port.ReviewImageMataCommandPort
import com.content.domain.review.ReviewImageMeta
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ReviewImageMetaCommandAdapter(
    private val reviewImageEntityRepository: ReviewImageEntityRepository,
) : ReviewImageMataCommandPort {
    override fun upsertReviewImageMeta(reviewImageMeta: ReviewImageMeta): ReviewImageMeta {
        return reviewImageEntityRepository.save(ReviewImageMetaEntity.from(reviewImageMeta))
            .toDomainModel()
    }

    override fun upsertReviewImageMetas(reviewImageMetas: List<ReviewImageMeta>): List<ReviewImageMeta> {
        return reviewImageEntityRepository.saveAll(
            reviewImageMetas.map { ReviewImageMetaEntity.from(it) }
        ).map { it.toDomainModel() }
    }
}