package io.mobilisinmobile.disneyworld

import io.mobilisinmobile.disneyworld.proxies.definition.IDisneyServiceProxy

interface CharacterRepository {
    suspend fun getCharacter(characterId: Int): RestCharacterResult
    suspend fun getCharacters(): RestCharactersResult

}

class CharacterRepositoryImpl(private val disneyService: IDisneyServiceProxy) : CharacterRepository {

    override suspend fun getCharacter(characterId: Int): RestCharacterResult {
        return disneyService.getCharacter(characterId)
    }

    override suspend fun getCharacters(): RestCharactersResult {
        return disneyService.getCharacters()
    }
}