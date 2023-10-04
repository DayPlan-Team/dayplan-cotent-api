package com.content.adapter.course.persistence

import com.content.adapter.course.entity.CourseGroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseGroupEntityRepository : JpaRepository<CourseGroupEntity, Long> {

}