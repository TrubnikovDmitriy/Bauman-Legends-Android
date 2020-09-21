package dv.trubnikov.legends.utils.android

import android.content.SharedPreferences

fun SharedPreferences.getBoolean(key: String): Boolean? {
    if (contains(key)) {
        return getBoolean(key, false)
    } else {
        return null
    }
}

fun SharedPreferences.getLong(key: String): Long? {
    if (contains(key)) {
        return getLong(key, -1)
    } else {
        return null
    }
}

fun SharedPreferences.Editor.putLong(key: String, value: Long?) {
    if (value != null) {
        putLong(key, value)
    } else {
        remove(key)
    }
}