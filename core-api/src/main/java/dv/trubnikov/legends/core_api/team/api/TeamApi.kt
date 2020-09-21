package dv.trubnikov.legends.core_api.team.api

import dv.trubnikov.legends.core_api.team.dto.from.TeamDto
import dv.trubnikov.legends.core_api.team.dto.to.CreateTeamDto
import dv.trubnikov.legends.core_api.team.dto.to.JoinToTeamDto
import dv.trubnikov.legends.core_api.user.dto.from.UserDto
import retrofit2.Response
import retrofit2.http.*

interface TeamApi {

    @GET("team/info")
    suspend fun teamInfo(): Response<TeamDto>

    @POST("team/update")
    suspend fun renameTeam(@Body dto: CreateTeamDto): Response<TeamDto>

    @GET("team/all")
    suspend fun allTeams(): Response<List<TeamDto>>

    @GET("team/members")
    suspend fun teamMembers(): Response<List<UserDto>>

    @GET("team/change")
    suspend fun changeCaptain(@Query("new_captain") captainId: Long): Response<TeamDto>

    @POST("team/join")
    suspend fun joinToTeam(@Body dto: JoinToTeamDto): Response<TeamDto>

    @POST("team/create")
    suspend fun createTeam(@Body dto: CreateTeamDto): Response<TeamDto>

    @DELETE("team/kick")
    suspend fun kickMember(@Query("new_captain") kickId: Long): Response<Unit>

    @DELETE("team/leave")
    suspend fun leaveTeam(): Response<Unit>
}