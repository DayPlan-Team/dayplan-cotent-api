package com.content.application.request

import com.content.util.share.AddressCategory
import org.springframework.data.domain.Pageable

data class CourseSearchRequest(
    val addressCategory: AddressCategory,
    val pageable: Pageable,
)