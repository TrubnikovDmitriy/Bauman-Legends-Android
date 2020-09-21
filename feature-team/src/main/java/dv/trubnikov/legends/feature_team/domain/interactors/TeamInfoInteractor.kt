package dv.trubnikov.legends.feature_team.domain.interactors

import dv.trubnikov.legends.core_credential.user.UserStore
import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.feature_team.domain.TeamRepository
import dv.trubnikov.legends.utils.domain.Result
import dv.trubnikov.legends.utils.domain.appstate.AppState.NEED_TEAM
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import dv.trubnikov.legends.utils.domain.appstate.AppStateResolver
import javax.inject.Inject

class TeamInfoInteractor @Inject constructor(
    private val teamRepository: TeamRepository,
    private val userStore: UserStore,
    private val authHandler: AuthErrorHandler,
    private val stateCenter: AppStateCenter,
    private val stateResolver: AppStateResolver
) {

    suspend fun getTeamInfo(): Result<TeamData> {
        return authHandler.with {
            val result = teamRepository.getTeamInfo()
            when(result) {
                is Result.Success -> {
                    val team = result.data
                    if (team != null) {
                        // Update user data with team id
                        val userWithTeam = userStore.value?.copy(teamId = team.teamId)
                        userStore.value = userWithTeam
                        stateCenter.removeState(NEED_TEAM)
                        return@with Result.Success(team)
                    } else {
                        stateCenter.addState(NEED_TEAM)
                        return@with Result.Error(stateResolver.messageForState(NEED_TEAM))
                    }
                }
                is Result.Error -> return@with result
            }
        }
    }

    suspend fun getTeamMembers(): Result<List<UserData>> {
        return authHandler.with {
            teamRepository.getTeamMembers()
        }
    }

    suspend fun getAllTeams(): Result<List<TeamData>> {
        return teamRepository.getAllTeams()
    }
}