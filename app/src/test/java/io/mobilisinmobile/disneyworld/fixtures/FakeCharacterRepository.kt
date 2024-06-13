package io.mobilisinmobile.disneyworld.fixtures

import io.mobilisinmobile.disneyworld.new.CharacterRepository
import io.mobilisinmobile.disneyworld.new.RestCharacterData
import io.mobilisinmobile.disneyworld.new.RestCharacterResult
import io.mobilisinmobile.disneyworld.new.RestInfo

class FakeCharacterRepository : CharacterRepository {

    override suspend fun getCharacter(characterId: Int): RestCharacterResult {
        return fakeCharacterResult
    }

    companion object {
        val fakeCharacterResult = RestCharacterResult(
            info = RestInfo(
                count = 1
            ),
            character = RestCharacterData(
                id = 1,
                name = "Mickey Mouse",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d4/Mickey_Mouse.png/220px-Mickey_Mouse.png",
                tvShows = listOf("Mickey Mouse Clubhouse", "Mickey Mouse Works"),
                allies = listOf("Minnie Mouse"),
                enemies = listOf("Pete"),
                createdAt = "1928-11-18",
                films = listOf("Fantasia", "The Sorcerer's Apprentice"),
                parkAttractions = listOf("Mickey's Toontown"),
                shortFilms = listOf("Steamboat Willie"),
                sourceUrl = "https://en.wikipedia.org/wiki/Mickey_Mouse",
                updatedAt = "2021-07-01",
                url = "https://disney.fandom.com/wiki/Mickey_Mouse",
                videoGames = listOf("Kingdom Hearts", "Disney Infinity")
            )
        )
    }
}