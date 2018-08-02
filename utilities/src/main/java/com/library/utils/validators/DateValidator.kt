package com.library.utils.validators

import android.widget.EditText
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNUSED")
class DateValidator : FormValidator.Validator {

    private val dateEditText: EditText
    private val dateCompare: String
    private val errorMessage: Int
    private val typeValidate: Int

    companion object {
        private const val BEFORE_DATE_SYSTEM = 1
        private const val BEFORE_DATE = 2
        private const val AFTER_DATE = 3
        private const val EQUAL_DATE = 4
        private const val NOT_EQUAL_DATE = 5
    }

    override val isValid: Boolean
        get() {
            var valid = true
            val formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            try {
                val dateSecond: Date
                val dateFirst: Date
                when (typeValidate) {
                    BEFORE_DATE_SYSTEM -> {
                        dateSecond = formatDate.parse(formatDate.format(Date()))
                        dateFirst = formatDate.parse(dateEditText.text.toString().trim() { it <= ' ' })
                        if (dateFirst == dateSecond || dateFirst.after(dateSecond)) {
                            valid = false
                        }
                    }
                    BEFORE_DATE -> {
                        dateSecond = formatDate.parse(dateCompare)
                        dateFirst = formatDate.parse(dateEditText.text.toString().trim { it <= ' ' })
                        valid = dateFirst.before(dateSecond)
                    }
                    AFTER_DATE -> {
                        dateSecond = formatDate.parse(dateCompare)
                        dateFirst = formatDate.parse(dateEditText.text.toString().trim { it <= ' ' })
                        valid = dateFirst.after(dateSecond)
                    }
                    EQUAL_DATE -> {
                        dateSecond = formatDate.parse(dateCompare)
                        dateFirst = formatDate.parse(dateEditText.text.toString().trim { it <= ' ' })
                        valid = dateFirst == dateSecond
                    }
                    NOT_EQUAL_DATE -> {
                        dateSecond = formatDate.parse(dateCompare)
                        dateFirst = formatDate.parse(dateEditText.text.toString().trim { it <= ' ' })
                        valid = dateFirst != dateSecond
                    }
                }
            } catch (e: ParseException) {
                e.printStackTrace()
                valid = false
            }

            return valid
        }

    constructor(dateEditText: EditText, typeValidate: Int, errorMessage: Int) {
        this.dateEditText = dateEditText
        this.errorMessage = errorMessage
        this.typeValidate = typeValidate
        this.dateCompare = ""
    }

    constructor(dateEditText: EditText, dateCompare: String, typeValidate: Int, errorMessage: Int) {
        this.dateEditText = dateEditText
        this.errorMessage = errorMessage
        this.typeValidate = typeValidate
        this.dateCompare = dateCompare
    }

    override fun showError() {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun stopError() {

    }
}
