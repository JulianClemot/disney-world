package io.mobilisinmobile.disneyworld

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertRangeInfoEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import io.mobilisinmobile.disneyworld.new.detail.Character
import io.mobilisinmobile.disneyworld.new.detail.DetailScreen
import io.mobilisinmobile.disneyworld.new.detail.DetailViewModel
import io.mobilisinmobile.disneyworld.new.theme.DisneyTheme
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun should_detail_screen_show_character_detail_when_state_is_success() {
        val character = Character(
            name = "Mickey Mouse",
            imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d4/Mickey_Mouse.png/220px-Mickey_Mouse.png",
            tvShows = listOf("Mickey Mouse Clubhouse", "Mickey Mouse Works"),
            films = listOf("Fantasia", "The Sorcerer's Apprentice"),
            parkAttractions = listOf("Mickey's Toontown"),
            shortFilms = listOf("Steamboat Willie"),
        )

        // Start the app
        composeTestRule.setContent {
            DisneyTheme {
                DetailScreen(DetailViewModel.DetailScreenState.Success(character))
            }
        }

        composeTestRule.apply {
            onNodeWithText("Mickey Mouse").assertIsDisplayed()
            onNodeWithText("Séries Télés : Mickey Mouse Clubhouse, Mickey Mouse Works").assertIsDisplayed()
            onNodeWithText("Attractions Disney : Mickey's Toontown").assertIsDisplayed()
        }
    }

    @Test
    fun should_detail_screen_show_loader_when_state_is_loading() {
        // Start the app
        composeTestRule.setContent {
            DisneyTheme {
                DetailScreen(DetailViewModel.DetailScreenState.Loading)
            }
        }

        composeTestRule.apply {
            val semantic = SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo)

            composeTestRule.onAllNodes(semantic, true)
                .assertCountEquals(1)[0].assertRangeInfoEquals(ProgressBarRangeInfo.Indeterminate)
        }
    }

    @Test
    fun should_detail_screen_show_error_message_when_state_is_error() {
        // Start the app
        composeTestRule.setContent {
            DisneyTheme {
                DetailScreen(DetailViewModel.DetailScreenState.Error("an error happened"))
            }
        }

        composeTestRule.apply {
            onNodeWithText("an error happened").assertIsDisplayed()
        }
    }
}