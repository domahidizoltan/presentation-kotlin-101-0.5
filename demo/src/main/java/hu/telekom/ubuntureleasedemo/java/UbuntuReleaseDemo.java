package hu.telekom.ubuntureleasedemo.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static hu.telekom.ubuntureleasedemo.kotlin.WireMockConfigKt.WIREMOCK_BASE_URL;
import static hu.telekom.ubuntureleasedemo.kotlin.WireMockConfigKt.setUpWireMock;

@Configuration
class AppConfig {

    @Bean
    public WebClient ubuntuReleaseWebClient() {
        return WebClient.create(WIREMOCK_BASE_URL);
    }

}

@SpringBootApplication
public class UbuntuReleaseDemo {

    public static void main(String[] args) {
        setUpWireMock();
        SpringApplication.run(UbuntuReleaseDemo.class, args);
    }

}
