package dv.trubnikov.legends.feature_team.domain.interactors

import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.feature_team.domain.TeamRepository
import dv.trubnikov.legends.utils.domain.Result
import dv.trubnikov.legends.utils.domain.appstate.AppState.NEED_TEAM
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import javax.inject.Inject

class TeamCreateInteractor @Inject constructor(
    private val teamRepository: TeamRepository,
    private val authHandler: AuthErrorHandler,
    private val stateCenter: AppStateCenter
) {

    suspend fun createTeam(teamName: String): Result<TeamData> {
        return authHandler.with {
            val result = teamRepository.createTeam(teamName)
            if (result is Result.Success) stateCenter.removeState(NEED_TEAM)
            return@with result
        }
    }

    suspend fun joinToTeam(teamId: Long, inviteCode: String): Result<TeamData> {
        return authHandler.with {
            val result = teamRepository.joinToTeam(teamId, inviteCode)
            if (result is Result.Success) stateCenter.removeState(NEED_TEAM)
            return@with result
        }
    }

    suspend fun leaveTeam(): Result<Unit> {
        return authHandler.with {
            val result = teamRepository.leaveTeam()
            if (result is Result.Success) stateCenter.addState(NEED_TEAM)
            return@with result
        }
    }
}