package io.mobilisinmobile.disneyworld

interface IDetailDelegate {
    suspend fun fetchCharacter(characterId: Int)
}