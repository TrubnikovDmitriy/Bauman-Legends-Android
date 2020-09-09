package dv.trubnikov.legends.core_network.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import javax.inject.Inject

internal class CookieController @Inject constructor(
    private val cookieStore: CookieStore
) : CookieJar {

    companion object {
        private const val COOKIE_NAME = "JSESSIONID"
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookie = cookieStore.value
        if (cookie == null) {
            return emptyList()
        } else {
            return listOf(cookie)
        }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val newSessionCookie = cookies.find { cookie -> cookie.name == COOKIE_NAME }
        cookieStore.value = newSessionCookie
    }
}
