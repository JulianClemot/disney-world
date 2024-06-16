package io.mobilisinmobile.disneyworld.fake

import android.view.View
import io.mobilisinmobile.disneyworld.proxies.definition.IDetailsUIProxy

class FakeDetailsUIProxy : IDetailsUIProxy {

    var errorViewVisibility: Int = View.GONE
    var contentVisibility: Int = View.GONE
    var errorLabelText: String? = null

    var nameText: String? = null
    var avatarImageUrl: String? = null
    var tvShowsList: List<String>? = null
    var disneyAttractions: List<String>? = null

    override fun showContent() {
        contentVisibility = View.VISIBLE
        errorViewVisibility = View.GONE
    }

    override fun showError() {
        contentVisibility = View.GONE
        errorViewVisibility = View.VISIBLE
        errorLabelText = "Erreur lors de la récupération des données"
    }

    override fun hideAllViews() {
        contentVisibility = View.GONE
        errorViewVisibility = View.GONE
    }

    override fun setName(name: String) {
        nameText = name
    }

    override fun setAvatar(imageUrl: String?) {
        avatarImageUrl = imageUrl
    }

    override fun setTvShows(tvShows: List<String>) {
        tvShowsList = tvShows
    }

    override fun setAttractions(parkAttractions: List<String>) {
        disneyAttractions = parkAttractions
    }
}