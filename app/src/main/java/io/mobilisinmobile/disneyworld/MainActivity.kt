package io.mobilisinmobile.disneyworld

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import io.mobilisinmobile.disneyworld.CharactersAdapter.CharacterViewHolder
import io.mobilisinmobile.disneyworld.databinding.ActivityMainBinding
import io.mobilisinmobile.disneyworld.databinding.ItemCharacterBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val disneyService = GetCharactersUseCase()
    private val adapter = CharactersAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        GlobalScope.launch {
            val characters = disneyService.getCharacters().characters
            runOnUiThread {
                adapter.submitList(characters)
            }
        }
    }
}

class CharactersAdapter(val context: Context) :
    ListAdapter<CharacterData, CharacterViewHolder>(CharacterDiffUtil()) {
    class CharacterDiffUtil : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem == newItem
        }

    }

    class CharacterViewHolder(
        private val context: Context,
        private val binding: ItemCharacterBinding,
    ) : ViewHolder(binding.root) {
        constructor(context: Context, parent: ViewGroup) : this(
            context,
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        fun bind(item: CharacterData) {
            binding.apply {
                avatar.load(item.imageUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }

                name.text = item.name

                val appearancesInMovie = item.films + item.shortFilms + item.tvShows

                val stringBuilder = StringBuilder()
                appearancesInMovie.forEachIndexed { index, appearance ->
                    stringBuilder.append(appearance)
                    if (index < appearancesInMovie.size - 1) {
                        stringBuilder.append(", ")
                    }
                }

                appearances.text = stringBuilder.toString()

                root.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("characterId", item.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(context, parent)
    }
}