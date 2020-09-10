package dv.trubnikov.legends.core_api.team.dto.to

import com.google.gson.annotations.SerializedName

data class SignInDto(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String
)