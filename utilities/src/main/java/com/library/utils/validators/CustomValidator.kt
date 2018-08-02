package com.library.utils.validators

import com.library.utils.MessageUtils

class CustomValidator(validator: () -> Boolean, val errorMessage: Int) : FormValidator.Validator() {

    var function: Boolean = false

    init {
        function = validator()
    }

    override val isValid: Boolean
        get() {
            return function
        }

    override fun showError() {
        MessageUtils.toast(context, errorMessage)
    }

    override fun stopError() {

    }

}