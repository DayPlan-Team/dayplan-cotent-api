package com.content.api.internal

import com.content.application.service.CourseVisitedService
import com.content.domain.location.UserLocation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/datecourse/visitedstatus")
class CourseVisitedController(
    private val courseVisitedService: CourseVisitedService,
) {

    @PostMapping
    fun updateDatecourseVisitedStatus(
        @RequestBody userLocation: UserLocationApiRequest,
    ): ResponseEntity<Unit> {
        courseVisitedService.updateCourseVisitedStatus(
            UserLocation(
                userId = userLocation.userId,
                latitude = userLocation.latitude,
                longitude = userLocation.longitude,
            )
        )

        return ResponseEntity.ok().build()
    }

    data class UserLocationApiRequest(
        val userId: Long,
        val latitude: Double,
        val longitude: Double,
    )

}