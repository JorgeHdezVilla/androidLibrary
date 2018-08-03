package com.library.libreria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.library.utils.validators.CustomValidator;
import com.library.utils.validators.FormValidator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FormValidator formValidator = new FormValidator(this, true);
        formValidator.addValidators(
                new CustomValidator(() -> {
                    Integer x = 5;
                    Integer y = 6;
                    return x == y;
                }, "puto")
        );
    }
}
