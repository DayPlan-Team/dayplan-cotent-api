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
    name = "courses",
    indexes = [
        Index(name = "idx__courses_group_id", columnList = "course_group_id"),
    ],
)
data class CourseEntity(
    @Column(name = "course_group_id", columnDefinition = "bigint", nullable = false)
    val groupId: Long,
    @Column(name = "step", columnDefinition = "bigint", nullable = false)
    val step: Int,
    @Column(name = "course_stage", columnDefinition = "varchar(32)", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val courseStage: CourseStage,
    @Column(name = "place_category", columnDefinition = "varchar(32)", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val placeCategory: PlaceCategory,
    @Column(name = "place_id", columnDefinition = "bigint", nullable = false)
    val placeId: Long,
    @Column(name = "is_visited", columnDefinition = "bit", nullable = false)
    val isVisited: Boolean,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {
    fun toDomainModel(): Course {
        return Course(
            courseId = id,
            groupId = groupId,
            step = step,
            placeId = placeId,
            placeCategory = placeCategory,
            visitedStatus = isVisited,
            courseStage = courseStage,
        )
    }

    companion object {
        fun fromCourse(course: Course): CourseEntity {
            return CourseEntity(
                groupId = course.groupId,
                step = course.step,
                placeId = course.placeId,
                placeCategory = course.placeCategory,
                isVisited = course.visitedStatus,
                courseStage = course.courseStage,
                id = course.courseId,
            )
        }
    }
}
