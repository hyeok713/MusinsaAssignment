package com.hyeokbeom.data.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.hyeokbeom.data.datasource.MusinsaAPI
import com.hyeokbeom.shared.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * NetworkModule
 * [Network 통신을 위한 작업]
 * 최종적으로 Retrofit 인스턴스 생성 후 DataModule 로 전달
 * @see DataModule
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        const val endPoint = "https://meta.musinsa.com/"
    }

    /**
     * loggerProvider
     * [Logger instance 생성]
     * - Json 형태의 response message 커스텀 로그 사용
     */
    @Provides
    @Singleton
    fun loggerProvider() = HttpLoggingInterceptor.Logger { message ->
        val label = "LOG RESULT"
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(JsonParser.parseString(message))
                Log.i("$label : $prettyPrintJson")
            } catch (m: JsonSyntaxException) {
                Log.e("$label : $m")
            }
        } else {
            Log.w("$label : $message")
        }
    }

    /**
     * loggingInterceptorProvider
     * [Logger Interceptor instance 생성]
     * @param logger
     * - BODY Level 의 정보만 취함
     */
    @Provides
    @Singleton
    fun loggingInterceptorProvider(logger: HttpLoggingInterceptor.Logger) =
        HttpLoggingInterceptor(logger).apply { level = HttpLoggingInterceptor.Level.BODY }

    /**
     * okHttpClientProvider
     * [OkHttpClient instance 생성]
     * @param networkInterceptor
     */
    @Provides
    @Singleton
    fun okHttpClientProvider(networkInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(networkInterceptor)
            .build()


    /**
     * musinsaApiProvider
     * [MusinsaAPI instance 생성]
     * @param okHttpClient
     *
     * 생성된 instance 는 MusinsaRepositoryImpl(구현부)로 의존성 주입
     */
    @Singleton
    @Provides
    fun musinsaApiProvider(okHttpClient: OkHttpClient) =
        createAPI(endPoint, okHttpClient, MusinsaAPI::class.java) as MusinsaAPI

    /**
     * createAPI()
     * [API service instance 생성]
     * @param url 적용할 base url
     * @param client OkHttpClient, 제공받을 클라이언트 사용
     * @param cls Service Interface
     */
    private fun createAPI(
        url: String,
        client: OkHttpClient,
        cls: Class<*>,
    ): Any {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(cls)
    }
}