package com.moon.map_application.di

import com.moon.map_application.data.remote.ApiEndPoint.Companion.BASE_URL
import com.moon.map_application.data.remote.ApiEndPoint.Companion.BASE_URL_FOR_RESERVATION
import com.moon.map_application.data.remote.apiService.ICarService
import com.moon.map_application.data.remote.apiService.IReservationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCarService(retrofit: Retrofit): ICarService =
        retrofit.create(ICarService::class.java)

    @Singleton
    @Provides
    fun provideCarServicePost(
        @Named("reservation") retrofit: Retrofit
    ): IReservationService =
        retrofit.create(IReservationService::class.java)


    @Singleton
    @Provides
    @Named("reservation")
    fun provideRetrofitWithToken(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_FOR_RESERVATION)
            .client(
                OkHttpClient.Builder().addNetworkInterceptor(HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                        level = HttpLoggingInterceptor.Level.HEADERS
                    })
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder().addHeader(
                            "Authorization",
                            "Bearer df7c313b47b7ef87c64c0f5f5cebd6086bbb0fa"
                        ).build()
                        chain.proceed(request)
                    }.build()
            )
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}