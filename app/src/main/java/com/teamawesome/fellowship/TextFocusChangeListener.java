package com.teamawesome.fellowship;

import android.view.View;
import android.widget.EditText;

/**
 * Created by Aronson1 on 5/22/18.
 */

public class TextFocusChangeListener implements View.OnFocusChangeListener {
    private String defaultString;
    private EditText editText;

    public TextFocusChangeListener(String defaultString, EditText editText) {
        this.defaultString = defaultString;
        this.editText = editText;
    }

    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if(this.editText.getText().toString().equals(this.defaultString)) {
                this.editText.setText("");
            }
        }
        else {
            if(this.editText.getText().toString().isEmpty()) {
                this.editText.setText(this.defaultString);
            }
        }
    }

}
