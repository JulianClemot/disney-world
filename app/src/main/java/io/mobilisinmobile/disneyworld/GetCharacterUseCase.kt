package io.mobilisinmobile.disneyworld

import io.mobilisinmobile.disneyworld.detail.Character

class GetCharacterUseCase(private val characterRepository: CharacterRepository) {

    suspend fun getCharacter(characterId: Int?): Character {
        return characterId?.let {
            characterRepository.getCharacter(it).toCharacter()
        } ?: throw IllegalArgumentException("Character id is null")
    }
}

fun RestCharacterResult.toCharacter() = Character(
    name = this.character.name,
    imageUrl = this.character.imageUrl.orEmpty(),
    films = this.character.films,
    shortFilms = this.character.shortFilms,
    tvShows = this.character.tvShows,
    parkAttractions = this.character.parkAttractions
)