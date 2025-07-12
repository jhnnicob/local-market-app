package com.master.tech.soft.solutions.localmarketapp.di

import com.master.tech.soft.solutions.localmarketapp.data.remote.api.CartApi
import com.master.tech.soft.solutions.localmarketapp.data.remote.api.OrderApi
import com.master.tech.soft.solutions.localmarketapp.data.remote.api.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://your-api.com/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    @Singleton
    fun provideCartApi(retrofit: Retrofit): CartApi =
        retrofit.create(CartApi::class.java)

    @Provides
    @Singleton
    fun provideOrderApi(retrofit: Retrofit): OrderApi =
        retrofit.create(OrderApi::class.java)
}
