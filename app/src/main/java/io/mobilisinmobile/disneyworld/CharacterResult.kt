package io.mobilisinmobile.disneyworld

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterResult(
    @SerialName("info")
    val info: Info,

    @SerialName("data")
    val character: CharacterData,
)