plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.mobilisinmobile.disneyworld"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.mobilisinmobile.disneyworld"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "io.mobilisinmobile.disneyworld.DisneyMockTestRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(platform(libs.okhttpBom))
    implementation(libs.okhttp)
    implementation(libs.okhttpLoggingInterceptor)
    implementation(platform(libs.composeBom))
    implementation(libs.composeMaterial3)
    implementation(libs.composeActivity)
    implementation(libs.composeToolingPreview)
    implementation(libs.appcompat)
    implementation(libs.coil)
    implementation(libs.coilCompose)
    implementation(libs.retrofit)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core.ktx)
    implementation(libs.kotlinSerializationJson)
    implementation(libs.retrofitKotlinSerializationConverter)
    implementation(platform(libs.koinBom))
    implementation(libs.koinCore)
    implementation(libs.koinAndroid)
    implementation(libs.lifecycle.runtime.compose.android)
    debugImplementation(libs.composeToolingDebug)
    debugImplementation(libs.composeTestManifest)
    testImplementation(libs.junit)
    testImplementation(libs.coroutinesTest)
    androidTestImplementation(libs.composeTest)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.coilTest)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.mockwebserver)
}