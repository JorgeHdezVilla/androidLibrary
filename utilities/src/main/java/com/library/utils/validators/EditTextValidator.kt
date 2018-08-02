package com.library.utils.validators

import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import java.util.regex.Pattern


@Suppress("UNUSED")
class EditTextValidator : FormValidator.Validator {

    private val editText: EditText
    private val errorMessage: Int
    private val pattern: Pattern?
    private val minCharacters: Int
    private val maxCharacters: Int

    override val isValid: Boolean
        get() {
            var valid = true
            val viewGroup = editText.parent as? ViewGroup
            val secondViewGroup = editText.parent.parent as? ViewGroup
            if (editText.visibility == View.VISIBLE
                    && (viewGroup == null || viewGroup.visibility == View.VISIBLE)
                    && (secondViewGroup == null || secondViewGroup.visibility == View.VISIBLE)) {
                if (minCharacters != -1) {
                    valid = editText.text.toString().trim { it <= ' ' }.length >= minCharacters
                }
                if (maxCharacters != -1) {
                    valid = valid and (maxCharacters >= editText.text.toString().trim { it <= ' ' }.length)
                }
                if (pattern != null) {
                    val matcher = pattern.matcher(editText.text.toString().trim { it <= ' ' })
                    return valid and matcher.matches()
                } else {
                    valid = !editText.text.toString().trim { it <= ' ' }.isEmpty()
                }
            }
            return valid
        }

    constructor(editText: EditText, errorMessage: Int) {
        this.editText = editText
        this.errorMessage = errorMessage
        this.minCharacters = -1
        this.maxCharacters = -1
        this.pattern = null
    }

    constructor(editText: EditText, regex: String, errorMessage: Int) {
        this.editText = editText
        this.errorMessage = errorMessage
        this.minCharacters = -1
        this.maxCharacters = -1
        this.pattern = Pattern.compile(regex)
    }

    constructor(editText: EditText, minCharacters: Int, maxCharacters: Int, errorMessage: Int) {
        this.editText = editText
        this.errorMessage = errorMessage
        this.minCharacters = minCharacters
        this.maxCharacters = maxCharacters
        if (minCharacters < 0) {
            throw IllegalArgumentException("[EditTextValidator] Minimum number of characters is 0")
        }
        this.pattern = null
    }

    override fun showError() {
        error(errorMessage)
    }

    private fun error(message: Int?) {
        try {
            val textInputLayout = editText.parent as TextInputLayout
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = if (message != null) context.getString(message) else null
        } catch (ignored: ClassCastException) {
            editText.error = if (message != null) context.getString(message) else null
        }

    }

    override fun stopError() {
        error(null)
    }
}
