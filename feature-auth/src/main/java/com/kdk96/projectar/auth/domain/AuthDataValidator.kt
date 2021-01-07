package com.kdk96.projectar.auth.domain

import com.kdk96.projectar.common.domain.validation.Valid
import com.kdk96.projectar.common.domain.validation.Violation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataValidator @Inject constructor(
    private val authErrorMessageProvider: AuthErrorMessageProvider
) {

    fun validateEmail(email: String) = when {
        email.isBlank() -> Violation(authErrorMessageProvider.emptyEmail)
        !EMAIL_REGEX.matches(email) -> Violation(authErrorMessageProvider.invalidEmail)
        else -> Valid
    }

    companion object {
        private val EMAIL_REGEX = Regex("^.+@.+\\..+$")
        private val PASSWORD_REGEX = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$")
    }
//
//    fun validateSignInData(email: String, password: String? = null) = validateData(
//            { checkOnEmptySignInData(it, email, password) },
//            { validateEmail(it, email) }
//    )
//
//    fun validateSignUpData(
//            email: String,
//            name: String,
//            password: String,
//            passwordConfirmation: String
//    ) = validateData({
//        checkOnEmptySignInData(it, email, password)
//        checkOnEmptySignUpData(it, name, passwordConfirmation)
//    }, {
//        validateEmail(it, email)
//        validatePassword(it, password, passwordConfirmation)
//    })
//
//    private inline fun validateData(checkEmpty: ValidateFunction, validateData: ValidateFunction) {
//        val fieldSet = EnumSet.noneOf(FieldName::class.java)
//        checkEmpty(fieldSet)
//        if (fieldSet.isNotEmpty()) throw EmptyFieldException(fieldSet)
//        validateData(fieldSet)
//        if (fieldSet.isNotEmpty()) throw InvalidFieldException(fieldSet)
//    }
//
//    private fun checkOnEmptySignInData(fieldSet: MutableSet<FieldName>, email: String, password: String?) {
//        if (email.isEmpty()) fieldSet.add(FieldName.EMAIL)
//        if (password != null && password.isEmpty()) fieldSet.add(FieldName.PASSWORD)
//    }
//
//    private fun checkOnEmptySignUpData(fieldSet: MutableSet<FieldName>, name: String, passwordConfirmation: String) {
//        if (name.isEmpty()) fieldSet.add(FieldName.NAME)
//        if (passwordConfirmation.isEmpty()) fieldSet.add(FieldName.PASSWORD_CONFIRMATION)
//    }
//
//    private fun validateEmail(fieldSet: MutableSet<FieldName>, email: String) {
//        if (!EMAIL_PATTERN.matcher(email).matches()) fieldSet.add(FieldName.EMAIL)
//    }
//
//    private fun validatePassword(fieldSet: MutableSet<FieldName>, password: String, passwordConfirmation: String) {
//        if (!PASSWORD_PATTERN.matcher(password).matches()) {
//            fieldSet.add(FieldName.PASSWORD)
//            return
//        }
//        if (password != passwordConfirmation) fieldSet.add(FieldName.PASSWORD_CONFIRMATION)
//    }
}
