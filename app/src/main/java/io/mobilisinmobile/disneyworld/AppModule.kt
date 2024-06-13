package io.mobilisinmobile.disneyworld

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mobilisinmobile.disneyworld.new.CharacterRepository
import io.mobilisinmobile.disneyworld.new.CharacterRepositoryImpl
import io.mobilisinmobile.disneyworld.new.NewDisneyService
import io.mobilisinmobile.disneyworld.new.NewGetCharacterUseCase
import io.mobilisinmobile.disneyworld.new.detail.DetailViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single(named("baseUrl")) { "https://api.disneyapi.dev/" }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        val json = Json {
            ignoreUnknownKeys = true
        }
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(NewDisneyService::class.java) }

    single<CharacterRepository> { CharacterRepositoryImpl(get()) }

    single { NewGetCharacterUseCase(get()) }

    viewModel { DetailViewModel(get()) }
}