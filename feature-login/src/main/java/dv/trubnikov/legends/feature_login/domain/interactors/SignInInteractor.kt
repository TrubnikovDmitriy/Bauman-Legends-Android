package dv.trubnikov.legends.feature_login.domain.interactors

import dv.trubnikov.legends.core_credential.auth.AuthData
import dv.trubnikov.legends.core_credential.auth.AuthStore
import dv.trubnikov.legends.core_credential.user.UserStore
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.feature_login.domain.LoginRepository
import dv.trubnikov.legends.utils.domain.Result
import javax.inject.Inject

class SignInInteractor @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userStore: UserStore,
    private val authStore: AuthStore
) {
    suspend fun signIn(login: String, password: String): Result<UserData> {
        val result = loginRepository.signIn(login, password)
        when(result) {
            is Result.Success -> {
                authStore.value = AuthData(login, password)
                userStore.value = result.data
            }
            is Result.Error -> userStore.value = null
        }
        return result
    }
}