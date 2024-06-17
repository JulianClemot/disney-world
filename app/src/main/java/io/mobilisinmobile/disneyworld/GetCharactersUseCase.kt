package io.mobilisinmobile.disneyworld

class GetCharactersUseCase(private val characterRepository: CharacterRepository) {

    suspend fun getCharacters(): RestCharactersResult {
        return characterRepository.getCharacters()
    }
}