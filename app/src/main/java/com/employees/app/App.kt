package com.employees.app

import android.app.Application
import com.employees.BuildConfig
import com.employees.app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                netModule,
                localDataSourceModule,
                remoteDataSourceModule,
                repositoriesModule,
                viewModelsModule
            )
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + ':' + element.lineNumber + '#' + element.methodName + "()"
                }
            })
        }
    }
}