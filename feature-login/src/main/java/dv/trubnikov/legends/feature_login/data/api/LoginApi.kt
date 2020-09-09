package dv.trubnikov.legends.feature_login.data.api

import dv.trubnikov.legends.feature_login.data.dto.SignInDto
import dv.trubnikov.legends.feature_login.data.dto.SignUpDto
import dv.trubnikov.legends.feature_login.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {

    @POST("user/sign_in")
    suspend fun signIn(@Body dto: SignInDto): Response<UserDto>

    @POST("user/sign_up")
    suspend fun signUp(@Body dto: SignUpDto): Response<UserDto>
}
