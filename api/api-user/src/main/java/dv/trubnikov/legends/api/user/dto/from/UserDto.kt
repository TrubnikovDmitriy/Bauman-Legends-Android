package dv.trubnikov.legends.api.user.dto.from

import com.google.gson.annotations.SerializedName
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.core_models.user.UserRole
import dv.trubnikov.legends.utils.lang.safetyValueOf

data class UserDto(
    @SerializedName("login") val login: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("user_id") val userId: Long?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("first_name") val firstName: String?
) {
    fun convert(): UserData? {
        return UserData(
            login = login ?: return null,
            userId = userId ?: return null,
            lastName = lastName ?: return null,
            firstName = firstName ?: return null,
            role = safetyValueOf<UserRole>(role) ?: return null
        )
    }
}
