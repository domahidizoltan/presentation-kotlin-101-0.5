package hu.telekom.bankdemo.kotlin.transport

import hu.telekom.bankdemo.kotlin.domain.BankData
import hu.telekom.bankdemo.kotlin.transport.DtoFields.*

private enum class DtoFields(val idx:Int) {
    ID(2),
    OPENING_HOURS_001(4),
    OPENING_HOURS_002(5),
    OPENING_HOURS_003(6),
    OPENING_HOURS_004(7),
    OPENING_HOURS_005(8),
    OPENING_HOURS_006(9),
    OPENING_HOURS_007(10),
    SPECIAL_SERVICES_001(11),
    SPECIAL_SERVICES_002(12),
    SPECIAL_SERVICES_003(13),
    SPECIAL_SERVICES_004(14),
    ADDRESS(15),
    TYPE(16),
    ZIP_CODE(49),
    TOWN(50),
    LAT(51),
    LONG(52),
}

data class OtpBankDto(
        val id: String,
        val type: String? = null,
        val town: String,
        val zipCode: String,
        val address: String,
        val lat: String? = null,
        val long: String? = null,
        val openingHours: List<String>,
        val specialServices: List<String>
)

internal fun List<String>.parseToDto() = OtpBankDto(
        id = this[ID.idx],
        type = this[TYPE.idx],
        town = this[TOWN.idx],
        zipCode = this[ZIP_CODE.idx],
        address = this[ADDRESS.idx],
        lat = this[LAT.idx],
        long = this[LONG.idx],
        openingHours = this.slice(OPENING_HOURS_001.idx..OPENING_HOURS_007.idx),
        specialServices = this.slice(SPECIAL_SERVICES_001.idx..SPECIAL_SERVICES_004.idx)
)

internal fun BankData.toDto() = OtpBankDto(
        id = id.toString(),
        town = address.town,
        zipCode = address.zipCode.toString(),
        address = address.address,
        openingHours = openingHours.map { "${it.key}=${it.value}" },
        specialServices = if (hasFreeWifi) listOf("Free WiFi") else emptyList()
)