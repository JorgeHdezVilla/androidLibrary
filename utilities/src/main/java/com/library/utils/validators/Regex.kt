package com.library.utils.validators

@Suppress("UNUSED")
object Regex {
    const val EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    const val ALPHA_NUMERIC = "^[A-za-z0-9-\\+]+$"
    const val NUMERIC = "^[0-9]+$"
    const val DECIMAL = "^[0-9]+(\\.[0-9][0-9]?)?$"
    const val NOT_EMPTY = "^(?=\\s*\\S).*$"
    const val SPECIAL_ALPHA_NUMERIC = "^[A-za-z0-9-/_()]+$"
}
