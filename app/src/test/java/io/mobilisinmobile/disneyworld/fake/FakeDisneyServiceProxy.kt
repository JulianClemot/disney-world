package io.mobilisinmobile.disneyworld.fake

import io.mobilisinmobile.disneyworld.CharacterResult
import io.mobilisinmobile.disneyworld.proxies.definition.IDisneyServiceProxy

class FakeDisneyServiceProxy: IDisneyServiceProxy {

    lateinit var characterResult: CharacterResult
    var expectedCharacterId: Int? = null
    var shouldThrowException = false

    override suspend fun getCharacter(characterId: Int): CharacterResult {
        if (shouldThrowException) {
            throw Exception("Fake exception")
        }
        return characterResult
    }
}