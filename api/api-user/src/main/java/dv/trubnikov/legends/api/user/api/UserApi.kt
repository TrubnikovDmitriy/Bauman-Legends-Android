package dv.trubnikov.legends.api.user.api

import dv.trubnikov.legends.api.user.dto.from.UserDto
import dv.trubnikov.legends.api.user.dto.to.SignInDto
import dv.trubnikov.legends.api.user.dto.to.SignUpDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("user/sign_in")
    suspend fun signIn(@Body dto: SignInDto): Response<UserDto>

    @POST("user/sign_up")
    suspend fun signUp(@Body dto: SignUpDto): Response<UserDto>

}