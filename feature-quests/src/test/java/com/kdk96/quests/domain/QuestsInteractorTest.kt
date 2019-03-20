package com.kdk96.quests.domain

import com.kdk96.quests.domain.entity.QuestShortInfo
import io.reactivex.Observable
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
        questsInteractor = QuestsInteractorImpl(questsRepository)
    }

    @Test
    fun get_quests_success() {
        val quest1 = QuestShortInfo("1", "Q1", 1540176230190, "CN1", "CLU1", "GP1")
        val quest2 = QuestShortInfo("2", "Q2", 1540176230190, "CN2", "CLU2", "GP2")
        val quests = listOf(quest1, quest2)
        `when`(questsRepository.getQuests()).thenReturn(Observable.just(quests))
        val testObserver = questsInteractor.getQuests().test()
        testObserver.assertNoErrors()
                .assertValue(quests)
    }

    @Test
    fun get_quests_failure() {
        `when`(questsRepository.getQuests()).thenReturn(Observable.error(IOException()))
        val testObserver = questsInteractor.getQuests().test()
        testObserver.assertNoValues()
                .assertError(IOException::class.java)
    }
}