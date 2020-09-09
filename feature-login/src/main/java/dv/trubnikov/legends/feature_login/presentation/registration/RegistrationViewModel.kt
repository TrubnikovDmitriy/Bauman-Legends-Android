package dv.trubnikov.legends.feature_login.presentation.registration

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.feature_login.R
import dv.trubnikov.legends.feature_login.domain.interactors.SignUpInteractor
import dv.trubnikov.legends.utils.domain.Result
import kotlinx.coroutines.*

class RegistrationViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val interactor: SignUpInteractor
) : ViewModel() {

    private val finishLogin = MutableLiveData<Boolean>(false)
    private val error = MutableLiveData<String?>(null)

    fun getErrors(): LiveData<String?> = error
    fun isFinished(): LiveData<Boolean> = finishLogin

    fun signUp(
        login: String,
        password: String,
        firstName: String,
        lastName: String,
        group: String,
        vkUri: String
    ) {
        error.value = null

        for (field in listOf(login, password, firstName, lastName, group, vkUri)) {
            if (field.isBlank()) {
                error.postValue(context.getString(R.string.error_empty_field))
                return
            }
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = interactor.signUp(
                    login.trim(),
                    password.trim(),
                    firstName.trim(),
                    lastName.trim(),
                    group.trim(),
                    vkUri.trim()
                )
                when (result) {
                    is Result.Success -> finishLogin.postValue(true)
                    is Result.Error -> error.postValue(result.message)
                }
            }
        }
    }
}
