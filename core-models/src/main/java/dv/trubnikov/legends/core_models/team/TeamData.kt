package dv.trubnikov.legends.core_models.team

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamData(
    val teamId: Long,
    val teamName: String,
    val leaderId: Long,
    val money: Int,
    val score: Int,
    val size: Int,
    val inviteCode: String?
) : Parcelable