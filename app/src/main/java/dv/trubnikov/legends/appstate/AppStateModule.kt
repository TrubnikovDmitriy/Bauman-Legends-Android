package dv.trubnikov.legends.appstate

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import dv.trubnikov.legends.utils.domain.appstate.AppStateResolver
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppStateModule {

    @Binds
    @Singleton
    abstract fun bindAppStateCenter(appStateCenter: AppStateCenterImpl): AppStateCenter

    @Binds
    @Singleton
    abstract fun bindAppStateResolver(appStateResolver: AppStateResolverImpl): AppStateResolver
}