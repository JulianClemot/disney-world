package io.mobilisinmobile.disneyworld.detail

interface IDetailDelegate {
    fun fetchCharacter(characterId: Int)

    suspend fun register()
}