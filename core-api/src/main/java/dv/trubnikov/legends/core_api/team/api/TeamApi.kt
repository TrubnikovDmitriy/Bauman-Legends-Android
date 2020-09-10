package dv.trubnikov.legends.core_api.team.api

import dv.trubnikov.legends.core_api.team.dto.from.TeamDto
import retrofit2.Response
import retrofit2.http.GET

interface TeamApi {

    @GET("team/info")
    suspend fun teamInfo(): Response<TeamDto>

    @GET("team/all")
    suspend fun allTeams(): Response<List<TeamDto>>
}