package dv.trubnikov.legends.core_api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dv.trubnikov.legends.core_api.team.api.TeamApi
import dv.trubnikov.legends.core_api.user.api.UserApi
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    fun provideTeamApi(retrofit: Retrofit): TeamApi = retrofit.create(TeamApi::class.java)
}