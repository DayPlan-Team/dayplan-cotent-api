package com.content.application.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CourseGroupWithUserNicknameResponse(
    @JsonProperty("courseGroupId") val courseGroupId: Long,
    @JsonProperty("userNickName") val userNickName: String,
)