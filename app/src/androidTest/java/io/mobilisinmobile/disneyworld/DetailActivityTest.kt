@file:OptIn(ExperimentalCoroutinesApi::class)

package io.mobilisinmobile.disneyworld

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.test.FakeImageLoaderEngine
import io.mobilisinmobile.disneyworld.detail.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

@OptIn(ExperimentalCoilApi::class)
class DetailActivityTest {
    private var robot = DetailActivityRobot()

    @Test
    fun should_on_create_display_character_detail_when_a_character_id_is_provided_to_the_screen_and_call_is_successful() {
        //Given
        //an stubbed api with fake json response
        val server = MockWebServer().apply {
            enqueue(
                MockResponse().apply {
                    setResponseCode(200)
                    setBody(API_RESPONSE)
                }
            )
            start(8080)
        }

        //a stubbed avatar image
        val expectedDrawable = ColorDrawable(Color.RED)
        val engine = FakeImageLoaderEngine.Builder()
            .intercept(
                "https://static.wikia.nocookie.net/disney/images/3/31/Profile_-_Baloo.jpeg",
                expectedDrawable
            )
            .default(ColorDrawable(Color.BLUE))
            .build()
        val imageLoader =
            ImageLoader.Builder(InstrumentationRegistry.getInstrumentation().targetContext)
                .components { add(engine) }
                .build()
        Coil.setImageLoader(imageLoader)

        //when
        launchActivity(450)

        //then
        robot.checkCharacterDetailIsDisplayed(expectedDrawable)

        server.shutdown()
    }

    @Test
    fun should_on_create_display_error_message_when_no_character_id_is_provided_to_screen() {
        //Given
        //an stubbed api with fake json response
        val server = MockWebServer().apply {
            enqueue(
                MockResponse().apply {
                    setResponseCode(200)
                    setBody(API_RESPONSE)
                }
            )
            start(8080)
        }

        //a stubbed avatar image
        val expectedDrawable = ColorDrawable(Color.RED)
        val engine = FakeImageLoaderEngine.Builder()
            .intercept(
                "https://static.wikia.nocookie.net/disney/images/3/31/Profile_-_Baloo.jpeg",
                expectedDrawable
            )
            .default(ColorDrawable(Color.BLUE))
            .build()
        val imageLoader =
            ImageLoader.Builder(InstrumentationRegistry.getInstrumentation().targetContext)
                .components { add(engine) }
                .build()
        Coil.setImageLoader(imageLoader)

        //when
        launchActivity(null)

        //then
        robot.checkErrorViewIsDisplayed()

        server.shutdown()
    }

    @Test
    fun should_on_create_display_error_message_when_when_a_character_id_is_provided_to_the_screen_and_call_is_failing() {
        //Given
        //an stubbed api with fake json response
        val server = MockWebServer().apply {
            enqueue(
                MockResponse().apply {
                    setResponseCode(204)
                }
            )
            start(8080)
        }

        //a stubbed avatar image
        val expectedDrawable = ColorDrawable(Color.RED)
        val engine = FakeImageLoaderEngine.Builder()
            .intercept(
                "https://static.wikia.nocookie.net/disney/images/3/31/Profile_-_Baloo.jpeg",
                expectedDrawable
            )
            .default(ColorDrawable(Color.BLUE))
            .build()
        val imageLoader =
            ImageLoader.Builder(InstrumentationRegistry.getInstrumentation().targetContext)
                .components { add(engine) }
                .build()
        Coil.setImageLoader(imageLoader)

        //when
        launchActivity(450)

        //then
        robot.checkErrorViewIsDisplayed()

        server.shutdown()
    }
    private fun launchActivity(characterId: Int?): ActivityScenario<DetailActivity> {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            DetailActivity::class.java
        ).also {
            characterId?.let { id ->
                it.putExtra("characterId", id)
            }
        }
        return ActivityScenario.launch(intent)
    }

    companion object {
        const val API_RESPONSE = """
        {
  "info": {
    "count": 1,
    "totalPages": 1,
    "previousPage": null,
    "nextPage": null
  },
  "data": {
    "_id": 450,
    "films": [
      "The Jungle Book",
      "The Jungle Book 2",
      "Rudyard Kipling's The Jungle Book",
      "The Jungle Book: Mowgli's Story",
      "Mickey's Magical Christmas: Snowed in at the House of Mouse",
      "Mickey's House of Villains",
      "The Lion King 1½",
      "Meet the Robinsons",
      "The Jungle Book (2016 film)"
    ],
    "shortFilms": [],
    "tvShows": [
      "The Mouse Factory",
      "TaleSpin"
    ],
    "videoGames": [
      "TaleSpin (NES video game)",
      "TaleSpin (Sega Genesis Video Game)",
      "The Jungle Book (video game)",
      "The Jungle Book Groove Party",
      "Disney Universe",
      "Kinect Disneyland Adventures",
      "Disney Infinity (series)",
      "Disney Crossy Road",
      "Disney Emoji Blitz",
      "Disney Magic Kingdoms",
      "Kingdom Hearts Union χ",
      "Disney Heroes: Battle Mode",
      "Disney Sorcerer's Arena",
      "Just Dance: Disney Party"
    ],
    "parkAttractions": [
      "The Disney Afternoon Live!: Plane Crazy",
      "Mickey's Magical TV World"
    ],
    "allies": [],
    "enemies": [],
    "sourceUrl": "https://disney.fandom.com/wiki/Baloo",
    "name": "Baloo",
    "imageUrl": "https://static.wikia.nocookie.net/disney/images/3/31/Profile_-_Baloo.jpeg",
    "createdAt": "2021-04-12T01:35:02.338Z",
    "updatedAt": "2021-12-20T20:39:21.786Z",
    "url": "https://api.disneyapi.dev/characters/450",
    "__v": 0
  }
}
        """
    }
}