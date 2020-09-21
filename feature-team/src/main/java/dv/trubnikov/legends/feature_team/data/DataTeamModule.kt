package dv.trubnikov.legends.feature_team.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dv.trubnikov.legends.feature_team.data.repos.BackendTeamRepository
import dv.trubnikov.legends.feature_team.domain.TeamRepository

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataTeamModule {
    @Binds
    abstract fun bindTeamRepository(impl: BackendTeamRepository): TeamRepository
}