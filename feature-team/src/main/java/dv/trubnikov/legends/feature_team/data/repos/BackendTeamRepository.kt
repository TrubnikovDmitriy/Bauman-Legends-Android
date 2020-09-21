package dv.trubnikov.legends.feature_team.data.repos

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.core_api.team.api.TeamApi
import dv.trubnikov.legends.core_api.team.dto.to.CreateTeamDto
import dv.trubnikov.legends.core_api.team.dto.to.JoinToTeamDto
import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.feature_team.domain.TeamRepository
import dv.trubnikov.legends.utils.domain.Result
import dv.trubnikov.legends.utils.network.ErrorMessageParser
import dv.trubnikov.legends.utils.network.HttpCode
import dv.trubnikov.legends.utils.network.HttpCode.is4xx
import retrofit2.Response
import javax.inject.Inject
import dv.trubnikov.legends.utils.R as utilsR

class BackendTeamRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val teamApi: TeamApi
) : TeamRepository {

    override suspend fun createTeam(teamName: String): Result<TeamData> {
        val dto = CreateTeamDto(teamName)
        val response = teamApi.createTeam(dto)
        return handleResponse(response) { it.convert() }
    }

    override suspend fun renameTeam(teamName: String): Result<TeamData> {
        val dto = CreateTeamDto(teamName)
        val response = teamApi.renameTeam(dto)
        return handleResponse(response) { it.convert() }
    }

    override suspend fun joinToTeam(teamId: Long, inviteCode: String): Result<TeamData> {
        val dto = JoinToTeamDto(teamId, inviteCode)
        val response = teamApi.joinToTeam(dto)
        return handleResponse(response) { it.convert() }
    }

    override suspend fun getTeamInfo(): Result<TeamData?> {
        val response = teamApi.teamInfo()
        if (response.code() == HttpCode.NOT_FOUND) {
            return Result.Success(null)
        }
        return handleResponse(response) { it.convert() }
    }

    override suspend fun getTeamMembers(): Result<List<UserData>> {
        val response = teamApi.teamMembers()
        return handleResponse(response) { listDto -> listDto.mapNotNull { it.convert() } }
    }

    override suspend fun changeCaptain(captainId: Long): Result<TeamData> {
        val response = teamApi.changeCaptain(captainId)
        return handleResponse(response) { it.convert() }
    }

    override suspend fun getAllTeams(): Result<List<TeamData>> {
        val response = teamApi.allTeams()
        return handleResponse(response) { listDto -> listDto.mapNotNull { it.convert() } }
    }

    override suspend fun leaveTeam(): Result<Unit> {
        val response = teamApi.leaveTeam()
        return handleResponse(response) { }
    }

    override suspend fun kickMember(kickId: Long): Result<Unit> {
        val response = teamApi.kickMember(kickId)
        return handleResponse(response) { }
    }


    private val response5xx = context.getString(utilsR.string.response_5xx)
    private val responseNull = context.getString(utilsR.string.response_null)
    private val responseUnknown = context.getString(utilsR.string.response_unknown)

    private fun <R, T> handleResponse(
        response: Response<R>,
        bodyConverter: (R) -> T?
    ): Result<T> {
        if (response.isSuccessful) {
            val data = response.body()?.let { bodyConverter(it) }
            return if (data != null) {
                Result.Success(data)
            } else {
                Result.Error(responseNull)
            }
        } else {
            val httpCode = response.code()
            val message = when {
                httpCode.is4xx() -> ErrorMessageParser.parse(response.errorBody())
                else -> response5xx
            }
            return if (message != null) {
                Result.Error(message)
            } else {
                Result.Error(responseUnknown)
            }
        }
    }
}