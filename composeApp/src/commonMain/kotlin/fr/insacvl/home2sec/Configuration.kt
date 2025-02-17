package fr.insacvl.home2sec

import fr.insacvl.home2sec.utils.DateUtils
import io.ktor.client.HttpClient

/**
 *
 */

data class Configuration (
    val httpClient: HttpClient,
    val dateUtils: DateUtils
)
