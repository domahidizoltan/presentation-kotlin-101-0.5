package hu.telekom.ubuntureleasedemo.kotlin

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class UbuntuReleaseClient(private val ubuntuReleaseWebClient: WebClient) {

    suspend fun getVersion(idx: Int): String = ubuntuReleaseWebClient
            .get().uri("/version/$idx").retrieve()
            .awaitBody()

    suspend fun getFirstCodeName(version: String): String = ubuntuReleaseWebClient
            .get().uri("/codename/first/$version").retrieve()
            .awaitBody()

    suspend fun getLastCodeName(version: String): String = ubuntuReleaseWebClient
            .get().uri("/codename/last/$version").retrieve()
            .awaitBody()

    suspend fun getLts(codename: String): String = ubuntuReleaseWebClient
            .get().uri("/lts/$codename").retrieve()
            .awaitBody()

}
