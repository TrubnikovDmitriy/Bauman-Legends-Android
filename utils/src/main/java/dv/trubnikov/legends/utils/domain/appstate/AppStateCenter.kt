package dv.trubnikov.legends.utils.domain.appstate

import androidx.lifecycle.LiveData

interface AppStateCenter {

    fun addState(state: AppState)

    fun removeState(state: AppState)

    fun getEvents(): LiveData<AppStateEvent>
}
