package io.mobilisinmobile.disneyworld.new

import io.mobilisinmobile.disneyworld.CharactersResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DisneyService {


    @GET("/character")
    suspend fun getCharacters(@Query("page") page: Int = 1, @Query("pageSize") pageSize: Int = 50) : CharactersResult


    @GET("/character/{id}")
    suspend fun getCharacter(@Path("id") characterId: Int) : RestCharacterResult
}