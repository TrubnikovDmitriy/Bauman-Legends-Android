package dv.trubnikov.legends.appstate

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dv.trubnikov.legends.utils.android.logi
import dv.trubnikov.legends.utils.domain.appstate.AppState
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import dv.trubnikov.legends.utils.domain.appstate.AppStateEvent
import javax.inject.Inject
import javax.inject.Singleton

class AppStateCenterImpl @Inject constructor() : AppStateCenter {

    private val events = MutableLiveData<AppStateEvent>()

    private val handler = Handler(Looper.getMainLooper())
    private var event: AppStateEvent = AppStateEvent.EMPTY_EVENT


    override fun addState(state: AppState) {
        logi("AppStateCenter: Add app state $state to $event")
        handler.post {
            event += state
            events.value = event
        }
    }

    override fun removeState(state: AppState) {
        logi("AppStateCenter: Remove app state $state from $event")
        handler.post {
            event -= state
            events.value = event
        }
    }

    override fun getEvents(): LiveData<AppStateEvent> = events
}