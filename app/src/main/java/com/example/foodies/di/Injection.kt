package com.example.foodies.di

import com.example.foodies.data.FoodiesRepository
import com.example.foodies.network.FoodiesApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Injection {
    private const val baseUrl = "https://anika1d.github.io/WorkTestServer/"

    @Provides
    @Singleton
    fun provideApi(): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideFoodiesApiService(retrofit: Retrofit): FoodiesApiService =
        retrofit.create(FoodiesApiService::class.java)

    @Provides
    @Singleton
    fun provideFoodiesRepository(foodiesApiService: FoodiesApiService): FoodiesRepository =
        FoodiesRepository(foodiesApiService)
}