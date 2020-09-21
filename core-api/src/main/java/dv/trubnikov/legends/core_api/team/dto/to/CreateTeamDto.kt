package dv.trubnikov.legends.core_api.team.dto.to

import com.google.gson.annotations.SerializedName

data class CreateTeamDto(
    @SerializedName("team_name") val teamName: String
)