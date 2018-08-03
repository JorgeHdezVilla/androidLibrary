package com.library.utils.validators

import com.library.utils.MessageUtils

interface CustomValidatorCallback {
    fun onCheckValidation(): Boolean
}

class CustomValidator(validator: () -> Boolean, private val errorMessage: String) : FormValidator.Validator() {

    private val callback: CustomValidatorCallback

    init {
        callback = object : CustomValidatorCallback {
            override fun onCheckValidation(): Boolean {
                return validator()
            }
        }
    }

    override val isValid: Boolean
        get() {
            return callback.onCheckValidation()
        }

    override fun showError() {
        MessageUtils.toast(context, errorMessage)
    }

    override fun stopError() {

    }

}