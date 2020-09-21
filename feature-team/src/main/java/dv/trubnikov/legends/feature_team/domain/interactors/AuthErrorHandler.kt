package dv.trubnikov.legends.feature_team.domain.interactors

import dv.trubnikov.legends.utils.domain.Result
import dv.trubnikov.legends.utils.domain.appstate.AppState
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import dv.trubnikov.legends.utils.domain.appstate.AppStateResolver
import dv.trubnikov.legends.utils.exceptions.AuthRequiredException
import javax.inject.Inject

class AuthErrorHandler @Inject constructor(
    private val appStateCenter: AppStateCenter,
    private val appStateResolver: AppStateResolver
) {

    suspend fun <R> with(action: suspend () -> Result<R>): Result<R> {
        return with(firstAttempt = true, action)
    }

    private suspend fun <R> with(
        firstAttempt: Boolean,
        action: suspend () -> Result<R>
    ): Result<R> {
        try {
            return action()
        } catch (e: AuthRequiredException) {
            if (firstAttempt) {
                return with(firstAttempt = false, action)
            } else {
                appStateCenter.addState(AppState.NEED_AUTH)
                return Result.Error(appStateResolver.messageForState(AppState.NEED_AUTH))
            }
        }
    }
}