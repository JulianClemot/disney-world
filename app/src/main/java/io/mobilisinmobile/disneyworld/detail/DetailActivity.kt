package io.mobilisinmobile.disneyworld.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.mobilisinmobile.disneyworld.R
import io.mobilisinmobile.disneyworld.databinding.ActivityDetailBinding
import io.mobilisinmobile.disneyworld.proxies.impl.DetailsUIProxy
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModel()

    private lateinit var detailDelegate: IDetailDelegate


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

        detailDelegate = NewDetailDelegate(
            uiProxy = DetailsUIProxy(binding),
            viewModel = viewModel
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailDelegate.register()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val characterId = intent.extras?.getInt("characterId")
        detailDelegate.fetchCharacter(characterId ?: 0)
    }
}