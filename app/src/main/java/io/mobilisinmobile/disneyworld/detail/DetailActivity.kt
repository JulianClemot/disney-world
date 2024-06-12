package io.mobilisinmobile.disneyworld.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.transform.CircleCropTransformation
import io.mobilisinmobile.disneyworld.R
import io.mobilisinmobile.disneyworld.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterDetailState.collect {
                    when (it) {
                        is DetailViewModel.DetailScreenState.Loading -> {
                            showEmptyScreen()
                        }

                        is DetailViewModel.DetailScreenState.Error -> {
                            showErrorScreen(it.errorMessage)
                        }

                        is DetailViewModel.DetailScreenState.Success -> {
                            showCharacterData(it.character)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val characterId = intent.extras?.getInt("characterId")
        viewModel.getCharacterDetail(characterId)
    }

    private fun showEmptyScreen() {
        binding.content.visibility = View.GONE
        binding.errorView.visibility = View.GONE
    }

    private fun showCharacterData(character: Character) {
        binding.content.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
        binding.name.text = character.name
        binding.avatar.load(character.imageUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.tvShows.text =
            "Séries Télés : ${character.tvShows.joinToString(", ")}"

        binding.disneyAttractions.text =
            "Attractions Disney : ${character.parkAttractions.joinToString(", ")}"
    }

    private fun showErrorScreen(errorMessage: String) {
        binding.content.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.errorLabel.text = errorMessage
    }
}