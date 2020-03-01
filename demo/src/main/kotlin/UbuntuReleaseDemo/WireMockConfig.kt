package hu.telekom.ubuntureleasedemo.kotlin

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.LogNormal
import java.io.File

private const val UBUNTU_FILE_PATH = "src/main/resources/ubuntu-release.txt";
const val RELEASE_COUNT = 32
const val WIREMOCK_BASE_URL = "http://localhost:8080"

fun setUpWireMock() {
    WireMockServer().start()
    setGlobalRandomDelay(LogNormal(1000.0, 0.5))

    File(UBUNTU_FILE_PATH).readLines()
            .map { it.split(" ") }
            .forEachIndexed { idx, it ->
                val (version, firstName, lastName) = it

                stubFor(get("/version/$idx").willReturn(ok(version)))
                stubFor(get("/codename/first/$version").willReturn(ok(firstName)))
                stubFor(get("/codename/last/$version").willReturn(ok(lastName)))

                val lts = if (it.size == 4) { ok(it[3]) } else { notFound() }
                stubFor(get("/lts/$firstName%20$lastName").willReturn(lts))
            }
}