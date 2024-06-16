package io.mobilisinmobile.disneyworld.proxies.impl

import android.view.View
import coil.load
import coil.transform.CircleCropTransformation
import io.mobilisinmobile.disneyworld.databinding.ActivityDetailBinding
import io.mobilisinmobile.disneyworld.proxies.definition.IDetailsUIProxy

class DetailsUIProxy(private val binding: ActivityDetailBinding) : IDetailsUIProxy {

    override fun showContent() {
        binding.content.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
    }

    override fun showError() {
        binding.content.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.errorLabel.text = "Erreur lors de la récupération des données"
    }

    override fun hideAllViews() {
        binding.content.visibility = View.GONE
        binding.errorView.visibility = View.GONE
    }

    override fun setName(name: String) {
        binding.name.text = name
    }

    override fun setAvatar(imageUrl: String?) {
        binding.avatar.load(imageUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    override fun setTvShows(tvShows: List<String>) {
        binding.tvShows.text = "Séries Télés : " + tvShows.joinToString(", ")
    }

    override fun setAttractions(parkAttractions: List<String>) {
        binding.disneyAttractions.text =
            "Attractions Disney : " + parkAttractions.joinToString(
                ", "
            )
    }
}