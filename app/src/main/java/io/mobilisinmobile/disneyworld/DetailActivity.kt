package io.mobilisinmobile.disneyworld

import android.os.Bundle
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
            val characterResult = getCharacterUseCase.getCharacter(characterId.toString())
            runOnUiThread {
                binding.name.text = characterResult.character.name
                binding.avatar.load(characterResult.character.imageUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                binding.allies.text = "Séries Télés : "+characterResult.character.tvShows.joinToString(", ")

                binding.enemies.text = "Attractions Disney : "+characterResult.character.parkAttractions.joinToString(", ")
            }
        }
    }
}