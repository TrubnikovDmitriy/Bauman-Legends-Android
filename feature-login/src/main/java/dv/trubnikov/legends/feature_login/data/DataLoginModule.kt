package dv.trubnikov.legends.feature_login.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.feature_login.data.repos.BackendLoginRepository
import dv.trubnikov.legends.feature_login.domain.LoginRepository
import dv.trubnikov.legends.api.user.api.UserApi
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataLoginModule {

    @Provides
    @Singleton
    fun provideLoginRepository(@ApplicationContext context: Context, userApi: UserApi): LoginRepository {
        return BackendLoginRepository(context, userApi)
    }
}