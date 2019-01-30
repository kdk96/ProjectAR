package com.kdk96.settings.domain

import com.kdk96.settings.data.repository.AccountRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import java.io.IOException

class AccountInteractorTest {
    companion object {
        const val TEST_EMAIL = "johndoe@gmail.com"
        const val TEST_NAME = "John Doe"
        const val TEST_AVATAR_URL = "https://vignette.wikia.nocookie.net/villains/images/4/4a/Kevin_Spacey_John_Doe_Se7en.jpeg/revision/latest?cb=20171230134058"
        const val ANOTHER_TEST_EMAIL = "john_doe@gmail.com"
        const val ANOTHER_TEST_NAME = "Doe John"
        const val ANOTHER_TEST_AVATAR_URL = "http://www.frenchtoastsunday.com/wp-content/uploads/2013/11/John-Doe-Seven-1.png"
        const val TEST_PATH = "/folder/avatar.jpg"
    }

    private lateinit var accountRepository: AccountRepository
    private lateinit var router: Router
    private lateinit var accountInteractor: AccountInteractor

    @Before
    fun setUp() {
        accountRepository = mock(AccountRepository::class.java)
        router = mock(Router::class.java)
        accountInteractor = AccountInteractor(
                accountRepository,
                router,
                Schedulers.trampoline(),
                object : SupportAppScreen() {}
        )
    }

    @Test
    fun is_signed_in() {
        `when`(accountRepository.isSingedIn()).thenReturn(true)
        val signedIn = accountInteractor.isSignedIn()
        verify(accountRepository).isSingedIn()
        Assert.assertTrue(signedIn)
    }

    @Test
    fun is_not_signed_in() {
        `when`(accountRepository.isSingedIn()).thenReturn(false)
        val signedIn = accountInteractor.isSignedIn()
        verify(accountRepository).isSingedIn()
        Assert.assertFalse(signedIn)
    }

    @Test
    fun account_data_changes() {
        val accountDataChanges = BehaviorSubject.create<AccountData>()
        val firstTestData = AccountData(TEST_EMAIL, TEST_NAME, TEST_AVATAR_URL)
        val secondTestData = AccountData(ANOTHER_TEST_EMAIL, ANOTHER_TEST_NAME, ANOTHER_TEST_AVATAR_URL)
        `when`(accountRepository.accountDataChanges).thenReturn(accountDataChanges)
        val testObserver = accountInteractor.accountDataChanges.test()
        accountRepository.accountDataChanges.onNext(firstTestData)
        accountRepository.accountDataChanges.onNext(secondTestData)
        testObserver.assertNoErrors()
                .assertValueCount(2)
                .assertValues(firstTestData, secondTestData)
    }

    @Test
    fun get_account_data_success() {
        `when`(accountRepository.getAccountData()).thenReturn(Completable.complete())
        val testObserver = accountInteractor.getAccountData().test()
        testObserver.assertNoErrors()
                .assertComplete()
    }

    @Test
    fun get_account_data_failure() {
        `when`(accountRepository.getAccountData()).thenReturn(Completable.error(IOException()))
        val testObserver = accountInteractor.getAccountData().test()
        testObserver.assertNoValues()
                .assertError(IOException::class.java)
    }

    @Test
    fun update_avatar_success() {
        `when`(accountRepository.updateAvatar(TEST_PATH)).thenReturn(Completable.complete())
        val testObserver = accountInteractor.updateAvatar(TEST_PATH).test()
        testObserver.assertNoErrors()
                .assertComplete()
    }

    @Test
    fun update_avatar_failure() {
        `when`(accountRepository.updateAvatar(TEST_PATH)).thenReturn(Completable.error(IOException()))
        val testObserver = accountInteractor.updateAvatar(TEST_PATH).test()
        testObserver.assertNoValues()
                .assertError(IOException::class.java)
    }

    @Test
    fun sign_out() {
        `when`(accountRepository.signOut()).thenReturn(Completable.complete())
        val testObserver = accountInteractor.signOut().test()
        testObserver.assertNoErrors()
                .assertComplete()
    }
}