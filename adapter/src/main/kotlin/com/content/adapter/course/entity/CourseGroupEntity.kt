package com.content.adapter.course.entity

import com.content.adapter.share.BaseEntity
import com.content.domain.course.CourseGroup
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(
    name = "course_group",
    indexes = [
        Index(name = "idx_userId", columnList = "userId"),
    ]
)
data class CourseGroupEntity(
    @Column
    val userId: Long,

    @Column
    val groupName: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {

    fun toCourseGroup(): CourseGroup {
        return CourseGroup(
            groupId = id,
            userId = userId,
            groupName = groupName,
        )
    }

    companion object {
        fun fromCourseGroup(courseGroup: CourseGroup): CourseGroupEntity {
            return CourseGroupEntity(
                userId = courseGroup.userId,
                groupName = courseGroup.groupName,
                id = courseGroup.groupId,
            )
        }
    }

}
