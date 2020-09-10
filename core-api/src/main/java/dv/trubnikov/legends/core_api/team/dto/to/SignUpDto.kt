package dv.trubnikov.legends.core_api.team.dto.to

import com.google.gson.annotations.SerializedName

data class SignUpDto(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("group") val studyGroup: String,
    @SerializedName("vk_ref") val vkUri: String
)