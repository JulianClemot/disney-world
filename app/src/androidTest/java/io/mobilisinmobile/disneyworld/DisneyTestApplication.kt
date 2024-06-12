package io.mobilisinmobile.disneyworld

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner

class DisneyTestApplication : DisneyApplication() {
    override val baseUrl = "http://localhost:8080"
}

class DisneyMockTestRunner : AndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, DisneyTestApplication::class.java.name, context)
    }
}