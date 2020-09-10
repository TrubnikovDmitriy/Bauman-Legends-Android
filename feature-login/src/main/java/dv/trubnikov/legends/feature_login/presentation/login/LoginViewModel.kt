package dv.trubnikov.legends.feature_login.presentation.login

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.feature_login.R
import dv.trubnikov.legends.feature_login.domain.interactors.SignInInteractor
import dv.trubnikov.legends.utils.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val interactor: SignInInteractor
) : ViewModel() {

    private val finishLogin = MutableLiveData<Boolean>(false)
    private val error = MutableLiveData<String?>(null)

    fun getErrors(): LiveData<String?> = error
    fun isFinished(): LiveData<Boolean> = finishLogin

    fun login(username: String, password: String) {
        error.value = null

        if (username.isBlank()) {
            error.value = context.getString(R.string.error_empty_login)
            return
        }
        if (password.isBlank()) {
            error.value = context.getString(R.string.error_empty_password)
            return
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result: Result<UserData> = interactor.signIn(username, password)
                when (result) {
                    is Result.Success -> finishLogin.postValue(true)
                    is Result.Error -> error.postValue(result.message)
                }
            }
        }
    }
}
