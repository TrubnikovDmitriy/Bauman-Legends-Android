package dv.trubnikov.legends.utils.domain

interface AutoReloginer {
    /**
     * Try to automatically login into backend.
     * @return true in case of success auto-relogin, false otherwise
     */
    suspend fun relogin(): Boolean
}