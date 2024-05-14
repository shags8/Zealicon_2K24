package com.zealicon_2024.di

import com.zealicon_2024.api.AuthInterceptor
import com.zealicon_2024.api.PaymentAPI
import com.zealicon_2024.api.SignupAPI
import com.zealicon_2024.utils.Constants.BASE_URL
import com.zealicon_2024.utils.RemoteConfigHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import retrofit2.Retrofit.Builder
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier



@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        RemoteConfigHelper.fetchAndActivate()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }
    @Singleton
    @Provides
    fun providesOkHTTPClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providePaymentAPI(
        retrofitBuilder: Builder
    ): PaymentAPI {
        return retrofitBuilder.build().create(PaymentAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideSignupAPI(retrofitBuilder: Builder): SignupAPI {
        return retrofitBuilder.build().create(SignupAPI::class.java)
    }
}