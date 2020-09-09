package dv.trubnikov.legends.core_network.cookie

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.utils.android.getBoolean
import dv.trubnikov.legends.utils.android.getLong
import dv.trubnikov.legends.utils.android.logi
import dv.trubnikov.legends.utils.domain.Store
import okhttp3.Cookie
import javax.inject.Inject

internal class CookieStore @Inject constructor(
    @ApplicationContext private val context: Context
) : Store<Cookie>() {

    companion object {
        private const val SESSION_PREFERENCES   = "dv.trubnikov.legends.core_auth.SessionStore"
        private const val NAME                  = "$SESSION_PREFERENCES.name"
        private const val VALUE                 = "$SESSION_PREFERENCES.value"
        private const val EXPIRES               = "$SESSION_PREFERENCES.expires"
        private const val DOMAIN                = "$SESSION_PREFERENCES.domen"
        private const val PATH                  = "$SESSION_PREFERENCES.path"
        private const val SECURE                = "$SESSION_PREFERENCES.secure"
        private const val HTTP_ONLY             = "$SESSION_PREFERENCES.http_only"
        private const val HOST_ONLY             = "$SESSION_PREFERENCES.host_only"
    }


    override fun onValueChanged(oldValue: Cookie?, newValue: Cookie?) {
        logi("Update cookie: from [${oldValue}] to [${newValue}]")
    }

    override fun readFromPersistence(): Cookie? {
        val prefs = context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE)
        return Cookie.Builder().apply {
            prefs.getString(NAME, null)?.let { name(it) } ?: return null
            prefs.getString(VALUE, null)?.let { value(it) } ?: return null
            prefs.getString(PATH, null)?.let { path(it) }
            prefs.getLong(EXPIRES)?.let { expiresAt(it) }
            prefs.getBoolean(SECURE)?.let { if (it) secure() }
            prefs.getBoolean(HTTP_ONLY)?.let { if (it) httpOnly() }
            val domain = prefs.getString(DOMAIN, null)
            val hostOnly = prefs.getBoolean(HOST_ONLY, false)
            if (domain != null) {
                if (hostOnly) {
                    hostOnlyDomain(domain)
                } else {
                    domain(domain)
                }
            }
        }.build()
    }

    override fun writeToPersistence(value: Cookie?) {
        context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE).edit().apply {
            if (value != null) {
                putString(NAME, value.name)
                putString(VALUE, value.value)
                putLong(EXPIRES, value.expiresAt)
                putString(DOMAIN, value.domain)
                putString(PATH, value.path)
                putBoolean(SECURE, value.secure)
                putBoolean(HTTP_ONLY, value.httpOnly)
                putBoolean(HOST_ONLY, value.hostOnly)
            } else {
                remove(NAME)
                remove(VALUE)
                remove(EXPIRES)
                remove(DOMAIN)
                remove(PATH)
                remove(SECURE)
                remove(HTTP_ONLY)
                remove(HOST_ONLY)
            }
        }.apply()
    }
}