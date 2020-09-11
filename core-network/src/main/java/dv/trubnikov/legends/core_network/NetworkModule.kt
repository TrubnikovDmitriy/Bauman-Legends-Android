package dv.trubnikov.legends.core_network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dv.trubnikov.legends.core_network.cookie.CookieController
import dv.trubnikov.legends.core_network.cookie.CookieStore
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val BACKEND_URL = "http://192.168.1.108:8080"

    @Provides
    internal fun provideCookieController(cookieStore: CookieStore): CookieJar {
        return CookieController(cookieStore)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cookieController: CookieJar): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(cookieController)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}

