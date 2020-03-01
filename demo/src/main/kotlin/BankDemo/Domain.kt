package hu.telekom.bankdemo.kotlin.domain

data class Address(
        val address: String,
        val zipCode: Int,
        val town: String
)

enum class Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

data class BankData(
        val id: Int,
        val address: Address,
        val hasFreeWifi: Boolean,
        val openingHours: Map<Day, String>
)