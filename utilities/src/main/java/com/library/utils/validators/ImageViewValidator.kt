package com.library.utils.validators

import android.widget.ImageView
import android.widget.Toast


@Suppress("UNUSED")
class ImageViewValidator(private val imageView: ImageView, private val errorMessage: Int) : FormValidator.Validator() {


    override val isValid: Boolean
        get() = imageView.drawable != null

    override fun showError() {
        error(errorMessage)
    }

    private fun error(message: Int?) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun stopError() {
        //        error(null);
    }
}
