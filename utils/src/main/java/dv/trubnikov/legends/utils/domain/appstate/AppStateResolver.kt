package dv.trubnikov.legends.utils.domain.appstate

import androidx.navigation.NavDirections

interface AppStateResolver {

    fun navigationForState(state: AppState): NavDirections

    fun messageForState(state: AppState): String
}
