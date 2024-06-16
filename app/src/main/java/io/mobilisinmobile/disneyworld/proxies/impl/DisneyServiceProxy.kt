package io.mobilisinmobile.disneyworld.proxies.impl

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mobilisinmobile.disneyworld.CharacterResult
import io.mobilisinmobile.disneyworld.DisneyApplication
import io.mobilisinmobile.disneyworld.DisneyService
import io.mobilisinmobile.disneyworld.proxies.definition.IDisneyServiceProxy
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class DisneyServiceProxy(private val application: DisneyApplication) : IDisneyServiceProxy {
    private val retrofitClient : DisneyService by lazy {
        Retrofit.Builder()
            .baseUrl(application.baseUrl)
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
                    .build()
            )
            .build().create(DisneyService::class.java)
    }

    override suspend fun getCharacter(characterId: Int): CharacterResult {
        return retrofitClient.getCharacter(characterId)
    }
}