package com.content.adapter.course.persistence

import com.content.adapter.course.entity.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseEntityRepository : JpaRepository<CourseEntity, Long> {
    fun findCourseEntitiesByGroupId(groupId: Long): List<CourseEntity>

    fun findCoursesEntitiesByGroupIdIn(groupIds: List<Long>): List<CourseEntity>

    fun findCourseEntitiesByGroupIdIn(groupIds: List<Long>): List<CourseEntity>

    fun findCourseEntitiesByGroupIdInAndVisitedStatus(
        groupIds: List<Long>,
        visitedStatus: Boolean,
    ): List<CourseEntity>
}
