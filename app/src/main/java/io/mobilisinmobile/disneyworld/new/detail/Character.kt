package io.mobilisinmobile.disneyworld.new.detail

data class Character(
    val name: String,
    val imageUrl: String,
    val films: List<String>,
    val shortFilms: List<String>,
    val tvShows: List<String>,
    val parkAttractions: List<String>,
)