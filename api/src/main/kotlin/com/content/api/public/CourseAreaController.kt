package com.content.api.public

import com.content.application.service.UserVerifyService
import com.content.util.share.Logger
import com.user.util.address.AddressUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/area")
class CourseAreaController(
    private val userVerifyService: UserVerifyService,
) {

    @GetMapping("/city")
    fun getCity(
        @RequestHeader("UserId") userId: Long,
    ): ResponseEntity<LocationOuterResponse<List<LocationResponse>>> {

        userVerifyService.verifyNormalUserAndGet(userId)

        return ResponseEntity.ok(
            LocationOuterResponse(
                results = AddressUtil.cities.map {
                    LocationResponse(
                        name = it.koreanName,
                        code = it.code,
                    )
                }
            ),
        )
    }

    @GetMapping("/city/{cityCode}/districts")
    fun getDistrictsInCity(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("cityCode") cityCode: Long,
    ): ResponseEntity<LocationOuterResponse<List<LocationResponse>>> {

        userVerifyService.verifyNormalUserAndGet(userId)

        return ResponseEntity.ok(
            LocationOuterResponse(
                results = AddressUtil.getDistrictByCityCode(cityCode)
                    .map {
                        LocationResponse(
                            name = it.koreanName,
                            code = it.code,
                        )
                    },
            )
        )
    }

    data class LocationOuterResponse<T>(
        val results: T
    )

    data class LocationResponse(
        val name: String,
        val code: Long,
    )

    companion object : Logger()

}