package dv.trubnikov.legends.feature_login.domain

import dv.trubnikov.legends.core_credential.auth.AuthStore
import dv.trubnikov.legends.utils.android.logi
import dv.trubnikov.legends.utils.domain.AutoReloginer
import dv.trubnikov.legends.utils.domain.Result
import javax.inject.Inject

class AutoReloginerImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    private val authStore: AuthStore
) : AutoReloginer {

    override suspend fun relogin(): Boolean {
        val authData = authStore.value ?: return false
        logi("AutoRelogin: tried to relogin with result")
        val result = loginRepository.signIn(authData.login, authData.password)
        return when(result) {
            is Result.Success -> true
            is Result.Error -> {
                authStore.value = null
                false
            }
        }
    }
}