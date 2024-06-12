package io.mobilisinmobile.disneyworld

import org.koin.core.qualifier.named
import org.koin.dsl.module

val testModule = module {
    single(named("baseUrl")) { "http://localhost:8080" }
}