package io.mobilisinmobile.disneyworld.new.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import io.mobilisinmobile.disneyworld.new.theme.DisneyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewDetailActivity : ComponentActivity() {
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            DisneyTheme {
                val state = viewModel.characterDetailState.collectAsStateWithLifecycle()
                DetailScreen(state.value)
                val lifecycleOwner = LocalLifecycleOwner.current
                LifecycleStartEffect(
                    viewModel.characterDetailState,
                    lifecycleOwner = lifecycleOwner
                ) {
                    viewModel.getCharacterDetail(intent.extras?.getInt("characterId"))
                    onStopOrDispose {}
                }
            }
        }
    }
}

@Composable
private fun DetailScreen(detailScreenState: DetailViewModel.DetailScreenState) {
    when (detailScreenState) {
        is DetailViewModel.DetailScreenState.Loading -> {
            LoadingContent()
        }

        is DetailViewModel.DetailScreenState.Error -> {
            ErrorContent(detailScreenState.errorMessage)
        }

        is DetailViewModel.DetailScreenState.Success -> {
            DetailContent(detailScreenState.character)
        }
    }

}

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp)
        )
    }
}

@Composable
fun ErrorContent(errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            color = Color.Red,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun DetailContent(character: Character) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.imageUrl)
                .transformations(CircleCropTransformation())
                .build(),
            contentDescription = "avatar",
            modifier = Modifier
                .padding(top = 16.dp)
                .size(56.dp),
            contentScale = ContentScale.Crop, // Optional: Crop the content
        )

        Text(
            text = character.name,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
        Text(
            text = "Séries Télés : ${character.tvShows.joinToString(", ")}",
            textAlign = TextAlign.Center,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
        Text(
            text = "Attractions Disney : ${character.parkAttractions.joinToString(", ")}",
            textAlign = TextAlign.Center,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}