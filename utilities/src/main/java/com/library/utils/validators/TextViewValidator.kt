package com.library.utils.validators

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import java.util.regex.Pattern

/**
 * Created by jorgehdezvilla on 10/11/17.
 */

@Suppress("UNUSED")
class TextViewValidator : FormValidator.Validator, TextWatcher {

    private val textView: TextView
    private val errorMessage: Int
    private val pattern: Pattern?
    private val minCharacters: Int
    private val maxCharacters: Int

    override val isValid: Boolean
        get() {
            var valid = true
            if (minCharacters != -1) {
                valid = textView.text.toString().trim { it <= ' ' }.length >= minCharacters
            }
            if (maxCharacters != -1) {
                valid = valid and (maxCharacters >= textView.text.toString().trim { it <= ' ' }.length)
            }
            if (pattern != null) {
                val matcher = pattern.matcher(textView.text.toString().trim { it <= ' ' })
                return valid and matcher.matches()
            }
            return valid
        }


    constructor(textView: TextView, regex: String, errorMessage: Int) {
        this.textView = textView
        this.textView.addTextChangedListener(this)
        this.errorMessage = errorMessage
        this.minCharacters = -1
        this.maxCharacters = -1
        this.pattern = Pattern.compile(regex)
    }

    constructor(textView: TextView, minCharacters: Int, maxCharacters: Int, errorMessage: Int) {
        this.textView = textView
        this.textView.addTextChangedListener(this)
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
            textView.error = if (message != null) context.getString(message) else null
            textView.requestFocus()
        } catch (ignored: Exception) {

        }

    }

    override fun stopError() {
        error(null)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        textView.error = null
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        textView.error = null
    }

    override fun afterTextChanged(s: Editable) {
        textView.error = null
    }
}