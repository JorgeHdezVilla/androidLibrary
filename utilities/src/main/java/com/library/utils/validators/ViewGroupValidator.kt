package com.library.utils.validators

import com.library.utils.MessageUtils


@Suppress("UNUSED")
class ViewGroupValidator : FormValidator.Validator {

    private val validator: FormValidator.Validator?
    private val validators: Array<FormValidator.Validator>
    private val showAllErrors: Boolean
    private val valueActivator: Boolean
    private var messageError: String

    constructor(validator: FormValidator.Validator, validators: Array<FormValidator.Validator>,
                showAllErrors: Boolean, valueActivator: Boolean) {
        this.validator = validator
        this.validators = validators
        this.showAllErrors = showAllErrors
        this.valueActivator = valueActivator
        messageError = ""
    }

    constructor(validators: Array<FormValidator.Validator>, showAllErrors: Boolean, messageError: String = "") {
        this.validator = null
        this.validators = validators
        this.showAllErrors = showAllErrors
        this.valueActivator = true
        this.messageError = messageError
    }

    override val isValid: Boolean
        get() {
            var valid = true
            val result = validator?.isValid ?: true
            if (result == valueActivator) {
                if (showAllErrors) {
                    for (validator in validators) {
                        validator.context = context
                        if (!validator.isValid) {
                            valid = false
                            validator.showError()
                        }
                    }
                } else {
                    for (validator in validators) {
                        validator.context = context
                        if (!validator.isValid) {
                            valid = false
                            validator.showError()
                            break
                        }
                    }
                }
            }
            return valid
        }

    override fun showError() {
        if (messageError != "") {
            MessageUtils.toast(context, messageError)
        }
    }

    override fun stopError() {
        for (validator in validators) {
            if (validator.isValid) {
                validator.stopError()
            }
        }
    }
}
