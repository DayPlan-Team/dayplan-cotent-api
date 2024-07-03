package com.content.domain.user

data class User(
    val userId: Long,
    val userAccountStatus: UserAccountStatus,
    val nickName: String,
)
