package io.mobilisinmobile.disneyworld.detail

import io.mobilisinmobile.disneyworld.proxies.definition.IDetailsUIProxy

class NewDetailDelegate(
    private val uiProxy: IDetailsUIProxy,
    private val viewModel: DetailViewModel,
) : IDetailDelegate {
    override suspend fun register() {
        viewModel.characterDetailState.collect {
            when (it) {
                is DetailViewModel.DetailScreenState.Loading -> {
                    uiProxy.hideAllViews()
                }

                is DetailViewModel.DetailScreenState.Error -> {
                    uiProxy.showError()
                }

                is DetailViewModel.DetailScreenState.Success -> {
                    uiProxy.showContent()
                    uiProxy.setName(it.character.name)
                    uiProxy.setAvatar(it.character.imageUrl)
                    uiProxy.setTvShows(it.character.tvShows)
                    uiProxy.setAttractions(it.character.parkAttractions)
                }
            }
        }
    }

    override fun fetchCharacter(characterId: Int) {
        viewModel.getCharacterDetail(characterId)
    }
}