package com.library.utils.validators

import android.widget.RadioButton


@Suppress("UNUSED")
class RadioButtonGroupValidator(private val radioButton: RadioButton, private val validators: Array<FormValidator.Validator>,
                                private val showAllErrors: Boolean) : FormValidator.Validator() {

    override val isValid: Boolean
        get() {
            var valid = true
            if (radioButton.isChecked) {
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
