package com.content.application.response

import com.content.domain.share.PlaceCategory
import com.fasterxml.jackson.annotation.JsonProperty

data class CourseGroupListSearchResponse(
    @JsonProperty("hasNext") val hasNext: Boolean,
    @JsonProperty("courseGroupItems") val courseGroupItems: List<CourseGroupItem>,
)

data class CourseGroupItem(
    @JsonProperty("title") val title: String,
    @JsonProperty("groupId") val groupId: Long,
    @JsonProperty("cityName") val cityName: String,
    @JsonProperty("districtName") val districtName: String,
    @JsonProperty("courseCategories") val courseCategories: List<CourseStepItem>,
    @JsonProperty("modifiedAt") val modifiedAt: String,
)

data class CourseStepItem(
    @JsonProperty("step") val step: Int,
    @JsonProperty("courseId") val courseId: Long,
    @JsonProperty("category") val category: PlaceCategory,
)
