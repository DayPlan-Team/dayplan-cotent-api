package com.content.adapter.review

import com.content.adapter.review.entity.ReviewImageMetaEntity
import com.content.adapter.review.persistence.ReviewImageEntityRepository
import com.content.domain.review.port.ReviewImageMetaCommandPort
import com.content.domain.review.ReviewImageMeta
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ReviewImageMetaCommandAdapter(
    private val reviewImageEntityRepository: ReviewImageEntityRepository,
) : ReviewImageMetaCommandPort {
    override fun upsertReviewImageMeta(reviewImageMeta: ReviewImageMeta): ReviewImageMeta {
        return reviewImageEntityRepository.save(ReviewImageMetaEntity.from(reviewImageMeta))
            .toDomainModel()
    }

    override fun upsertReviewImageMetas(reviewImageMetas: List<ReviewImageMeta>): List<ReviewImageMeta> {
        return reviewImageEntityRepository.saveAll(
            reviewImageMetas.map { ReviewImageMetaEntity.from(it) }
        ).map { it.toDomainModel() }
    }

    override fun deleteReviewImageMeta(reviewImageMeta: ReviewImageMeta) {
        reviewImageEntityRepository.save(
            ReviewImageMetaEntity.fromToDelete(reviewImageMeta)
        )
    }

    override fun deleteReviewImageMetas(reviewImageMetas: List<ReviewImageMeta>) {
        reviewImageEntityRepository.saveAll(
            reviewImageMetas.map {
                ReviewImageMetaEntity.fromToDelete(it)
            }
        )
    }
}