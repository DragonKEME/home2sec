package fr.insacvl.home2sec

import fr.insacvl.home2sec.utils.DateUtils
import io.ktor.client.HttpClient

/**
 * Class to pass platform specific implementation to commonMain
 */
data class Configuration (
    val httpClient: HttpClient,
    val dateUtils: DateUtils
)
