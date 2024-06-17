@file:OptIn(ExperimentalCoroutinesApi::class)
package io.mobilisinmobile.disneyworld

import io.mobilisinmobile.disneyworld.detail.Character
import io.mobilisinmobile.disneyworld.fixtures.FakeCharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class GetCharacterUseCaseTest {

    private lateinit var characterRepository: CharacterRepository
    private lateinit var getCharacterUseCase: GetCharacterUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        characterRepository = FakeCharacterRepository()
        getCharacterUseCase = GetCharacterUseCase(characterRepository)
    }

    @Test
    fun `should GetCharacter return Character object when a character id was provided`() = runTest {
        val characterId = 450
        val result = getCharacterUseCase.getCharacter(characterId)
        assertEquals(
            Character(
                name = "Mickey Mouse",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d4/Mickey_Mouse.png/220px-Mickey_Mouse.png",
                tvShows = listOf("Mickey Mouse Clubhouse", "Mickey Mouse Works"),
                films = listOf("Fantasia", "The Sorcerer's Apprentice"),
                parkAttractions = listOf("Mickey's Toontown"),
                shortFilms = listOf("Steamboat Willie"),
            ), result
        )
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `should GetCharacter throw an exception when no character id was provided`() {
        assertThrows(IllegalArgumentException::class.java) {
            runTest {
                getCharacterUseCase.getCharacter(null)
            }
        }
    }
}

class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}