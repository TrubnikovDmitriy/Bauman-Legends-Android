package dv.trubnikov.legends.feature_login.domain

import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.utils.domain.Result

interface LoginRepository {

    suspend fun signIn(
        login: String,
        password: String
    ): Result<UserData>

    suspend fun signUp(
        login: String,
        password: String,
        firstName: String,
        lastName: String,
        group: String,
        vkUri: String
    ): Result<UserData>
}