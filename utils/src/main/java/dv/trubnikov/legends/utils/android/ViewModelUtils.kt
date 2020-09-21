package dv.trubnikov.legends.utils.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ViewModel.ioScope(action: suspend () -> Unit): Job {
    return viewModelScope.launch {
        withContext(Dispatchers.IO) {
            action()
        }
    }
}