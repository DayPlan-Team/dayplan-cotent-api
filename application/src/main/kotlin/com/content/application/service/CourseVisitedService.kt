package com.content.application.service

import com.content.application.port.PlacePort
import com.content.domain.course.Course
import com.content.domain.course.LocationRectangleRangeCreator
import com.content.domain.course.port.CourseCommandPort
import com.content.domain.course.port.CourseQueryPort
import com.content.domain.location.UserLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseVisitedService(
    private val courseCommandPort: CourseCommandPort,
    private val courseQueryPort: CourseQueryPort,
    private val placePort: PlacePort,
    private val coroutineScope: CoroutineScope,
) {
    @Transactional
    fun updateCourseVisitedStatus(userLocation: UserLocation) {
        coroutineScope.launch {
            val (latitudeMin, latitudeMax) = LocationRectangleRangeCreator.getLatitudeRange(userLocation.latitude)
            val (longitudeMin, longitudeMax) = LocationRectangleRangeCreator.getLongitudeRange(userLocation.longitude)

            val courses = courseQueryPort.getCursesByUserIdAndVisitedStatus(userLocation.userId, false)
            val coursesMap = courses.groupBy { it.placeId }

            val coursesToUpdate =
                placePort.getSuspendPlaceByPlaceId(coursesMap.keys.toList())
                    .filter {
                        it.latitude in latitudeMin..latitudeMax &&
                            it.longitude in longitudeMin..longitudeMax
                    }
                    .flatMap { place ->
                        coursesMap[place.placeId]?.map { course ->
                            Course.from(
                                course = course,
                                place = place,
                                visitedStatus = true,
                            )
                        } ?: emptyList()
                    }
            courseCommandPort.upsertCourses(coursesToUpdate)
        }
    }
}
