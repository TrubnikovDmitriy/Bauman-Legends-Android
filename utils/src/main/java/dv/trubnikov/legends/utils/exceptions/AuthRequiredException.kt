package dv.trubnikov.legends.utils.exceptions

import java.io.IOException

class AuthRequiredException(url: String) : IOException(url)