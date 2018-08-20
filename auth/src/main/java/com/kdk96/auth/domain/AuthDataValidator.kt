package com.kdk96.auth.domain

import java.util.regex.Pattern

class AuthDataValidator {
    companion object {
        private val EMAIL_PATTERN = Pattern.compile("^.+@.+\\..+$")
        private val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$")
    }

    fun validateSignInData(email: String, password: String? = null) {
        val fieldSet = mutableSetOf<FieldName>()
        checkOnEmptySignInData(fieldSet, email, password)
        if (fieldSet.isNotEmpty()) throw EmptyFieldException(fieldSet)
        validateEmail(fieldSet, email)
        if (fieldSet.isNotEmpty()) throw InvalidFieldException(fieldSet)
    }

    fun validateSignUpData(email: String, name: String, password: String, passwordConfirmation: String) {
        val fieldSet = mutableSetOf<FieldName>()
        checkOnEmptySignInData(fieldSet, email, password)
        checkOnEmptySignUpData(fieldSet, name, passwordConfirmation)
        if (fieldSet.isNotEmpty()) throw EmptyFieldException(fieldSet)
        validateEmail(fieldSet, email)
        validatePassword(fieldSet, password, passwordConfirmation)
        if (fieldSet.isNotEmpty()) throw InvalidFieldException(fieldSet)
    }

    private fun checkOnEmptySignInData(fieldSet: MutableSet<FieldName>, email: String, password: String?) {
        if (email.isEmpty()) fieldSet.add(FieldName.EMAIL)
        if (password != null && password.isEmpty()) fieldSet.add(FieldName.PASSWORD)
    }

    private fun checkOnEmptySignUpData(fieldSet: MutableSet<FieldName>, name: String, passwordConfirmation: String) {
        if (name.isEmpty()) fieldSet.add(FieldName.NAME)
        if (passwordConfirmation.isEmpty()) fieldSet.add(FieldName.PASSWORD_CONFIRMATION)
    }

    private fun validateEmail(fieldSet: MutableSet<FieldName>, email: String) {
        if (!EMAIL_PATTERN.matcher(email).matches()) fieldSet.add(FieldName.EMAIL)
    }

    private fun validatePassword(fieldSet: MutableSet<FieldName>, password: String, passwordConfirmation: String) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            fieldSet.add(FieldName.PASSWORD)
            return
        }
        if (password != passwordConfirmation) fieldSet.add(FieldName.PASSWORD_CONFIRMATION)
    }
}