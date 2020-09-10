package dv.trubnikov.legends.core_api.team.dto.from

import com.google.gson.annotations.SerializedName
import dv.trubnikov.legends.core_models.team.TeamData

data class TeamDto(
    @SerializedName("team_id") val teamId: Long?,
    @SerializedName("team_name") val teamName: String?,
    @SerializedName("leader_id") val leaderId: Long?,
    @SerializedName("score") val score: Int?,
    @SerializedName("money") val money: Int?,
    @SerializedName("size") val size: Int?,
    @SerializedName("invite_code") val inviteCode: String?
) {
    fun convert(): TeamData? {
        return TeamData(
            teamId = teamId ?: return null,
            teamName = teamName ?: return null,
            leaderId = leaderId ?: return null,
            score = score ?: return null,
            money = money ?: return null,
            size = size ?: return null,
            inviteCode = inviteCode
        )
    }
}
