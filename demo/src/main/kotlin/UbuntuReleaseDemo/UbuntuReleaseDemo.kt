package hu.telekom.ubuntureleasedemo.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfig {

    @Bean
    fun ubuntuReleaseWebClient() = WebClient.create(WIREMOCK_BASE_URL);

}

@SpringBootApplication
class UbuntuReleaseDemo

fun main(args: Array<String>) {
    setUpWireMock()
    runApplication<UbuntuReleaseDemo>(*args)
}
