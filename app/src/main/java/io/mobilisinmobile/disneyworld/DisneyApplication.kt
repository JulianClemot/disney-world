package io.mobilisinmobile.disneyworld

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

open class DisneyApplication : Application() {
    open val baseUrl = "https://api.disneyapi.dev"
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@DisneyApplication)
            // Load modules
            modules(appModule)
        }
    }
}