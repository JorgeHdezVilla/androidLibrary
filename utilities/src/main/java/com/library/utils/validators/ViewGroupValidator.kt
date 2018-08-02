package com.library.utils.validators


@Suppress("UNUSED")
class ViewGroupValidator : FormValidator.Validator {

    private val validator: FormValidator.Validator?
    private val validators: Array<FormValidator.Validator>
    private val showAllErrors: Boolean
    private val valueActivator: Boolean

    constructor(validator: FormValidator.Validator, validators: Array<FormValidator.Validator>,
                showAllErrors: Boolean, valueActivator: Boolean) {
        this.validator = validator
        this.validators = validators
        this.showAllErrors = showAllErrors
        this.valueActivator = valueActivator
    }

    constructor(validators: Array<FormValidator.Validator>, showAllErrors: Boolean) {
        this.validator = null
        this.validators = validators
        this.showAllErrors = showAllErrors
        this.valueActivator = true
    }

    override val isValid: Boolean
        get() {
            var valid = true
            val result = validator?.isValid ?: true
            if (result == valueActivator) {
                for (validator in validators) {
                    validator.context = context
                    if (!validator.isValid) {
                        valid = false
                    }
                }
            }
            return valid
        }

    override fun showError() {
        if (showAllErrors) {
            for (validator in validators) {
                if (!validator.isValid) {
                    validator.showError()
                }
            }
        } else {
            for (validator in validators) {
                if (!validator.isValid) {
                    validator.showError()
                    break
                }
            }
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
