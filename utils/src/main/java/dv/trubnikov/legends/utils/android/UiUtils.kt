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