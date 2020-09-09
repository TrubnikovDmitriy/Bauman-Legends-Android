package dv.trubnikov.legends.utils.network

enum class HttpStatusCode(val code: Int, val message: String) {

    _200(200, "OK"),
    _201(201, "Created"),
    _202(202, "Accepted"),

    _400(400, "Bad Request"),
    _401(401, "Unauthorized"),
    _403(403, "Forbidden"),
    _404(404, "Not Found"),

    _500(500, "Internal Server Error"),
    _503(503, "Service Unavailable");

    fun is200(): Boolean = code in 200..299
    fun is400(): Boolean = code in 400..499
    fun is500(): Boolean = code >= 500
}