package dv.trubnikov.legends.core_models.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthData(
    val login: String,
    val password: String
) : Parcelable