package com.kdk96.questinfo.domain

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
        const val PLAYER_ID = "player id"
        val QUEST_INFO = QuestInfo(
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
                ),
                PLAYER_ID
        )
        val QUEST_INFO_WITH_NULL_PLAYER_ID = QuestInfo(
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
                ),
                null
        )
    }

    private lateinit var questInfoRepository: QuestInfoRepository
    private lateinit var questInfoInteractor: QuestInfoInteractor

    @Before
    fun setUp() {
        questInfoRepository = mock(QuestInfoRepository::class.java)
        questInfoInteractor = QuestInfoInteractorImpl(QUEST_ID, questInfoRepository)
    }

    @Test
    fun get_quest_info_success() {
        `when`(questInfoRepository.getQuestInfo(QUEST_ID)).thenReturn(Single.just(QUEST_INFO))
        val testObserver = questInfoInteractor.getQuestInfo().test()
        testObserver.assertNoErrors()
                .assertValue(QUEST_INFO)
    }

    @Test
    fun get_quest_info_failure() {
        `when`(questInfoRepository.getQuestInfo(QUEST_ID)).thenReturn(Single.error(IOException()))
        val testObserver = questInfoInteractor.getQuestInfo().test()
        testObserver.assertNoValues()
                .assertError(IOException::class.java)
    }

    @Test
    fun participate_in_quest_success() {
        `when`(questInfoRepository.participateInQuest(QUEST_ID)).thenReturn(Single.just(QUEST_INFO))
        val testObserver = questInfoInteractor.participateInQuest().test()
        testObserver.assertNoErrors()
                .assertValue(QUEST_INFO)
    }

    @Test
    fun participate_in_quest_failure() {
        `when`(questInfoRepository.participateInQuest(QUEST_ID)).thenReturn(Single.error(IOException()))
        val testObserver = questInfoInteractor.participateInQuest().test()
        testObserver.assertNoValues()
                .assertError(IOException::class.java)
    }

    @Test
    fun cancel_participation_success() {
        questInfoInteractor.questInfo = QUEST_INFO
        `when`(questInfoRepository.cancelParticipationInQuest(PLAYER_ID)).thenReturn(Single.just(QUEST_INFO_WITH_NULL_PLAYER_ID))
        val testObserver = questInfoInteractor.cancelParticipation().test()
        testObserver.assertNoErrors()
                .assertValue(QUEST_INFO_WITH_NULL_PLAYER_ID)
    }

    @Test
    fun cancel_participation_failure() {
        questInfoInteractor.questInfo = QUEST_INFO
        `when`(questInfoRepository.cancelParticipationInQuest(PLAYER_ID)).thenReturn(Single.error(IOException()))
        val testObserver = questInfoInteractor.cancelParticipation().test()
        testObserver.assertNoValues()
                .assertError(IOException::class.java)
    }
}