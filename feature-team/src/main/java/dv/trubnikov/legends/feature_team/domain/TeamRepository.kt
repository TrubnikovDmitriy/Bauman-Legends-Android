package dv.trubnikov.legends.feature_team.domain

import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.utils.domain.Result

interface TeamRepository {
    suspend fun createTeam(teamName: String): Result<TeamData>
    suspend fun renameTeam(teamName: String): Result<TeamData>
    suspend fun joinToTeam(teamId: Long, inviteCode: String): Result<TeamData>
    suspend fun getTeamInfo(): Result<TeamData?>
    suspend fun getTeamMembers(): Result<List<UserData>>
    suspend fun changeCaptain(captainId: Long): Result<TeamData>
    suspend fun getAllTeams(): Result<List<TeamData>>
    suspend fun leaveTeam(): Result<Unit>
    suspend fun kickMember(kickId: Long): Result<Unit>
}