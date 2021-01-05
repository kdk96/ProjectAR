package com.kdk96.projectar.common.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Screen

val TextView.trimmedString: String
    get() = text.trim().toString()

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun Activity.hideKeyboard() {
    currentFocus?.let {
        hideKeyboard(it)
        it.clearFocus()
    }
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Navigator.setLaunchScreen(screen: Screen) {
    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}