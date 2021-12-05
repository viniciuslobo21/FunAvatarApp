package com.lobo.userlist

import android.app.Application
import com.lobo.userlist.core.di.appModule
import com.lobo.userlist.core.di.repositoryModule
import com.lobo.userlist.core.di.useCaseModule
import com.lobo.userlist.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class UserListApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UserListApp)
            androidLogger()
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    useCaseModule,
                    repositoryModule

                )
            )
        }
    }
}
