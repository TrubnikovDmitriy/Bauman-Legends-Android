package dv.trubnikov.legends.utils.network

object HttpCode {
    const val OK            = 200
    const val CREATED       = 201
    const val ACCEPTED      = 202

    const val BAD_REQUEST   = 400
    const val UNAUTHORIZED  = 401
    const val FORBIDDEN     = 403
    const val NOT_FOUND     = 404

    const val INTERNAL_SERVER_ERROR = 500
    const val SERVICE_UNAVAILABLE   = 503

    fun Int.is2xx(): Boolean = this in 200..299
    fun Int.is4xx(): Boolean = this in 400..499
    fun Int.is5xx(): Boolean = this >= 500
}
