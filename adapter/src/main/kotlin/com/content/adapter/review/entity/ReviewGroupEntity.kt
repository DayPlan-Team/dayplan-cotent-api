package com.content.adapter.review.entity

import com.content.adapter.share.BaseEntity
import com.content.domain.course.CourseGroup
import com.content.domain.review.ReviewGroup
import com.content.util.share.DateTimeCustomFormatter
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "review_group")
data class ReviewGroupEntity(

    @Column
    val userId: Long,

    @Column
    val courseGroupId: Long,

    @Column
    val reviewGroupName: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {

    companion object {
        fun from(courseGroup: CourseGroup): ReviewGroupEntity {
            return ReviewGroupEntity(
                userId = courseGroup.userId,
                courseGroupId = courseGroup.groupId,
                reviewGroupName = "${ReviewGroup.DEFAULT_NAME}_${DateTimeCustomFormatter.nowToDefaultFormat()}"
            )
        }

        fun from(reviewGroup: ReviewGroup): ReviewGroupEntity {
            return ReviewGroupEntity(
                id = reviewGroup.reviewGroupId,
                userId = reviewGroup.userId,
                courseGroupId = reviewGroup.courseGroupId,
                reviewGroupName = reviewGroup.reviewGroupName,
            )
        }
    }

    fun toDomainModel(): ReviewGroup {
        return ReviewGroup(
            userId = userId,
            courseGroupId = courseGroupId,
            reviewGroupId = id,
            reviewGroupName = reviewGroupName,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )
    }

}