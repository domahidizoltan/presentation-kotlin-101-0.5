package hu.telekom.bankdemo.kotlin.transport

import hu.telekom.bankdemo.java.domain.Availability
import hu.telekom.bankdemo.java.domain.Availability.*
import hu.telekom.bankdemo.java.transport.BankDataQuery
import hu.telekom.bankdemo.kotlin.domain.*

class BankDataQuery(val bankService: BankService) : BankDataQuery<OtpBankDto> {

    override fun getBanksWithWeekendAvailabilityAndWifi(isOpenAtWeekend: Boolean, hasFreeWifi: Boolean) =
        bankService.getBanksByAvailability()
                .let { if (isOpenAtWeekend) it.weekends else it.workdays }
                .filter { it.hasFreeWifi == hasFreeWifi }
                .map { it.toDto() }

    private val Map<Availability, List<BankData>>.weekends: List<BankData>
            get() = listOf(this[INCREASED], this[ALLDAY])
                    .filterNotNull().flatten()

    private val Map<Availability, List<BankData>>.workdays: List<BankData>
        get() = listOf(this[REDUCED], this[NORMAL])
                .filterNotNull().flatten()
}
