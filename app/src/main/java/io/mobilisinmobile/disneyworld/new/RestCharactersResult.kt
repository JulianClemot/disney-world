package io.mobilisinmobile.disneyworld.new

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RestCharactersResult(
    @SerialName("info")
    val info: RestInfo,

    @SerialName("data")
    val characters: List<RestCharacterData>,
)

@Serializable
data class RestInfo(
    @SerialName("totalPages")
    val totalPages: Int? = null,

    @SerialName("count")
    val count: Int,

    @SerialName("previousPage")
    val previousPage: String? = null,

    @SerialName("nextPage")
    val nextPage: String? = null,
)

@Serializable
data class RestCharacterData(
    @SerialName("_id")
    val id: Int,

    @SerialName("imageUrl")
    val imageUrl: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("allies")
    val allies: List<String>,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("enemies")
    val enemies: List<String>,

    @SerialName("films")
    val films: List<String>,

    @SerialName("parkAttractions")
    val parkAttractions: List<String>,

    @SerialName("shortFilms")
    val shortFilms: List<String>,

    @SerialName("sourceUrl")
    val sourceUrl: String,

    @SerialName("tvShows")
    val tvShows: List<String>,

    @SerialName("updatedAt")
    val updatedAt: String,

    @SerialName("url")
    val url: String,

    @SerialName("videoGames")
    val videoGames: List<String>,
)