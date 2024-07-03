package com.content.domain.course

import com.content.domain.place.Place
import com.content.domain.share.PlaceCategory

data class Course(
    val courseId: Long,
    val step: Int,
    val placeId: Long,
    val courseStage: CourseStage,
    val placeCategory: PlaceCategory,
    val visitedStatus: Boolean = false,
    val groupId: Long = 0L,
) {
    companion object {
        fun from(
            courseId: Long,
            placeId: Long,
            courseStage: CourseStage,
            courseUpsertRequest: CourseUpsertRequest,
        ): Course {
            return Course(
                courseId = courseId,
                groupId = courseUpsertRequest.groupId,
                step = courseUpsertRequest.step,
                placeCategory = courseUpsertRequest.placeCategory,
                placeId = placeId,
                courseStage = courseStage,
            )
        }

        fun from(
            place: Place,
            courseStage: CourseStage,
            courseUpsertRequest: CourseUpsertRequest,
        ): Course {
            return Course(
                courseId = courseUpsertRequest.courseId,
                groupId = courseUpsertRequest.groupId,
                step = courseUpsertRequest.step,
                placeCategory = place.placeCategory,
                placeId = place.placeId,
                courseStage = courseStage,
            )
        }

        fun from(
            course: Course,
            place: Place,
            visitedStatus: Boolean,
        ): Course {
            return Course(
                groupId = course.groupId,
                courseId = course.courseId,
                step = course.step,
                courseStage = course.courseStage,
                placeId = place.placeId,
                placeCategory = place.placeCategory,
                visitedStatus = visitedStatus,
            )
        }
    }
}
