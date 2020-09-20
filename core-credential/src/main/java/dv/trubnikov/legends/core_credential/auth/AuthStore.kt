package dv.trubnikov.legends.core_credential.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.utils.domain.Store
import javax.inject.Inject


class AuthStore @Inject constructor(
    @ApplicationContext private val context: Context
) : Store<AuthData>() {

    companion object {
        private const val AUTH_PREFERENCES  = "dv.trubnikov.legends.core_auth.auth.AuthStore"
        private const val LOGIN             = "$AUTH_PREFERENCES.login"
        private const val PASSWORD          = "$AUTH_PREFERENCES.study_group"
    }

    override fun readFromPersistence(): AuthData? {
        val prefs = getSharedPreferences()
        return AuthData(
            login = prefs.getString(LOGIN, null) ?: return null,
            password = prefs.getString(PASSWORD, null) ?: return null,
        )
    }

    override fun writeToPersistence(value: AuthData?) {
        getSharedPreferences().edit().apply {
            if (value != null) {
                putString(LOGIN, value.login)
                putString(PASSWORD, value.password)
            } else {
                remove(LOGIN)
                remove(PASSWORD)
            }
        }.apply()
    }

    private fun getSharedPreferences(): SharedPreferences {
        return EncryptedSharedPreferences.create(
            AUTH_PREFERENCES,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}