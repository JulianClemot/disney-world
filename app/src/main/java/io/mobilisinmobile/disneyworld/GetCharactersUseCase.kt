package io.mobilisinmobile.disneyworld

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mobilisinmobile.disneyworld.new.DisneyService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class GetCharactersUseCase(applicationContext : DisneyApplication) {
    private val retrofitClient : DisneyService = Retrofit.Builder()
        .baseUrl(applicationContext.baseUrl)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType())
        )
        .client(
            OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build())
        .build().create(DisneyService::class.java)


    suspend fun getCharacters() : CharactersResult {
        return retrofitClient.getCharacters()
    }
}