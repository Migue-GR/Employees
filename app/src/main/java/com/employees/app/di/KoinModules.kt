package com.employees.app.di

import android.app.Application
import android.content.Context
import com.employees.BuildConfig
import com.employees.datasource.local.AppDataBase
import com.employees.datasource.remote.EmployeesApi
import com.employees.domain.GetCurrentUserUseCase
import com.employees.domain.GetEmployeesUseCase
import com.employees.domain.LoginUseCase
import com.employees.domain.LogoutUseCase
import com.employees.repository.EmployeeRepository
import com.employees.repository.UserRepository
import com.employees.viewmodel.HomeViewModel
import com.employees.viewmodel.LoginViewModel
import com.employees.viewmodel.SplashViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelsModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
}

val useCasesModule = module {
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { GetEmployeesUseCase(get()) }
}

val repositoriesModule = module {
    factory { UserRepository(get()) }
    factory { EmployeeRepository(get(), get()) }
}

val remoteDataSourceModule: Module = module {
    fun providePokeService(retrofit: Retrofit) = retrofit.create(EmployeesApi::class.java)
    single { providePokeService(get()) }
}

val localDataSourceModule = module {
    fun provideAppDatabase(context: Context) = AppDataBase.getInstance(context)
    single { provideAppDatabase(androidApplication()) }
}

val netModule: Module = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(
        cache: Cache
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}