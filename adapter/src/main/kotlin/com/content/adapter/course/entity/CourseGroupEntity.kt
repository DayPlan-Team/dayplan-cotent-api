package com.content.adapter.course.entity

import com.content.adapter.share.BaseEntity
import com.content.domain.course.CourseGroup
import com.content.util.address.CityCode
import com.content.util.address.DistrictCode
import com.content.util.share.DateTimeCustomFormatter
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(
    name = "course_group",
    indexes = [
        Index(name = "idx_course_group_userId", columnList = "userId"),
        Index(name = "idx_course_group_city_district", columnList = "cityCode,districtCode"),
    ]
)
data class CourseGroupEntity(
    @Column
    val userId: Long,

    @Column
    val groupName: String,

    @Column
    @Enumerated(value = EnumType.STRING)
    val cityCode: CityCode,

    @Column
    @Enumerated(value = EnumType.STRING)
    val districtCode: DistrictCode,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {

    fun toDomainModel(): CourseGroup {
        return CourseGroup(
            groupId = id,
            userId = userId,
            groupName = groupName,
            cityCode = cityCode,
            districtCode = districtCode,
        )
    }

    companion object {
        fun fromCourseGroup(courseGroup: CourseGroup): CourseGroupEntity {

            val groupName = when {
                courseGroup.groupName.isBlank() -> "${CourseGroup.DEFAULT_NAME}_${DateTimeCustomFormatter.nowToDefaultFormat()}"
                else -> courseGroup.groupName
            }

            return CourseGroupEntity(
                userId = courseGroup.userId,
                groupName = groupName,
                cityCode = courseGroup.cityCode,
                districtCode = courseGroup.districtCode,
                id = courseGroup.groupId,
            )
        }
    }

}
