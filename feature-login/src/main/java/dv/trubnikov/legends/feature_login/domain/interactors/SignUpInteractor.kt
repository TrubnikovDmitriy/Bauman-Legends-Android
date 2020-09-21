package dv.trubnikov.legends.feature_login.domain.interactors

import dv.trubnikov.legends.core_credential.auth.AuthStore
import dv.trubnikov.legends.core_credential.user.UserStore
import dv.trubnikov.legends.core_models.auth.AuthData
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.feature_login.domain.LoginRepository
import dv.trubnikov.legends.utils.domain.Result
import dv.trubnikov.legends.utils.domain.appstate.AppState.NEED_AUTH
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import javax.inject.Inject

class SignUpInteractor @Inject constructor(
    private val loginRepository: LoginRepository,
    private val authStore: AuthStore,
    private val userStore: UserStore,
    private val stateCenter: AppStateCenter
) {
    suspend fun signUp(
        login: String,
        password: String,
        firstName: String,
        lastName: String,
        group: String,
        vkUri: String
    ): Result<UserData> {
        val result = loginRepository.signUp(login, password, firstName, lastName, group, vkUri)
        when(result) {
            is Result.Success -> {
                stateCenter.removeState(NEED_AUTH)
                authStore.value = AuthData(login, password)
                userStore.value = result.data
            }
            is Result.Error -> userStore.value = null
        }
        return result
    }
}