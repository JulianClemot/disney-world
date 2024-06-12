package io.mobilisinmobile.disneyworld

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import coil.transform.CircleCropTransformation
import io.mobilisinmobile.disneyworld.databinding.ActivityDetailBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val getCharacterUseCase = GetCharacterUseCase()

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

        val characterId = intent.extras?.getInt("characterId")

        GlobalScope.launch {
            runOnUiThread {
                binding.content.visibility = View.GONE
                binding.errorView.visibility = View.GONE
                binding.loadingView.visibility = View.VISIBLE
            }
            try {
                val characterResult = getCharacterUseCase.getCharacter(characterId.toString())
                runOnUiThread {
                    binding.content.visibility = View.VISIBLE
                    binding.errorView.visibility = View.GONE
                    binding.loadingView.visibility = View.GONE
                    binding.name.text = characterResult.character.name
                    binding.avatar.load(characterResult.character.imageUrl) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                    binding.tvShows.text = "Séries Télés : "+characterResult.character.tvShows.joinToString(", ")

                    binding.disneyAttractions.text = "Attractions Disney : "+characterResult.character.parkAttractions.joinToString(", ")
                }
            } catch (exception : Exception) {
                exception.printStackTrace()
                runOnUiThread {
                    binding.content.visibility = View.GONE
                    binding.errorView.visibility = View.VISIBLE
                    binding.errorLabel.text = "Erreur lors de la récupération des données"
                    binding.loadingView.visibility = View.GONE
                }
            }
        }
    }
}