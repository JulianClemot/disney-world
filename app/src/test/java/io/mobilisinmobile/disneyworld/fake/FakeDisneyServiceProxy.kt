package io.mobilisinmobile.disneyworld.fake


import io.mobilisinmobile.disneyworld.RestCharacterResult
import io.mobilisinmobile.disneyworld.RestCharactersResult
import io.mobilisinmobile.disneyworld.proxies.definition.IDisneyServiceProxy

class FakeDisneyServiceProxy : IDisneyServiceProxy {

    lateinit var characterResult: RestCharacterResult
    var expectedCharacterId: Int? = null
    var shouldThrowException = false

    override suspend fun getCharacter(characterId: Int): RestCharacterResult {
        if (shouldThrowException) {
            throw Exception("Fake exception")
        }
        return characterResult
    }

    override suspend fun getCharacters(): RestCharactersResult {
        return RestCharactersResult(
            info = characterResult.info,
            characters = listOf(characterResult.character)
        )
    }
}