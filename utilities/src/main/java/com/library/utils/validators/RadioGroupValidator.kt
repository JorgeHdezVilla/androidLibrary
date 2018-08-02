package com.library.utils.validators

import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast


@Suppress("UNUSED")
class RadioGroupValidator(private val radioGroup: RadioGroup, private val errorMessage: Int) : FormValidator.Validator() {

    override val isValid: Boolean
        get() {
            val count = radioGroup.childCount
            var atLeastOneChecked = false
            for (i in 0 until count) {
                val o = radioGroup.getChildAt(i)
                if (o is RadioButton) {
                    atLeastOneChecked = o.isChecked || atLeastOneChecked
                }
            }
            return atLeastOneChecked
        }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun showError() {
        //        int count = radioGroup.getChildCount();
        //        for (int i = 0; i < count; i++) {
        //            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
        //            radioButton.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        //            radioButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.holo_red_dark)));
        //        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun stopError() {

    }
}
