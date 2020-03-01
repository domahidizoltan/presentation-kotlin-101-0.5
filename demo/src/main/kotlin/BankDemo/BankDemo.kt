package hu.telekom.bankdemo.kotlin

import hu.telekom.bankdemo.java.BankDemo.OTP_FILE_PATH
import hu.telekom.bankdemo.kotlin.transport.BankDataQuery
import hu.telekom.bankdemo.kotlin.transport.parseToDto
import java.io.File

fun main(args: Array<String>) {
    val start = System.currentTimeMillis()

    val dtos = readData()
    val bankService = ServiceFactory.createService(dtos)
    val query = BankDataQuery(bankService)

    val results = query.getBanksWithWeekendAvailabilityAndWifi(true, true)
    results.forEach { println(it) }
    println("Total: ${results.size}")

    val end = System.currentTimeMillis();
    println("Finished in ${end-start} ms")
}

private fun readData() = File(OTP_FILE_PATH).readLines()
        .drop(1)
        .filter { it.isNotBlank() }
        .map { it.trim('"').split("\",\"") }
        .map { it.parseToDto() }
