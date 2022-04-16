package no.vegardaaberge.di

import no.vegardaaberge.controllers.AuthController
import no.vegardaaberge.data.sources.AuthDataSource
import no.vegardaaberge.data.sources.AuthDataSourceImpl
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("MyPlaygroupDatabase")
    }
    single<AuthDataSource> {
        AuthDataSourceImpl(get())
    }
    single {
        AuthController(get())
    }
}