package io.mobilisinmobile.disneyworld

import io.mobilisinmobile.disneyworld.proxies.definition.IDetailsUIProxy
import io.mobilisinmobile.disneyworld.proxies.definition.IDisneyServiceProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailDelegate(
    private val serviceProxy: IDisneyServiceProxy,
    private val uiProxy: IDetailsUIProxy,
) : IDetailDelegate {
    override suspend fun fetchCharacter(characterId: Int) {
        withContext(Dispatchers.Main) {
            uiProxy.hideAllViews()
        }
        try {
            val characterResult = serviceProxy.getCharacter(characterId)
            withContext(Dispatchers.Main) {
                uiProxy.showContent()
                uiProxy.setName(characterResult.character.name)
                uiProxy.setAvatar(characterResult.character.imageUrl)
                uiProxy.setTvShows(characterResult.character.tvShows)
                uiProxy.setAttractions(characterResult.character.parkAttractions)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            withContext(Dispatchers.Main) {
                uiProxy.showError()
            }
        }
    }

}