package dv.trubnikov.legends.utils.domain.appstate

enum class AppState(val flag: Int) {
    NO_NEEDED       (0),
    NEED_NETWORK    (1 shl 0),
    NEED_AUTH       (1 shl 1),
    NEED_TEAM       (1 shl 2);

    override fun toString(): String {
        return "${super.toString()}[${flag.toString(radix = 2)}]"
    }
}