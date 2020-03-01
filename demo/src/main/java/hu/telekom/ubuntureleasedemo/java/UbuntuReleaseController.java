package hu.telekom.ubuntureleasedemo.java;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.function.Function;

import static hu.telekom.ubuntureleasedemo.kotlin.WireMockConfigKt.RELEASE_COUNT;

@RestController
public class UbuntuReleaseController {

    private static final Logger LOG = LoggerFactory.getLogger(UbuntuReleaseController.class);

    private UbuntuReleaseClient ubuntuReleaseClient;

    public UbuntuReleaseController(UbuntuReleaseClient ubuntuReleaseClient) {
        this.ubuntuReleaseClient = ubuntuReleaseClient;
    }

    @GetMapping("/random-releases")
    public Flux<String> getReleaseNames(@RequestParam(name = "qty", defaultValue = "3") int qty) {
        return Flux.range(0, qty)
            .map(mapToRandomIndex())
            .flatMap(fetchVersion())
            .flatMap(ver -> fetchFirstCode(ver)
                .zipWith(fetchLastCode(ver)))
            .map(tuple -> tuple.getT1() + " " + tuple.getT2())
            .flatMap(fetchLts())
            .map(it -> it + "<br/>\r\n");
    }

    @NotNull
    private Function<Integer, Integer> mapToRandomIndex() {
        return count -> {
            int idx = new Random().nextInt(RELEASE_COUNT);
            LOG.info("Mapped count {} to index {}", count, idx);
            return idx;
        };
    }

    @NotNull
    private Function<Integer, Publisher<? extends String>> fetchVersion() {
        return idx -> ubuntuReleaseClient.getVersion(idx)
            .doOnNext(it -> LOG.info("> {}: received version {}", idx, it));
    }

    @NotNull
    private Mono<String> fetchFirstCode(String ver) {
        return ubuntuReleaseClient.getFirstCodeName(ver)
            .doOnNext(it -> LOG.info(">> {}: received first codename {}", ver, it));
    }

    @NotNull
    private Mono<String> fetchLastCode(String ver) {
        return ubuntuReleaseClient.getLastCodeName(ver)
            .doOnNext(it -> LOG.info(">>> {}: received last codename {}", ver, it));
    }

    @NotNull
    private Function<String, Mono<? extends String>> fetchLts() {
        return codename -> ubuntuReleaseClient.getLts(codename)
            .onErrorResume(it -> Mono.just(""))
            .doOnSuccess(it -> LOG.info(">>>> {} {}", codename, it))
            .map(it -> codename + " " + it);
    }

}
