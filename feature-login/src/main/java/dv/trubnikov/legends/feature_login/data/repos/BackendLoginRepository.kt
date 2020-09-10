package dv.trubnikov.legends.feature_login.data.repos

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.core_api.user.dto.from.UserDto
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.feature_login.R
import dv.trubnikov.legends.feature_login.domain.LoginRepository
import dv.trubnikov.legends.utils.domain.Result
import dv.trubnikov.legends.utils.network.ErrorMessageParser
import dv.trubnikov.legends.utils.network.HttpStatusCode
import retrofit2.Response
import javax.inject.Inject

class BackendLoginRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userApi: dv.trubnikov.legends.core_api.user.api.UserApi
) : LoginRepository {

    override suspend fun signIn(login: String, password: String): Result<UserData> {
        val dto =
            dv.trubnikov.legends.core_api.user.dto.to.SignInDto(login = login, password = password)
        val response = userApi.signIn(dto)
        return handleResponse(response) {
            val message = when(it.code()) {
                HttpStatusCode._400.code -> context.getString(R.string.fail_to_sign_in)
                else -> context.getString(R.string.response_5xx)
            }
            Result.Error(message)
        }
    }

    override suspend fun signUp(
        login: String,
        password: String,
        firstName: String,
        lastName: String,
        group: String,
        vkUri: String
    ): Result<UserData> {
        val dto = dv.trubnikov.legends.core_api.user.dto.to.SignUpDto(
            login = login,
            password = password,
            firstName = firstName,
            lastName = lastName,
            studyGroup = group,
            vkUri = vkUri
        )
        val response = userApi.signUp(dto)
        return handleResponse(response) {
            val message = when(it.code()) {
                HttpStatusCode._400.code -> ErrorMessageParser.parse(it.errorBody()!!.string())
                else -> context.getString(R.string.response_5xx)
            }
            Result.Error(message)
        }
    }


    private fun handleResponse(
        response: Response<UserDto>,
        errorHandler: (Response<*>) -> Result.Error
    ): Result<UserData> {
        if (response.isSuccessful) {
            response.body()?.convert()?.let {
                return Result.Success(it)
            }
            return Result.Error("Fail to deserialize")
        } else{
            return errorHandler(response)
        }
    }
}