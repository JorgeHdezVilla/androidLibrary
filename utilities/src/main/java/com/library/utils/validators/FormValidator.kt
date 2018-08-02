package com.library.utils.validators

import android.content.Context
import com.library.utils.MessageUtils
import java.util.*


@Suppress("UNUSED")
class FormValidator {

    private var mValidators: ArrayList<Validator>
    private var mContext: Context
    private var showAllErrors: Boolean = false

    private var showDefaultMessage = true

    val isEdit: Boolean
        get() {
            var edit = false
            for (v in mValidators) {
                edit = edit or v.isValid
            }
            return edit
        }

    val isValid: Boolean
        get() = valid()

    constructor(context: Context) {
        this.mContext = context
        mValidators = ArrayList()
        showAllErrors = false
    }

    constructor(context: Context, showAllErrors: Boolean) {
        this.mContext = context
        this.showAllErrors = showAllErrors
        mValidators = ArrayList()
    }

    fun <T : FormValidator.Validator> addValidator(validator: T) {
        validator.context = mContext
        mValidators.add(validator)
    }

    fun <T : FormValidator.Validator> addValidators(vararg validators: Validator) {
        for (validator in validators) {
            validator.context = mContext
            mValidators.add(validator)
        }
    }

    fun isValid(showDefaultMessage: Boolean): Boolean {
        this.showDefaultMessage = showDefaultMessage
        return valid()
    }

    private fun valid(): Boolean {
        var valid = true
        if (showAllErrors) {
            for (v in mValidators) {
                if (!v.isValid) {
                    v.showError()
                } else {
                    v.stopError()
                }
                valid = valid and v.isValid
            }
        } else {
            for (v in mValidators) {
                if (!v.isValid) {
                    v.showError()
                    valid = false
                    break
                }
            }
        }
        if (showDefaultMessage && !valid) {
            MessageUtils.toast(mContext, "Revise los campos obligatorios")
        }
        return valid
    }

    abstract class Validator {

        lateinit var context: Context

        abstract val isValid: Boolean

        abstract fun showError()

        abstract fun stopError()
    }

}
