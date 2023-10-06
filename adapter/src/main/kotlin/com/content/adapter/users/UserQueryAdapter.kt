package com.content.adapter.users

import com.content.adapter.client.UserClient
import com.content.application.port.UserQueryPort
import com.content.domain.user.User
import com.content.util.exception.ContentException
import com.content.util.exception.SystemException
import com.content.util.exceptioncode.ContentExceptionCode
import com.content.util.exceptioncode.SystemExceptionCode
import com.content.util.share.Logger
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class UserQueryAdapter(
    private val userClient: UserClient,
) : UserQueryPort {
    override fun verifyAndGetUser(userId: Long): User {
        try {
            val call = userClient.getUserResponse(userId)
            val response = call.execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    return User(
                        userId = it.userId,
                        nickName = it.nickName,
                    )
                }
            }
            throw ContentException(ContentExceptionCode.USER_INVALID)
        } catch (e: IOException) {
            log.error(e.message)
            throw SystemException(SystemExceptionCode.USER_SERVER_REST_EXCEPTION)
        }
    }

    override fun findUsersByUserIds(userIds: List<Long>): List<User> {
        try {
            val call = userClient.getUserResponses(userIds)

            val response = call.execute()
            log.info("response = ${response}")
            if (response.isSuccessful) {
                val result = response.body()?.users.let {
                    it?.map { user ->
                        User(
                            userId = user.userId,
                            nickName = user.nickName,
                        )
                    }

                }
                return result ?: emptyList()
            }
            return emptyList()
        } catch (e: IOException) {
            log.error(e.message)
            throw SystemException(SystemExceptionCode.USER_SERVER_REST_EXCEPTION)
        }
    }

    companion object : Logger()
}