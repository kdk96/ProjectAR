package com.kdk96.questinfo.domain

import com.kdk96.questinfo.data.repository.QuestInfoRepository
import com.kdk96.questinfo.domain.entity.Prize
import com.kdk96.questinfo.domain.entity.QuestInfo
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException

class QuestInfoInteractorTest {
    companion object {
        const val QUEST_ID = "quest id"
    }

    private lateinit var questInfoRepository: QuestInfoRepository
    private lateinit var questInfoInteractor: QuestInfoInteractor

    @Before
    fun setUp() {
        questInfoRepository = mock(QuestInfoRepository::class.java)
        questInfoInteractor = QuestInfoInteractor(QUEST_ID, questInfoRepository)
    }

    @Test
    fun get_quest_info_success() {
        val questInfo = QuestInfo(
                QUEST_ID,
                "quest",
                "nice quest",
                1545670800000,
                "comp id",
                "best company",
                "logo url",
                56.474244,
                84.951031,
                listOf(
                        Prize("prize1", "prize 1", 1),
                        Prize("prize2", "prize 2", 2)
                )
        )
        `when`(questInfoRepository.getQuestInfo(QUEST_ID)).thenReturn(Single.just(questInfo))
        val testObserver = questInfoInteractor.getQuestInfo().test()
        testObserver.assertNoErrors()
                .assertValue(questInfo)
    }

    @Test
    fun get_quest_info_failure() {
        `when`(questInfoRepository.getQuestInfo(QUEST_ID)).thenReturn(Single.error(IOException()))
        val testObserver = questInfoInteractor.getQuestInfo().test()
        testObserver.assertNoValues()
                .assertError(IOException::class.java)
    }
}