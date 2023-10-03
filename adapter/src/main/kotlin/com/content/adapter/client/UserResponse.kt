package com.content.adapter.client

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    @JsonProperty("userId") val userId: Long,
    @JsonProperty("verified") val verified: Boolean,
)
