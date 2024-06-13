package io.mobilisinmobile.disneyworld.new

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RestCharacterResult(
    @SerialName("info")
    val info: RestInfo,

    @SerialName("data")
    val character: RestCharacterData,
)