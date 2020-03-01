package hu.telekom.bankdemo.kotlin.domain

import hu.telekom.bankdemo.java.domain.Availability.*
import hu.telekom.bankdemo.java.domain.BankService
import hu.telekom.bankdemo.kotlin.domain.Day.SATURDAY
import hu.telekom.bankdemo.kotlin.domain.Day.SUNDAY

class BankService(val banks: List<BankData>): BankService<BankData> {
    
    override fun getBanksByAvailability() = banks
            .groupBy {
                val openingDays = it.openingHours.keys
                when {
                    openingDays.size < 5 -> REDUCED
                    SATURDAY in openingDays && SUNDAY in openingDays -> ALLDAY
                    SATURDAY in openingDays -> INCREASED
                    else -> NORMAL
                }
            }

}