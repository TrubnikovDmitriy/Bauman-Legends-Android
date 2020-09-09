package dv.trubnikov.legends.utils.android

import android.util.Log

private const val TAG = "Legends"

fun loge(message: String): Int = Log.e(TAG, constructMessage(message))
fun logw(message: String): Int = Log.w(TAG, constructMessage(message))
fun logi(message: String): Int = Log.i(TAG, constructMessage(message))
fun logv(message: String): Int = Log.v(TAG, constructMessage(message))
fun logd(message: String): Int = Log.d(TAG, constructMessage(message))
fun wtf(message: String): Int = Log.wtf(TAG, constructMessage(message))

private fun constructMessage(message: String): String {
    return "[Thread=${Thread.currentThread().name}] $message"
}