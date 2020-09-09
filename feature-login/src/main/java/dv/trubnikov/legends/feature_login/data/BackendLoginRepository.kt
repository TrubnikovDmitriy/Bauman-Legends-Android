package dv.trubnikov.legends.feature_login.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.core_auth.UserData
import dv.trubnikov.legends.feature_login.R
import dv.trubnikov.legends.feature_login.data.api.LoginApi
import dv.trubnikov.legends.feature_login.data.dto.SignInDto
import dv.trubnikov.legends.feature_login.data.dto.SignUpDto
import dv.trubnikov.legends.feature_login.data.dto.UserDto
import dv.trubnikov.legends.feature_login.domain.LoginRepository
import dv.trubnikov.legends.utils.network.ErrorMessageParser
import dv.trubnikov.legends.utils.network.HttpStatusCode
import dv.trubnikov.legends.utils.domain.Result
import retrofit2.Response
import javax.inject.Inject

class BackendLoginRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun signIn(login: String, password: String): Result<UserData> {
        val dto = SignInDto(login = login, password = password)
        val response = loginApi.signIn(dto)
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
        val dto = SignUpDto(
            login = login,
            password = password,
            firstName = firstName,
            lastName = lastName,
            studyGroup = group,
            vkUri = vkUri
        )
        val response = loginApi.signUp(dto)
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