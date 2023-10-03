package com.user.util.address

data class AddressCode(
    val cityCode: CityCode = CityCode.DEFAULT,
    val districtCode: DistrictCode = DistrictCode.DEFAULT,
)