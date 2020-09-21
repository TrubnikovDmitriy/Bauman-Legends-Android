package dv.trubnikov.legends.utils.network

import dv.trubnikov.legends.utils.android.loge
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object ErrorMessageParser {
    fun parse(body: ResponseBody?): String? {
        try {
            val errorBody = body?.string() ?: return null
            return JSONObject(errorBody).getString("message")
        } catch (e: JSONException) {
            loge("Fail to parse error body", e)
            return null
        } catch(e: IOException) {
            loge("Fail to parse error body", e)
            return null
        }
    }
}