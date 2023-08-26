package com.example.ihsanstask.data.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.ihsanstask.BuildConfig
import com.example.ihsanstask.data.endpoints.Endpoints
import com.example.ihsanstask.utils.LocaleHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
object ApiModules {

    private const val REQUEST_TIME_OUT: Long = 120

    @Provides
    fun provideHeadersInterceptor(
        localeHelper: LocaleHelper
    ): Interceptor = Interceptor { chain ->

        chain.proceed(
            chain.request().newBuilder()
                .addHeader("x-localization", if (localeHelper.isEnglish) "en" else "ar")
                .addHeader("Accept", "application/json")
//                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("Cache-Control", "max-age=0")
                .build()
        )
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    @Provides
    fun provideChuckInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250_000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(true)
            .build()

    @Provides
    fun provideOkHttpClient(
        headersInterceptor: Interceptor,
        chuckInterceptor: ChuckerInterceptor,
        logging: HttpLoggingInterceptor
    ): OkHttpClient = if (BuildConfig.DEBUG)
        OkHttpClient.Builder()
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor)
            .addNetworkInterceptor(logging)
            .addInterceptor(chuckInterceptor)
            .build()
    else
        OkHttpClient.Builder()
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor)
            .build()

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .serializeNulls() // To allow sending null values
            .create()
    }

    @Provides
    fun provideEndpoint(gson: Gson, okHttpClient: OkHttpClient): Endpoints {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(Endpoints::class.java)
    }
}
