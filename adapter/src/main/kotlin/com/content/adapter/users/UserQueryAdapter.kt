package com.content.adapter.users

import com.content.adapter.client.UserClient
import com.content.application.port.UserQueryPort
import com.content.domain.user.User

class UserQueryAdapter(
    private val userClient: UserClient,
) : UserQueryPort {
    override fun findById(userId: Long): User {
        val response = userClient.getUserResponse(userId)

        if (response.isSuccessful) {
            response.body()?.let {
                when {
                    it.verified -> return User(
                        userId = it.userId
                    )
                    else -> throw IllegalArgumentException()
                }
            }

        }
        throw IllegalArgumentException()
    }
}