package com.kdk96.prizes.domain

import com.kdk96.prizes.data.repository.PrizesRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException

class PrizesInteractorTest {
    private lateinit var prizesRepository: PrizesRepository
    private lateinit var prizesInteractor: PrizesInteractor

    @Before
    fun setUp() {
        prizesRepository = mock(PrizesRepository::class.java)
        prizesInteractor = PrizesInteractor(prizesRepository)
    }

    @Test
    fun get_prizes_success() {
        val prize1 = Prize("1", "d1", "C1", "QT1", "PIU1", "RC1")
        val prize2 = Prize("2", "d2", "C2", "QT2", "PIU2", "RC2")
        val prizes = listOf(prize1, prize2)
        `when`(prizesRepository.getPrizes()).thenReturn(Single.just(prizes))
        val testObserver = prizesInteractor.getPrizes().test()
        testObserver.assertNoErrors()
                .assertValue(prizes)
    }

    @Test
    fun get_prizes_failure() {
        `when`(prizesRepository.getPrizes()).thenReturn(Single.error(IOException()))
        val testObserver = prizesInteractor.getPrizes().test()
        testObserver.assertNoValues()
                .assertError(IOException::class.java)
    }
}