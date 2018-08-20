package com.kdk96.auth.domain

import com.kdk96.auth.data.repository.AuthRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class AuthInteractorTest {
    companion object {
        private const val CORRECT_TEST_EMAIL = "johndoe@gmail.com"
        private const val INCORRECT_TEST_EMAIL = "johndoe"
        private const val CORRECT_TEST_PASSWORD = "Chupakabra442"
        private const val INCORRECT_TEST_PASSWORD = "incorrectPassword"
    }

    @get:Rule
    val thrown = ExpectedException.none()

    private lateinit var authRepository: AuthRepository
    private lateinit var authInteractor: AuthInteractor

    @Before
    fun setUp() {
        authRepository = mock(AuthRepository::class.java)
        authInteractor = AuthInteractor(AuthDataValidator(), authRepository)
    }

    @Test
    fun account_exists() {
        `when`(authRepository.checkEmail(CORRECT_TEST_EMAIL)).thenReturn(Completable.complete())
        val testCompletable = authInteractor.checkEmail(CORRECT_TEST_EMAIL).test()
        testCompletable.assertComplete()
    }

    @Test
    fun account_not_exists() {
        `when`(authRepository.checkEmail(CORRECT_TEST_EMAIL))
                .thenReturn(Completable.error(NoSuchAccountException()))
        val testCompletable = authInteractor.checkEmail(CORRECT_TEST_EMAIL).test()
        testCompletable.assertError(NoSuchAccountException::class.java)
    }

    @Test
    fun check_invalid_email() {
        `when`(authRepository.checkEmail(INCORRECT_TEST_EMAIL))
                .thenReturn(Completable.error(InvalidFieldException(setOf(FieldName.EMAIL))))
        val testCompletable = authInteractor.checkEmail(INCORRECT_TEST_EMAIL).test()
        testCompletable.assertError(InvalidFieldException::class.java)
                .assertErrorMessage("fields: ${FieldName.EMAIL.name}")
    }

    @Test
    fun sign_in_success() {
        `when`(authRepository.authorize(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD))
                .thenReturn(Completable.complete())
        val testCompletable = authInteractor.signIn(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD).test()
        testCompletable.assertComplete()
    }

    @Test
    fun sign_in_invalid_password_exception() {
        `when`(authRepository.authorize(CORRECT_TEST_EMAIL, INCORRECT_TEST_PASSWORD))
                .thenReturn(Completable.error(InvalidFieldException(setOf(FieldName.PASSWORD))))
        val testCompletable = authInteractor.signIn(CORRECT_TEST_EMAIL, INCORRECT_TEST_PASSWORD).test()
        testCompletable.assertError(InvalidFieldException::class.java)
                .assertErrorMessage("fields: ${FieldName.PASSWORD.name}")
    }
}