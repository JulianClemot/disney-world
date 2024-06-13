package io.mobilisinmobile.disneyworld.new.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mobilisinmobile.disneyworld.new.GetCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val getCharacterUseCase: GetCharacterUseCase) : ViewModel() {
    val characterDetailState: StateFlow<DetailScreenState>
        get() = _characterDetailState
    private val _characterDetailState =
        MutableStateFlow<DetailScreenState>(DetailScreenState.Loading)


    fun getCharacterDetail(characterId: Int?) {
        viewModelScope.launch {
            _characterDetailState.emit(DetailScreenState.Loading)
            try {
                val character = getCharacterUseCase.getCharacter(characterId)
                _characterDetailState.emit(DetailScreenState.Success(character))
            } catch (e: Exception) {
                _characterDetailState.emit(
                    DetailScreenState.Error("Erreur lors de la récupération des données")
                )
            }
        }
    }

    sealed class DetailScreenState {
        data object Loading : DetailScreenState()
        data class Error(val errorMessage: String) : DetailScreenState()
        data class Success(val character: Character) : DetailScreenState()
    }
}