package com.kdk96.auth.domain

import java.util.regex.Pattern

class AuthDataValidator {
    companion object {
        private val EMAIL_PATTERN = Pattern.compile("^.+@.+\\..+$")
    }

    fun validateSignInData(email: String, password: String? = null) {
        val fieldSet = mutableSetOf<FieldName>()
        checkOnEmptySignInData(fieldSet, email, password)
        if (fieldSet.isNotEmpty()) throw EmptyFieldException(fieldSet)
        validateEmail(fieldSet, email)
        if (fieldSet.isNotEmpty()) throw InvalidFieldException(fieldSet)
    }

    private fun checkOnEmptySignInData(fieldSet: MutableSet<FieldName>, email: String, password: String?) {
        if (email.isEmpty()) fieldSet.add(FieldName.EMAIL)
        if (password != null && password.isEmpty()) fieldSet.add(FieldName.PASSWORD)
    }

    private fun validateEmail(fieldSet: MutableSet<FieldName>, email: String) {
        if (!EMAIL_PATTERN.matcher(email).matches()) fieldSet.add(FieldName.EMAIL)
    }
}