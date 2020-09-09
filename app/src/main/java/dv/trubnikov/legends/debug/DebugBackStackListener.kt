package dv.trubnikov.legends.debug

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import dv.trubnikov.legends.BuildConfig

class DebugBackStackListener(
    private val navController: NavController
) : FragmentManager.FragmentLifecycleCallbacks() {

    companion object {
        private const val DELIMITER = " --> "
    }

    @SuppressLint("RestrictedApi") // For debug purpose
    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, state: Bundle?) {
        if (!BuildConfig.DEBUG) return

        val stringBuilder = StringBuilder()
        for (navBackStack in navController.backStack) {
            stringBuilder.append(navBackStack.destination.label)
            stringBuilder.append(DELIMITER)
        }
        if (stringBuilder.length >= DELIMITER.length) {
            stringBuilder.delete(stringBuilder.length - DELIMITER.length, stringBuilder.length)
        }

        Log.i("BackStack:", stringBuilder.toString())
    }
}