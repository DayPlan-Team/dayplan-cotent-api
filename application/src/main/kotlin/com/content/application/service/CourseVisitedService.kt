package com.content.application.service

import com.content.application.port.CourseCommandPort
import com.content.application.port.CourseQueryPort
import com.content.domain.course.Course
import com.content.domain.course.LocationRectangleRangeCreator
import com.content.domain.location.UserLocation
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseVisitedService(
    private val courseCommandPort: CourseCommandPort,
    private val courseQueryPort: CourseQueryPort,
) {

    @Transactional
    fun updateCourseVisitedStatus(userLocation: UserLocation) {
        val (latitudeMin, latitudeMax) = LocationRectangleRangeCreator.getLatitudeRange(userLocation.latitude)
        val (longitudeMin, longitudeMax) = LocationRectangleRangeCreator.getLongitudeRange(userLocation.longitude)

        val courses = courseQueryPort.getCursesByUserIdAndNotVisited(userLocation.userId)
            .filter {
                it.location.latitude in latitudeMin..latitudeMax &&
                        it.location.longitude in longitudeMin..longitudeMax
            }
            .map {
                Course(
                    groupId = it.groupId,
                    courseId = it.courseId,
                    userId = it.userId,
                    step = it.step,
                    placeCategory = it.placeCategory,
                    location = it.location,
                    visitedStatus = true,
                )
            }
        courseCommandPort.upsertCourses(courses)
    }
}