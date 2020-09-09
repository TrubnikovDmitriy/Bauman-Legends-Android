package dv.trubnikov.legends.utils.lang

import java.util.*

inline fun <reified E : Enum<E>> safetyValueOf(value: String?): E? {
    return try {
        val enumValue = value?.toUpperCase(Locale.ENGLISH) ?: return null
        enumValueOf<E>(enumValue)
    } catch (e: IllegalArgumentException) {
        null
    }
}