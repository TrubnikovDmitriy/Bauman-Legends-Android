package dv.trubnikov.legends.utils.domain

sealed class Result<out R> {

    data class Success<R>(
        val data: R
    ) : Result<R>()

    data class Error(
        val message: String,
        val exception: Exception? = null
    ) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success [data=$data]"
            is Error -> "Error [message=$message, exception=${exception?.localizedMessage}]"
        }
    }
}
