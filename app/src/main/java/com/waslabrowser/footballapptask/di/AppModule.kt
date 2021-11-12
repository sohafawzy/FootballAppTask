package com.waslabrowser.footballapptask.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.waslabrowser.data.remote.ApiService
import com.waslabrowser.data.remote.NetworkConstants
import com.waslabrowser.data.remote.NetworkDataSource
import com.waslabrowser.footballapptask.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.waslabrowser.data.local.FixtureDao
import com.waslabrowser.data.local.FixtureDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideBaseUrl() = NetworkConstants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-Auth-Token", BuildConfig.API_KEY)
                        .addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                })
                .addInterceptor(loggingInterceptor)
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES).build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-Auth-Token", BuildConfig.API_KEY)
                        .addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                })
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES).build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideNetworkDataSource(apiService: ApiService): NetworkDataSource {
        return NetworkDataSource(apiService)
    }

}