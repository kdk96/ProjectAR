package com.kdk96.auth.domain

import com.kdk96.auth.data.repository.AuthRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mockito.*

class AuthInteractorTest {
    companion object {
        private const val CORRECT_TEST_EMAIL = "johndoe@gmail.com"
        private const val INCORRECT_TEST_EMAIL = "johndoe"
        private const val CORRECT_TEST_PASSWORD = "Chupakabra442"
        private const val INCORRECT_TEST_PASSWORD = "incorrectPassword"
        private const val CORRECT_TEST_NAME = "John Doe"
        private const val CORRECT_TEST_PASSWORD_CONFIRMATION = "Chupakabra442"
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
        val testObserver = authInteractor.checkEmail(CORRECT_TEST_EMAIL).test()
        verify(authRepository).checkEmail(CORRECT_TEST_EMAIL)
        testObserver.assertNoErrors()
                .assertComplete()
    }

    @Test
    fun account_not_exists() {
        `when`(authRepository.checkEmail(CORRECT_TEST_EMAIL))
                .thenReturn(Completable.error(NoSuchAccountException()))
        val testObserver = authInteractor.checkEmail(CORRECT_TEST_EMAIL).test()
        verify(authRepository).checkEmail(CORRECT_TEST_EMAIL)
        testObserver.assertError(NoSuchAccountException::class.java)
    }

    @Test
    fun check_invalid_email() {
        `when`(authRepository.checkEmail(INCORRECT_TEST_EMAIL))
                .thenReturn(Completable.error(InvalidFieldException(setOf(FieldName.EMAIL))))
        val testObserver = authInteractor.checkEmail(INCORRECT_TEST_EMAIL).test()
        verify(authRepository).checkEmail(INCORRECT_TEST_EMAIL)
        testObserver.assertError(InvalidFieldException::class.java)
                .assertErrorMessage("fields: ${FieldName.EMAIL.name}")
    }

    @Test
    fun sign_in_success() {
        `when`(authRepository.authorize(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD))
                .thenReturn(Completable.complete())
        val testObserver = authInteractor.signIn(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD).test()
        verify(authRepository).authorize(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD)
        testObserver.assertNoErrors()
                .assertComplete()
    }

    @Test
    fun sign_in_invalid_password_exception() {
        `when`(authRepository.authorize(CORRECT_TEST_EMAIL, INCORRECT_TEST_PASSWORD))
                .thenReturn(Completable.error(InvalidFieldException(setOf(FieldName.PASSWORD))))
        val testObserver = authInteractor.signIn(CORRECT_TEST_EMAIL, INCORRECT_TEST_PASSWORD).test()
        verify(authRepository).authorize(CORRECT_TEST_EMAIL, INCORRECT_TEST_PASSWORD)
        testObserver.assertError(InvalidFieldException::class.java)
                .assertErrorMessage("fields: ${FieldName.PASSWORD.name}")
    }

    @Test
    fun sign_up_success() {
        `when`(authRepository.register(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD, CORRECT_TEST_NAME))
                .thenReturn(Completable.complete())
        val testObserver = authInteractor.signUp(CORRECT_TEST_EMAIL, CORRECT_TEST_NAME, CORRECT_TEST_PASSWORD, CORRECT_TEST_PASSWORD_CONFIRMATION).test()
        verify(authRepository).register(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD, CORRECT_TEST_NAME)
        testObserver.assertNoErrors()
                .assertComplete()
    }

    @Test
    fun sign_up_invalid_email_exception() {
        `when`(authRepository.register(INCORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD, CORRECT_TEST_NAME))
                .thenReturn(Completable.error(InvalidFieldException(setOf(FieldName.EMAIL))))
        val testObserver = authInteractor.signUp(INCORRECT_TEST_EMAIL, CORRECT_TEST_NAME, CORRECT_TEST_PASSWORD, CORRECT_TEST_PASSWORD_CONFIRMATION).test()
        verify(authRepository).register(INCORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD, CORRECT_TEST_NAME)
        testObserver.assertError(InvalidFieldException::class.java)
                .assertErrorMessage("fields: ${FieldName.EMAIL.name}")
    }

    @Test
    fun sign_up_invalid_password_exception() {
        `when`(authRepository.register(CORRECT_TEST_EMAIL, INCORRECT_TEST_PASSWORD, CORRECT_TEST_NAME))
                .thenReturn(Completable.error(InvalidFieldException(setOf(FieldName.PASSWORD))))
        val testObserver = authInteractor.signUp(CORRECT_TEST_EMAIL, CORRECT_TEST_NAME, INCORRECT_TEST_PASSWORD, CORRECT_TEST_PASSWORD_CONFIRMATION).test()
        verify(authRepository).register(CORRECT_TEST_EMAIL, INCORRECT_TEST_PASSWORD, CORRECT_TEST_NAME)
        testObserver.assertError(InvalidFieldException::class.java)
                .assertErrorMessage("fields: ${FieldName.PASSWORD.name}")
    }

    @Test
    fun sign_up_account_collision_exception() {
        `when`(authRepository.register(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD, CORRECT_TEST_NAME))
                .thenReturn(Completable.error(AccountCollisionException()))
        val testObserver = authInteractor.signUp(CORRECT_TEST_EMAIL, CORRECT_TEST_NAME, CORRECT_TEST_PASSWORD, CORRECT_TEST_PASSWORD_CONFIRMATION).test()
        verify(authRepository).register(CORRECT_TEST_EMAIL, CORRECT_TEST_PASSWORD, CORRECT_TEST_NAME)
        testObserver.assertError(AccountCollisionException::class.java)
    }
}