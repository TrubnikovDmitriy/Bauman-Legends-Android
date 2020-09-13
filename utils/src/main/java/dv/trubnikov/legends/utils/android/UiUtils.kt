package dv.trubnikov.legends.utils.android

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService


fun View.hideKeyboard() {
    val imm = getSystemService(context, InputMethodManager::class.java)
    try {
        imm?.hideSoftInputFromWindow(applicationWindowToken, 0)
    } catch (e: Exception) {
        // TODO norm handling
    }
}

fun View.show(isVisible: Boolean) {
    if (isVisible) show() else hide()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
