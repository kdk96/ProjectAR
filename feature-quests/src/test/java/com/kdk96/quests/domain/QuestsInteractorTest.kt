package com.kdk96.quests.domain

import com.kdk96.quests.data.repository.QuestsRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException

class QuestsInteractorTest {
    private lateinit var questsRepository: QuestsRepository
    private lateinit var questsInteractor: QuestsInteractor

    @Before
    fun setUp() {
        questsRepository = mock(QuestsRepository::class.java)
        questsInteractor = QuestsInteractor(questsRepository)
    }

    @Test
    fun get_quests_success() {
        val quest1 = Quest("1", "Q1", 1540176230190, "CN1", "CLU1", "GP1")
        val quest2 = Quest("2", "Q2", 1540176230190, "CN2", "CLU2", "GP2")
        val quests = listOf(quest1, quest2)
        `when`(questsRepository.getQuests()).thenReturn(Single.just(quests))
        val testObserver = questsInteractor.getQuests().test()
        testObserver.assertNoErrors()
                .assertValue(quests)
    }

    @Test
    fun get_quests_failure() {
        val exception = IOException()
        `when`(questsRepository.getQuests()).thenReturn(Single.error(exception))
        val testObserver = questsInteractor.getQuests().test()
        testObserver.assertNoValues()
                .assertError(exception)
    }
}