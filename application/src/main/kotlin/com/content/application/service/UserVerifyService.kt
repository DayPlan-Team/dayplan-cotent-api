package com.content.application.service

import com.content.application.port.UserQueryPort
import com.content.domain.user.User
import com.content.domain.user.UserAccountStatus
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Service

@Service
class UserVerifyService(
    private val userQueryPort: UserQueryPort,
) {

    fun verifyNormalUserAndGet(userId: Long): User {
        val user = userQueryPort.findUserByUserId(userId = userId)
        require(user.userAccountStatus == UserAccountStatus.NORMAL) { throw ContentException(ContentExceptionCode.USER_INVALID) }
        return user
    }

    fun getNormalUsersAndGet(userIds: List<Long>): List<User> {
        return userQueryPort.findUsersByUserIds(userIds = userIds)
            .filter { it.userAccountStatus == UserAccountStatus.NORMAL }
    }

}