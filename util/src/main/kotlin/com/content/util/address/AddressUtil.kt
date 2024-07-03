package com.content.util.address

import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
object AddressUtil {
    private val districtsByCityCode =
        DistrictCode
            .values()
            .filter { it != DistrictCode.DEFAULT }
            .groupBy { it.city.code }

    private val districtByDistrictCode =
        DistrictCode
            .values()
            .filter { it != DistrictCode.DEFAULT }
            .associateBy { it.code }

    private val cityByCityCode =
        CityCode
            .values()
            .filter { it != CityCode.DEFAULT }
            .associateBy { it.code }

    val cities =
        CityCode
            .values()
            .filter { it != CityCode.DEFAULT }

    fun getDistrictByCityCode(cityCode: Long): List<DistrictCode> {
        return districtsByCityCode[cityCode] ?: throw ContentException(ContentExceptionCode.BAD_REQUEST_CITY_CODE)
    }

    fun verifyAddressCodeAndGet(
        cityCodeNumber: Long,
        districtCodeNumber: Long,
    ): AddressCode {
        val cityCode = verifyCityCodeAndGet(cityCodeNumber)
        val districtCode = verifyDistrictCodeAndGet(districtCodeNumber)
        require(districtCode.city == cityCode) { throw ContentException(ContentExceptionCode.BAD_REQUEST_DISTRICT_CODE) }
        return AddressCode(
            cityCode = cityCode,
            districtCode = districtCode,
        )
    }

    private fun verifyCityCodeAndGet(cityCode: Long): CityCode {
        return cityByCityCode[cityCode] ?: throw ContentException(ContentExceptionCode.BAD_REQUEST_CITY_CODE)
    }

    private fun verifyDistrictCodeAndGet(districtCode: Long): DistrictCode {
        return districtByDistrictCode[districtCode] ?: throw ContentException(ContentExceptionCode.BAD_REQUEST_DISTRICT_CODE)
    }
}
