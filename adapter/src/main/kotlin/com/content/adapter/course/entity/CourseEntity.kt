package com.content.adapter.course.entity

import com.content.adapter.share.BaseEntity
import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.share.PlaceCategory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(
    name = "course",
    indexes = [
        Index(name = "idx_course_groupId", columnList = "groupId"),
        Index(name = "idx_course_userId", columnList = "userId"),
    ]
)
data class CourseEntity(

    @Column
    val groupId: Long,

    @Column
    val userId: Long,

    @Column
    val step: Int,

    @Column
    @Enumerated(value = EnumType.STRING)
    val courseStage: CourseStage,

    @Column
    @Enumerated(value = EnumType.STRING)
    val placeCategory: PlaceCategory,

    @Column
    val placeId: Long,

    @Column
    val visitedStatus: Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {
    fun toDomainModel(): Course {
        return Course(
            courseId = id,
            groupId = groupId,
            userId = userId,
            step = step,
            placeId = placeId,
            placeCategory = placeCategory,
            visitedStatus = visitedStatus,
            courseStage = courseStage,
        )
    }

    companion object {
        fun fromCourse(course: Course): CourseEntity {
            return CourseEntity(
                groupId = course.groupId,
                userId = course.userId,
                step = course.step,
                placeId = course.placeId,
                placeCategory = course.placeCategory,
                visitedStatus = course.visitedStatus,
                courseStage = course.courseStage,
                id = course.courseId,
            )
        }
    }
}