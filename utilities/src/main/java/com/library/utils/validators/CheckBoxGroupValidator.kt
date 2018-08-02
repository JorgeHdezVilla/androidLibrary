package com.library.utils.validators

import android.widget.CheckBox

/**
 * Created by jorge.hernandez on 22/09/2016.
 * :v
 */
@Suppress("UNUSED")
class CheckBoxGroupValidator(private val checkBox: CheckBox, private val validators: Array<FormValidator.Validator>,
                             private val showAllErrors: Boolean) : FormValidator.Validator() {

    override val isValid: Boolean
        get() {
            var valid = true
            if (checkBox.isChecked) {
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
