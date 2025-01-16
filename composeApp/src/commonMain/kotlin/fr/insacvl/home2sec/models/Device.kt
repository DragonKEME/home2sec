package fr.insacvl.home2sec.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Device(
    val id: Int,
    var name: String? = null,
    var ip: String,
    val type : String,
    @SerialName("device_metadata")
    val deviceMetadata: JsonObject,
    @SerialName("discovered_at")
    val discoveredAt: String? = null,
    @SerialName("registered_at")
    var registeredAt: String? = null,
    @SerialName("last_seen")
    val lastSeen: String? = null,

    var registered: Boolean = false
)
