package com.kdk96.auth.domain

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class AuthDataValidatorTest {
    companion object {
        private const val CORRECT_TEST_EMAIL = "johndoe@gmail.com"
        private const val CORRECT_TEST_PASSWORD = "Chupakabra442"
        private const val INCORRECT_TEST_EMAIL = "johndoe"
    }

    @get:Rule
    val thrown = ExpectedException.none()

    private lateinit var authDataValidator: AuthDataValidator

    @Before
    fun setUp() {
        authDataValidator = AuthDataValidator()
    }

    @Test
    fun validate_sign_in_data_success() {
        authDataValidator.validateSignInData(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD)
    }

    @Test
    fun validate_sign_in_data_empty_email_exception() {
        thrown.expect(EmptyFieldException::class.java)
        thrown.expectMessage("fields: ${FieldName.EMAIL.name}")
        authDataValidator.validateSignInData("", CORRECT_TEST_PASSWORD)
    }

    @Test
    fun validate_sign_in_data_empty_password_exception() {
        thrown.expect(EmptyFieldException::class.java)
        thrown.expectMessage("fields: ${FieldName.PASSWORD.name}")
        authDataValidator.validateSignInData(CORRECT_TEST_EMAIL, "")
    }

    @Test
    fun validate_sign_in_data_empty_email_and_password_exception() {
        thrown.expect(EmptyFieldException::class.java)
        thrown.expectMessage("fields: ${FieldName.EMAIL.name}, ${FieldName.PASSWORD.name}")
        authDataValidator.validateSignInData("", "")
    }

    @Test
    fun validate_sign_in_data_invalid_email_exception() {
        thrown.expect(InvalidFieldException::class.java)
        thrown.expectMessage("fields: ${FieldName.EMAIL.name}")
        authDataValidator.validateSignInData(INCORRECT_TEST_EMAIL)
    }
}