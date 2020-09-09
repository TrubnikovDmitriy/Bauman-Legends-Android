package dv.trubnikov.legends.feature_login.data.dto

import com.google.gson.annotations.SerializedName

data class SignInDto(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String
)