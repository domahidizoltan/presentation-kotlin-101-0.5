package hu.telekom.ubuntureleasedemo.java;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UbuntuReleaseClient {

    private WebClient ubuntuReleaseWebClient;

    public UbuntuReleaseClient(WebClient client) {
        this.ubuntuReleaseWebClient = client;
    }

    public Mono<String> getVersion(int idx) {
        return ubuntuReleaseWebClient
            .get().uri("/version/" + idx).retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> getFirstCodeName(String version) {
        return ubuntuReleaseWebClient
            .get().uri("/codename/first/" + version).retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> getLastCodeName(String version) {
        return ubuntuReleaseWebClient
            .get().uri("/codename/last/" + version).retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> getLts(String codename) {
        return ubuntuReleaseWebClient
            .get().uri("/lts/" + codename).retrieve()
            .bodyToMono(String.class);
    }

}
