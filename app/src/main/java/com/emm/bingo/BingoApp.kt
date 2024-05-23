package com.emm.bingo

import android.app.Application
import com.emm.bingo.features.auth.di.loginModule
import com.emm.bingo.features.shared.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BingoApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger() // fake route
            androidContext(this@BingoApp) // second route // aca // tmr // no funciona
            modules(
                sharedModule,
                loginModule
            )
        }
    }
}