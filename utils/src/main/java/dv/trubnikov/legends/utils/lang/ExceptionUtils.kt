package dv.trubnikov.legends.utils.lang

tailrec fun Throwable.getRootCause(): Throwable {
    val cause = cause ?: return this
    if (cause == this) return this
    return cause.getRootCause()
}