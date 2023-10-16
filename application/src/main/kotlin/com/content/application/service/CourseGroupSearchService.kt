package com.content.application.service

import com.content.application.port.CourseGroupSearchPort
import com.content.application.request.CourseGroupAdministrativeSearchRequest
import com.content.application.response.CourseGroupListSearchResponse
import com.content.application.response.CourseGroupWithUserNicknameResponse
import org.springframework.stereotype.Service

@Service
class CourseGroupSearchService(
    private val courseGroupSearchPort: CourseGroupSearchPort,
    private val userVerifyService: UserVerifyService,
) {
    fun searchCourseGroupsWithCourseBy(courseGroupAdministrativeSearchRequest: CourseGroupAdministrativeSearchRequest): CourseGroupListSearchResponse {
        return courseGroupSearchPort.findCourseGroupBy(courseGroupAdministrativeSearchRequest)
    }

    fun searchCourseGroupWithNickNameBy(courseGroupIds: List<Long>): List<CourseGroupWithUserNicknameResponse> {
        val courseGroupMapByUserId = courseGroupSearchPort
            .findCourseGroupByGroupIds(courseGroupIds)
            .groupBy { it.userId }

        val users = userVerifyService.getNormalUsersAndGet(courseGroupMapByUserId.keys.toList())

        return users.flatMap { user ->
            courseGroupMapByUserId[user.userId]?.map { courseGroup ->
                CourseGroupWithUserNicknameResponse(
                    courseGroupId = courseGroup.groupId,
                    userNickName = user.nickName,
                )
            } ?: emptyList()
        }
    }
}