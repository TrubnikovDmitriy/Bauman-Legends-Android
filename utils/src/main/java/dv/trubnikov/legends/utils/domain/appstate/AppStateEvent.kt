package dv.trubnikov.legends.utils.domain.appstate

class AppStateEvent private constructor(private val states: Int) {

    companion object {
        val EMPTY_EVENT = AppStateEvent(AppState.NO_NEEDED.flag)
    }

    operator fun plus(state: AppState): AppStateEvent {
        return AppStateEvent(states or state.flag)
    }

    operator fun minus(state: AppState): AppStateEvent {
        return AppStateEvent(states and state.flag.inv())
    }

    infix fun has(state: AppState): Boolean {
        return (states and state.flag) != 0
    }

    override fun toString(): String {
        return "[${states.toString(radix = 2)}]"
    }
}