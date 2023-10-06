package com.content.adapter.course.persistence

import com.content.adapter.course.entity.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseEntityRepository : JpaRepository<CourseEntity, Long> {

    fun findCourseEntitiesByGroupId(groupId: Long): List<CourseEntity>

    fun findCourseEntitiesByUserIdAndVisitedStatus(userId: Long, visitedStatus: Boolean): List<CourseEntity>
}