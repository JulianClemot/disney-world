package io.mobilisinmobile.disneyworld.proxies.definition

import io.mobilisinmobile.disneyworld.CharacterResult

interface IDisneyServiceProxy {
    suspend fun getCharacter(characterId: Int): CharacterResult
}