package com.rf.dropchallenge.di

import com.rf.dropchallenge.BuildConfig
import com.rf.dropchallenge.data.api.BeerApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val apiModule = module {

    single(named<OkHttpClient>()) {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(get(qualifier = named<OkHttpClient>()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeerApiService::class.java)
    }
}