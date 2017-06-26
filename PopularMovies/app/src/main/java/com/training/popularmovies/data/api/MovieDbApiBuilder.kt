package com.training.popularmovies.data.api

import com.training.popularmovies.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by moacir on 26/02/17.
 */
class MovieDbApiBuilder {

    fun build(): MovieDbApi {
        val httpClient = OkHttpClient.Builder()
        httpClient
                .addInterceptor(ApiKeyInterceptor(BuildConfig.MOVIE_DB_API_KEY))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.MOVIE_DB_ENDPOINT)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(MovieDbApi::class.java)
    }

}

class ApiKeyInterceptor(val apiKey: String) : Interceptor {

    private object PARAMS {
        val API_KEY_PARAM = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl
                .newBuilder()
                .addQueryParameter(PARAMS.API_KEY_PARAM, apiKey).build()

        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}