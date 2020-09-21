package dv.trubnikov.legends.core_credential.user

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.core_models.user.UserRole
import dv.trubnikov.legends.utils.android.getLong
import dv.trubnikov.legends.utils.android.putLong
import dv.trubnikov.legends.utils.android.logi
import dv.trubnikov.legends.utils.domain.Store
import dv.trubnikov.legends.utils.lang.safetyValueOf
import javax.inject.Inject


class UserStore @Inject constructor(
    @ApplicationContext private val context: Context
) : Store<UserData>() {

    companion object {
        private const val USER_PREFERENCES  = "dv.trubnikov.legends.core_auth.user.UserStore"
        private const val ID                = "$USER_PREFERENCES.id"
        private const val LOGIN             = "$USER_PREFERENCES.login"
        private const val ROLE              = "$USER_PREFERENCES.role"
        private const val FIRST_NAME        = "$USER_PREFERENCES.first_name"
        private const val LAST_NAME         = "$USER_PREFERENCES.last_name"
        private const val STUDY_GROUP       = "$USER_PREFERENCES.study_group"
        private const val TEAM_ID           = "$USER_PREFERENCES.team_id"
    }

    override fun onValueChanged(oldValue: UserData?, newValue: UserData?) {
        logi("Update user: from [${oldValue}] to [${newValue}]")
    }

    override fun readFromPersistence(): UserData? {
        val prefs = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
        return UserData(
            userId = prefs.getLong(ID) ?: return null,
            login = prefs.getString(LOGIN, null) ?: return null,
            role = safetyValueOf<UserRole>(prefs.getString(ROLE, null)) ?: return null,
            firstName = prefs.getString(FIRST_NAME, null) ?: return null,
            lastName = prefs.getString(LAST_NAME, null) ?: return null,
            studyGroup = prefs.getString(STUDY_GROUP, null) ?: return null,
            teamId = prefs.getLong(TEAM_ID)
        )
    }

    override fun writeToPersistence(value: UserData?) {
        context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE).edit().apply {
            if (value != null) {
                putLong(ID, value.userId)
                putString(LOGIN, value.login)
                putString(ROLE, value.role.name)
                putString(FIRST_NAME, value.firstName)
                putString(LAST_NAME, value.lastName)
                putString(STUDY_GROUP, value.studyGroup)
                putLong(TEAM_ID, value.teamId)
            } else {
                remove(ID)
                remove(LOGIN)
                remove(ROLE)
                remove(FIRST_NAME)
                remove(LAST_NAME)
                remove(STUDY_GROUP)
                remove(TEAM_ID)
            }
        }.apply()
    }
}