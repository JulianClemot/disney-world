package io.mobilisinmobile.disneyworld.proxies.definition

interface IDetailsUIProxy {
    fun showContent()
    fun showError()
    fun hideAllViews()
    fun setName(name: String)
    fun setAvatar(imageUrl: String?)
    fun setTvShows(tvShows: List<String>)
    fun setAttractions(parkAttractions: List<String>)
}