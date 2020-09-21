package dv.trubnikov.legends.feature_team.domain.interactors

import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.feature_team.domain.TeamRepository
import dv.trubnikov.legends.utils.domain.Result
import javax.inject.Inject

class TeamChangeInteractor @Inject constructor(
    private val teamRepository: TeamRepository,
    private val authHandler: AuthErrorHandler
) {

    suspend fun renameTeam(teamName: String): Result<TeamData> {
        return authHandler.with {
            teamRepository.renameTeam(teamName)
        }
    }

    suspend fun changeCaptain(captainId: Long): Result<TeamData> {
        return authHandler.with {
            teamRepository.changeCaptain(captainId)
        }
    }

    suspend fun kickMember(kickId: Long): Result<Unit> {
        return authHandler.with {
            teamRepository.kickMember(kickId)
        }
    }
}