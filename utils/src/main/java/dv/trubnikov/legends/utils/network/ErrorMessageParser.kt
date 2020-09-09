package dv.trubnikov.legends.utils.network

import org.json.JSONObject

object ErrorMessageParser {
    fun parse(body: String): String {
        return JSONObject(body).optString("message", "Ой-ёй, непонятная ошибочка")
    }
}