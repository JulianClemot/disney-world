package io.mobilisinmobile.disneyworld

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.mobilisinmobile.disneyworld.databinding.ActivityDetailBinding
import io.mobilisinmobile.disneyworld.proxies.impl.DetailsUIProxy
import io.mobilisinmobile.disneyworld.proxies.impl.DisneyServiceProxy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    @VisibleForTesting
    var characterId: Int = 0

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

        characterId = intent.extras?.getInt("characterId", 0) ?: 0

        detailDelegate = DetailDelegate(
            serviceProxy = DisneyServiceProxy(applicationContext as DisneyApplication),
            uiProxy = DetailsUIProxy(binding)
        )
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            detailDelegate.fetchCharacter(characterId)
        }
    }
}