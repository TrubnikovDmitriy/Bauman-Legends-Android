package dv.trubnikov.legends.appstate

import android.content.Context
import androidx.navigation.NavDirections
import dagger.hilt.android.qualifiers.ApplicationContext
import dv.trubnikov.legends.AppNavigationDirections
import dv.trubnikov.legends.R
import dv.trubnikov.legends.utils.domain.appstate.AppState
import dv.trubnikov.legends.utils.domain.appstate.AppStateResolver
import javax.inject.Inject

class AppStateResolverImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppStateResolver {

    override fun navigationForState(state: AppState): NavDirections {
        return when(state) {
            AppState.NEED_AUTH -> AppNavigationDirections.actionGlobalLoginNavGraph()
            AppState.NEED_TEAM -> AppNavigationDirections.actionGlobalTeamNavGraph()
            AppState.NEED_NETWORK -> TODO()
            AppState.NO_NEEDED -> throw NotImplementedError("$state has no direction")
        }
    }

    override fun messageForState(state: AppState): String {
        return when(state) {
            AppState.NEED_AUTH -> context.getString(R.string.auth_need_error)
            AppState.NEED_TEAM -> context.getString(R.string.team_need_error)
            AppState.NEED_NETWORK -> context.getString(R.string.network_need_error)
            AppState.NO_NEEDED -> ""
        }
    }
}