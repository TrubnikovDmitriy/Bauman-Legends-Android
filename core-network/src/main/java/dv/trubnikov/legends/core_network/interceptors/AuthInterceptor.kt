package dv.trubnikov.legends.core_network.interceptors

import dv.trubnikov.legends.utils.exceptions.AuthRequiredException
import dv.trubnikov.legends.utils.network.HttpCode
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == HttpCode.UNAUTHORIZED) {
            throw AuthRequiredException(response.request.url.toString())
        }
        return response
    }
}