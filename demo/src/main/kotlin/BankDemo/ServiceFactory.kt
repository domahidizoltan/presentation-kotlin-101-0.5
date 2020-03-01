package hu.telekom.bankdemo.kotlin

import hu.telekom.bankdemo.kotlin.domain.Address
import hu.telekom.bankdemo.kotlin.domain.BankData
import hu.telekom.bankdemo.kotlin.domain.BankService
import hu.telekom.bankdemo.kotlin.domain.Day.*
import hu.telekom.bankdemo.kotlin.transport.OtpBankDto

internal class ServiceFactory {
    companion object {
        fun createService(dtos: List<OtpBankDto>): BankService {
            val bankData = dtos.map { it.toEntity() }
            return BankService(bankData)
        }
    }
}

private fun OtpBankDto.toEntity() = BankData(
        id = id.toInt(),
        address = Address(address, zipCode.toInt(), town),
        hasFreeWifi = specialServices.contains("Free WiFi"),
        openingHours = openingHours.toOpeningHoursMap()
)

private fun List<String>.toOpeningHoursMap() = mapOf(
        MONDAY to this[0],
        TUESDAY to this[1],
        WEDNESDAY to this[2],
        THURSDAY to this[3],
        FRIDAY to this[4],
        SATURDAY to this[5],
        SUNDAY to this[6]
).filterValues { !it.isBlank() }
