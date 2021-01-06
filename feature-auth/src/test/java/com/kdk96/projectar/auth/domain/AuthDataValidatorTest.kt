//package com.kdk96.projectar.auth.domain
//
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.rules.ExpectedException
//
//class AuthDataValidatorTest {
//    companion object {
//        private const val CORRECT_TEST_EMAIL = "johndoe@gmail.com"
//        private const val CORRECT_TEST_PASSWORD = "Chupakabra442"
//        private const val INCORRECT_TEST_EMAIL = "johndoe"
//        private const val CORRECT_TEST_NAME = "John Doe"
//        private const val CORRECT_TEST_PASSWORD_CONFIRMATION = "Chupakabra442"
//        private const val INCORRECT_TEST_PASSWORD = "incorrectPassword"
//        private const val INCORRECT_TEST_PASSWORD_CONFIRMATION = "incorrectPasswordConfirmation"
//    }
//
//    @get:Rule
//    val thrown = ExpectedException.none()
//
//    private lateinit var authDataValidator: AuthDataValidator
//
//    @Before
//    fun setUp() {
//        authDataValidator = AuthDataValidator()
//    }
//
//    @Test
//    fun validate_sign_in_data_success() {
//        authDataValidator.validateSignInData(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD)
//    }
//
//    @Test
//    fun validate_sign_in_data_empty_email_exception() {
//        thrown.expect(EmptyFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.EMAIL.name}")
//        authDataValidator.validateSignInData("", CORRECT_TEST_PASSWORD)
//    }
//
//    @Test
//    fun validate_sign_in_data_empty_password_exception() {
//        thrown.expect(EmptyFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.PASSWORD.name}")
//        authDataValidator.validateSignInData(CORRECT_TEST_EMAIL, "")
//    }
//
//    @Test
//    fun validate_sign_in_data_empty_email_and_password_exception() {
//        thrown.expect(EmptyFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.EMAIL.name}, ${FieldName.PASSWORD.name}")
//        authDataValidator.validateSignInData("", "")
//    }
//
//    @Test
//    fun validate_sign_in_data_invalid_email_exception() {
//        thrown.expect(InvalidFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.EMAIL.name}")
//        authDataValidator.validateSignInData(INCORRECT_TEST_EMAIL)
//    }
//
//    @Test
//    fun validate_sign_up_data_success() {
//        authDataValidator.validateSignUpData(CORRECT_TEST_EMAIL, CORRECT_TEST_NAME, CORRECT_TEST_PASSWORD, CORRECT_TEST_PASSWORD_CONFIRMATION)
//    }
//
//    @Test
//    fun validate_sign_up_data_empty_name_exception() {
//        thrown.expect(EmptyFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.NAME.name}")
//        authDataValidator.validateSignUpData(CORRECT_TEST_EMAIL, "", CORRECT_TEST_PASSWORD, CORRECT_TEST_PASSWORD_CONFIRMATION)
//    }
//
//    @Test
//    fun validate_sign_up_data_empty_password_confirmation_exception() {
//        thrown.expect(EmptyFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.PASSWORD_CONFIRMATION.name}")
//        authDataValidator.validateSignUpData(CORRECT_TEST_EMAIL, CORRECT_TEST_NAME, CORRECT_TEST_PASSWORD, "")
//    }
//
//    @Test
//    fun validate_sign_up_data_empty_email_and_name_and_password_and_password_confirmation_exception() {
//        thrown.expect(EmptyFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.EMAIL.name}, ${FieldName.PASSWORD.name}, ${FieldName.NAME.name}, ${FieldName.PASSWORD_CONFIRMATION.name}")
//        authDataValidator.validateSignUpData("", "", "", "")
//    }
//
//    @Test
//    fun validate_sign_up_data_invalid_password_exception() {
//        thrown.expect(InvalidFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.PASSWORD.name}")
//        authDataValidator.validateSignUpData(CORRECT_TEST_EMAIL, CORRECT_TEST_NAME, INCORRECT_TEST_PASSWORD, CORRECT_TEST_PASSWORD_CONFIRMATION)
//    }
//
//    @Test
//    fun validate_sign_up_data_invalid_password_confirmation_exception() {
//        thrown.expect(InvalidFieldException::class.java)
//        thrown.expectMessage("fields: ${FieldName.PASSWORD_CONFIRMATION.name}")
//        authDataValidator.validateSignUpData(CORRECT_TEST_EMAIL, CORRECT_TEST_NAME, CORRECT_TEST_PASSWORD, INCORRECT_TEST_PASSWORD_CONFIRMATION)
//    }
//}