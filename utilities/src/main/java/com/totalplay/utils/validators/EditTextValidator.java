package com.totalplay.utils.validators;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("unused")
public class EditTextValidator extends FormValidator.Validator {

    private final EditText editText;
    private final int errorMessage;
    private final Pattern pattern;
    private final int minCharacters;
    private final int maxCharacters;

    public EditTextValidator(EditText editText, int errorMessage) {
        this.editText = editText;
        this.errorMessage = errorMessage;
        this.minCharacters = -1;
        this.maxCharacters = -1;
        this.pattern = null;
    }

    public EditTextValidator(EditText editText, String regex, int errorMessage) {
        this.editText = editText;
        this.errorMessage = errorMessage;
        this.minCharacters = -1;
        this.maxCharacters = -1;
        this.pattern = Pattern.compile(regex);
    }

    public EditTextValidator(EditText editText, int minCharacters, int maxCharacters, int errorMessage) {
        this.editText = editText;
        this.errorMessage = errorMessage;
        this.minCharacters = minCharacters;
        this.maxCharacters = maxCharacters;
        if (minCharacters < 0) {
            throw new IllegalArgumentException("[EditTextValidator] Minimum number of characters is 0");
        }
        this.pattern = null;
    }

    @Override
    public boolean isValid() {
        boolean valid = true;
        ViewGroup viewGroup = ((ViewGroup) editText.getParent());
        ViewGroup secondViewGroup = (ViewGroup) editText.getParent().getParent();
        if (editText.getVisibility() == View.VISIBLE
                && (viewGroup == null || viewGroup.getVisibility() == View.VISIBLE)
                && (secondViewGroup == null || secondViewGroup.getVisibility() == View.VISIBLE)) {
            if (minCharacters != -1) {
                valid = editText.getText().toString().trim().length() >= minCharacters;
            }
            if (maxCharacters != -1) {
                valid &= maxCharacters >= editText.getText().toString().trim().length();
            }
            if (pattern != null) {
                Matcher matcher = pattern.matcher(editText.getText().toString().trim());
                return valid & matcher.matches();
            } else {
                valid = !editText.getText().toString().trim().isEmpty();
            }
        }
        return valid;
    }

    @Override
    public void showError() {
        error(errorMessage);
    }

    private void error(Integer message) {
        try {
            TextInputLayout textInputLayout = (TextInputLayout) editText.getParent();
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(message != null ? context.getString(message) : null);
        } catch (ClassCastException ignored) {
            editText.setError(message != null ? context.getString(message) : null);
        }
    }

    @Override
    void stopError() {
        error(null);
    }
}
