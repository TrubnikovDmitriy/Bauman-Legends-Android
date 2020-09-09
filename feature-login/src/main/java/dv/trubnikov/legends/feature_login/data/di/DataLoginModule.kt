package dv.trubnikov.legends.feature_login.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.feature_login.data.BackendLoginRepository
import dv.trubnikov.legends.feature_login.data.api.LoginApi
import dv.trubnikov.legends.feature_login.domain.LoginRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataLoginModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(@ApplicationContext context: Context, loginApi: LoginApi): LoginRepository {
        return BackendLoginRepository(context, loginApi)
    }
}