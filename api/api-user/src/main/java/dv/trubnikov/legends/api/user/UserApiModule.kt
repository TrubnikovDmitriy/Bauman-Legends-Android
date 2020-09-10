package dv.trubnikov.legends.api.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dv.trubnikov.legends.api.user.api.UserApi
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
object UserApiModule {
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}