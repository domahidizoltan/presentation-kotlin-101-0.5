package hu.telekom.ubuntureleasedemo.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newFixedThreadPoolContext
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClientResponseException
import kotlin.random.Random

@RestController
class UbuntuReleaseController(val ubuntuReleaseClient: UbuntuReleaseClient) {

    val log = LoggerFactory.getLogger(UbuntuReleaseController::class.java);

    val ctx = newFixedThreadPoolContext(50, "custom-tp")

    @GetMapping("/random-releases")
    fun getReleaseNames(@RequestParam(name = "qty", defaultValue = "3") qty: Int): Flow<String> {
        return flow {
            (0 until qty)
                    .map { mapToRandomIndex(it) }
                    .map { fetchVersion(it) }
                    .map {
                        val ver = it.await()
                        fetchFirstCode(ver) to fetchLastCode(ver)
                    }
                    .map {
                        val codename = "${it.first.await()} ${it.second.await()}"
                        fetchLts(codename)
                    }
                    .map { emit(it.await() +  "<br/>\r\n") }
        }
    }


    private fun mapToRandomIndex(it: Int): Int {
        val idx = Random.nextInt(RELEASE_COUNT)
        log.info("Mapped count {} to index {}", it, idx)
        return idx
    }

    private suspend fun fetchVersion(idx: Int) = CoroutineScope(ctx).async {
        val ver = ubuntuReleaseClient.getVersion(idx)
        log.info("> {}: received version {}", idx, ver)
        ver
    }

    private suspend fun fetchFirstCode(ver: String) = CoroutineScope(ctx).async {
        val name = ubuntuReleaseClient.getFirstCodeName(ver)
        log.info(">> {}: received first codename {}", ver, name)
        name
    }

    private suspend fun fetchLastCode(ver: String) = CoroutineScope(ctx).async {
        val name = ubuntuReleaseClient.getLastCodeName(ver)
        log.info(">>> {}: received last codename {}", ver, name)
        name
    }

    private suspend fun fetchLts(codename: String) = CoroutineScope(ctx).async {
        val lts = try { ubuntuReleaseClient.getLts(codename) }
        catch (ex: WebClientResponseException.NotFound) { "" }

        log.info(">>>> {} {}", codename, lts)
        "$codename $lts"
    }

}
