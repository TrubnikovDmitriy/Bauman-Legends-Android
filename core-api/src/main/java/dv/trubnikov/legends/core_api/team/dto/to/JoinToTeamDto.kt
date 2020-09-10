package dv.trubnikov.legends.core_api.team.dto.to

import com.google.gson.annotations.SerializedName

data class JoinToTeamDto(
    @SerializedName("team_id") val teamId: Long,
    @SerializedName("invite_code") val inviteCode: String
)