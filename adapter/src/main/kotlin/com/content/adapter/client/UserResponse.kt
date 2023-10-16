package com.content.adapter.client

import com.content.domain.user.UserAccountStatus

data class UserResponse(
    val userId: Long,
    val userAccountStatus: UserAccountStatus,
    val nickName: String,
)
