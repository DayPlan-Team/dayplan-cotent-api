package com.content.adapter.course.entity

import com.content.adapter.share.BaseEntity
import com.content.domain.course.Course
import com.content.domain.location.Location
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
    name = "course",
    indexes = [
        Index(name = "idx_groupId", columnList = "groupId"),
        Index(name = "idx_userId", columnList = "userId"),
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
    val placeId: Long,

    @Column
    val latitude: Double,

    @Column
    val longitude: Double,

    @Column
    val visitedStatus: Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {
    fun toCourse(): Course {
        return Course(
            courseId = id,
            groupId = groupId,
            userId = userId,
            step = step,
            placeId = placeId,
            location = Location(
                latitude = latitude,
                longitude = longitude,
            ),
            visitedStatus = visitedStatus,
        )
    }

    companion object {
        fun fromCourse(course: Course): CourseEntity {
            return CourseEntity(
                groupId = course.groupId,
                userId = course.userId,
                step = course.step,
                placeId = course.placeId,
                latitude = course.location.latitude,
                longitude = course.location.longitude,
                visitedStatus = course.visitedStatus,
                id = course.courseId,
            )
        }
    }
}