package com.content.application.port

import com.content.domain.user.User
import org.springframework.stereotype.Component

@Component
interface UserQueryPort {

    fun findUserByUserId(userId: Long): User

    fun findUsersByUserIds(userIds: List<Long>): List<User>
}