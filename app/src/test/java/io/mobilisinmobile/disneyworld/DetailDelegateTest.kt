package io.mobilisinmobile.disneyworld

import android.view.View
import io.mobilisinmobile.disneyworld.detail.DetailDelegate
import io.mobilisinmobile.disneyworld.detail.DetailViewModel
import io.mobilisinmobile.disneyworld.fake.FakeDetailsUIProxy
import io.mobilisinmobile.disneyworld.fake.FakeDisneyServiceProxy
import io.mobilisinmobile.utils.MainDispatcherRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailDelegateTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var detailDelegate: DetailDelegate

    private lateinit var fakeUiProxy: FakeDetailsUIProxy
    private lateinit var fakeServiceProxy: FakeDisneyServiceProxy

    private val defaultCharacterId = 10
    private val defaultCharacterResult = RestCharacterResult(
        info = RestInfo(
            count = 1,
            previousPage = "",
            nextPage = "",
            totalPages = 1,
        ),
        character = RestCharacterData(
            id = 10,
            name = "Mickey Mouse",
            imageUrl = "https://www.google.com",
            tvShows = listOf("Mickey Mouse Clubhouse", "Mickey Mouse Shorts"),
            parkAttractions = listOf("Mickey's PhilharMagic", "Mickey's Toontown"),
            films = emptyList(),
            videoGames = emptyList(),
            shortFilms = emptyList(),
            updatedAt = "2021-01-01",
            url = "https://www.google.com",
            sourceUrl = "https://www.google.com",
            allies = emptyList(),
            enemies = emptyList(),
            createdAt = "2021-01-01",
        )
    )

    @Before
    fun setup() {
        fakeUiProxy = FakeDetailsUIProxy()

        fakeServiceProxy = FakeDisneyServiceProxy().also {
            it.expectedCharacterId = defaultCharacterId
            it.characterResult = defaultCharacterResult
        }

        val viewModel = DetailViewModel(
            GetCharacterUseCase(
                CharacterRepositoryImpl(
                    fakeServiceProxy
                ),
            ),
        )

        detailDelegate = DetailDelegate(
            uiProxy = fakeUiProxy,
            viewModel = viewModel
        )
    }

    @Test
    fun should_fetch_character_details_when_service_returns_success() {
        runBlocking {
            // When
            detailDelegate.register()
            detailDelegate.fetchCharacter(characterId = defaultCharacterId)

            // Then
            assert(fakeUiProxy.contentVisibility == View.VISIBLE)
            assert(fakeUiProxy.errorViewVisibility == View.GONE)
            assert(fakeUiProxy.nameText == defaultCharacterResult.character.name)
            assert(fakeUiProxy.avatarImageUrl == defaultCharacterResult.character.imageUrl)
            assert(fakeUiProxy.tvShowsList == defaultCharacterResult.character.tvShows)
            assert(fakeUiProxy.disneyAttractions == defaultCharacterResult.character.parkAttractions)
            assert(fakeUiProxy.errorLabelText == null)
        }
    }

    @Test
    fun should_not_fetch_character_details_when_service_returns_failure() {
        runBlocking {
            // Given
            detailDelegate.register()
            fakeServiceProxy.shouldThrowException = true

            // When
            detailDelegate.fetchCharacter(characterId = defaultCharacterId)
            detailDelegate.register()

            // Then
            assert(fakeUiProxy.contentVisibility == View.GONE)
            assert(fakeUiProxy.errorViewVisibility == View.VISIBLE)
            assert(fakeUiProxy.errorLabelText == "Erreur lors de la récupération des données")
            assert(fakeUiProxy.nameText == null)
            assert(fakeUiProxy.avatarImageUrl == null)
            assert(fakeUiProxy.tvShowsList == null)
            assert(fakeUiProxy.disneyAttractions == null)
        }
    }
}