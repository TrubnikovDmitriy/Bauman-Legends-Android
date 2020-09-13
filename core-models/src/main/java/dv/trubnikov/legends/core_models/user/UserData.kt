package dv.trubnikov.legends.core_models.user

data class UserData(
    val userId: Long,
    val login: String,
    val role: UserRole,
    val firstName: String,
    val lastName: String,
    val studyGroup: String
)