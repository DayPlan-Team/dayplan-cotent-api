package com.content.application.response

import com.content.domain.course.Course
import com.content.domain.course.CourseStage
import com.content.domain.place.Place
import com.content.domain.share.PlaceCategory

data class DetailCourse(
    val step: Int,
    val placeCategory: PlaceCategory,
    val courseStage: CourseStage,
    val latitude: Double?,
    val longitude: Double?,
    val courseId: Long,
    val placeId: Long,
    val placeName: String = "",
    val address: String = "",
    val roadAddress: String = "",
) {

    companion object {
        fun from(
            course: Course,
            place: Place?,
        ): DetailCourse {
            return DetailCourse(
                step = course.step,
                placeCategory = course.placeCategory,
                latitude = place?.latitude,
                longitude = place?.longitude,
                courseId = course.courseId,
                placeId = course.placeId,
                courseStage = course.courseStage,
                placeName = place?.placeName ?: "",
                address = place?.address ?: "",
                roadAddress = place?.roadAddress ?: "",
            )
        }
    }


}