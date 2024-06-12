package io.mobilisinmobile.disneyworld

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class DetailActivityRobot {

    private val avatar: ViewInteraction by lazy { Espresso.onView(withId(R.id.avatar)) }
    private val characterName: ViewInteraction by lazy { Espresso.onView(withId(R.id.name)) }
    private val characterTvShows: ViewInteraction by lazy { Espresso.onView(withId(R.id.tv_shows)) }
    private val characterDisneyAttractions: ViewInteraction by lazy { Espresso.onView(withId(R.id.disney_attractions)) }
    private val mainContent: ViewInteraction by lazy { Espresso.onView(withId(R.id.content)) }
    private val errorView: ViewInteraction by lazy { Espresso.onView(withId(R.id.error_view)) }
    private val errorLabel: ViewInteraction by lazy { Espresso.onView(withId(R.id.error_label)) }


    private fun checkAvatarIsLoadedAndDisplayingRightDrawable(expectedDrawable: Drawable) {
        avatar.check(matches(withDrawable(expectedDrawable)))
    }

    private fun characterNameIsDisplayed(name: String) {
        characterName.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        characterName.check(matches(withText(name)))
    }

    private fun characterTvShowsIsDisplayed(expectedText: String) {
        characterTvShows.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        characterTvShows.check(matches(withText(expectedText)))
    }

    private fun characterDisneyAttractionsIsDisplayed(expectedText: String) {
        characterDisneyAttractions.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        characterDisneyAttractions.check(matches(withText(expectedText)))
    }

    private fun mainContentIsDisplayed() {
        mainContent.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    private fun mainContentIsHidden() {
        mainContent.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    private fun errorViewIsDisplayed() {
        errorView.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }

    private fun errorViewIsHidden() {
        errorView.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    fun checkCharacterDetailIsDisplayed(expectedDrawable: Drawable) {
        mainContentIsDisplayed()
        characterNameIsDisplayed("Baloo")
        characterTvShowsIsDisplayed("Séries Télés : The Mouse Factory, TaleSpin")
        characterDisneyAttractionsIsDisplayed("Attractions Disney : The Disney Afternoon Live!: Plane Crazy, Mickey's Magical TV World")
        checkAvatarIsLoadedAndDisplayingRightDrawable(expectedDrawable)
        errorViewIsHidden()
    }

    fun checkErrorViewIsDisplayed() {
        errorViewIsDisplayed()
        mainContentIsHidden()
        errorLabel.check(matches(withText("Erreur lors de la récupération des données")))
    }

    private fun withDrawable(expectedDrawable : Drawable) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("ImageView with drawable same as drawable")
        }

        override fun matchesSafely(view: View): Boolean {

            return view is ImageView && view.drawable.toBitmap(width = 100, height = 100).sameAs(expectedDrawable.toBitmap(width = 100, height = 100))
        }
    }

}