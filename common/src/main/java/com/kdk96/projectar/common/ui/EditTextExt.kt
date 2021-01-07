package com.kdk96.projectar.common.ui

import android.widget.EditText

fun EditText.setTextIfNotFocused(text: CharSequence) {
    if (!isFocused) {
        setText(text)
    }
}
