package dv.trubnikov.legends.core_models.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    val userId: Long,
    val login: String,
    val role: UserRole,
    val firstName: String,
    val lastName: String,
    val studyGroup: String,
    val teamId: Long?
) : Parcelable