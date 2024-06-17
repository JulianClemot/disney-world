package io.mobilisinmobile.disneyworld.proxies.definition

import io.mobilisinmobile.disneyworld.RestCharacterResult
import io.mobilisinmobile.disneyworld.RestCharactersResult

interface IDisneyServiceProxy {
    suspend fun getCharacter(characterId: Int): RestCharacterResult

    suspend fun getCharacters(): RestCharactersResult
}