package dv.trubnikov.legends.feature_login.domain.interactors

import dv.trubnikov.legends.core_auth.UserData
import dv.trubnikov.legends.core_auth.UserStore
import dv.trubnikov.legends.feature_login.domain.LoginRepository
import dv.trubnikov.legends.utils.domain.Result
import javax.inject.Inject

class SignInInteractor @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userStore: UserStore
) {
    suspend fun signIn(login: String, password: String): Result<UserData> {
        val result = loginRepository.signIn(login, password)
        when(result) {
            is Result.Success -> userStore.value = result.data
            is Result.Error -> userStore.value = null
        }
        return result
    }
}