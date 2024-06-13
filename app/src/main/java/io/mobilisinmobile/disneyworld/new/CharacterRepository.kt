package io.mobilisinmobile.disneyworld.new

interface CharacterRepository {
    suspend fun getCharacter(characterId: Int): RestCharacterResult

}

class CharacterRepositoryImpl(private val disneyService: NewDisneyService) : CharacterRepository {

    override suspend fun getCharacter(characterId: Int): RestCharacterResult {
        return disneyService.getCharacter(characterId)
    }
}