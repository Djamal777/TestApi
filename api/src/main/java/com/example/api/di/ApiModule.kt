package com.example.api.di

import com.example.api.data.remote.IProBonusApi
import com.example.api.data.repository.BonusRepositoryImpl
import com.example.api.domain.repository.BonusRepository
import com.example.api.other.Constants.ACCESS_KEY
import com.example.api.other.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApi(): IProBonusApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("AccessKey", ACCESS_KEY)
                    }.build())
                }
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IProBonusApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api:IProBonusApi):BonusRepository{
        return BonusRepositoryImpl(api)
    }

}