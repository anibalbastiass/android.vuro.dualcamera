package com.anibalbastias.android.vuro.dualcamerapp.di.module

import com.anibalbastias.android.vuro.BuildConfig
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.dualcamerapp.presentation.VuroApplication
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class VuroAPIModule {

    companion object {
        const val CONNECT_TIMEOUT = 120L
        const val READ_TIMEOUT = 120L
        const val WRITE_TIMEOUT = 120L
    }


    @Provides
    @Singleton
    @Named("provideRetrofitHttpClient")
    fun provideHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

//    @Provides
//    @Singleton
//    @Named("provideForeignExchangeRetrofit")
//    fun provideRetrofit(@Named("provideRetrofitHttpClient") okHttpClient: OkHttpClient): Retrofit =
//        Retrofit.Builder()
//            .baseUrl(VuroApplication.appContext.getString(R.string.foreignexchange_endpoint))
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create(makeGson()))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//
//    private fun makeGson(): Gson {
//        return VuroAPIGSONManager.createGsonBuilder()
//            .setLenient()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            .create()
//    }
//
//    @Provides
//    fun provideForeignExchangeAPI(@Named("provideForeignExchangeRetrofit") retrofit: Retrofit): VuroApiService =
//        retrofit.create(VuroApiService::class.java)
}