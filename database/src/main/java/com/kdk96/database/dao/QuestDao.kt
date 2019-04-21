package com.kdk96.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kdk96.database.entity.Quest
import com.kdk96.quests.domain.entity.QuestShortInfo
import io.reactivex.Single

@Dao
interface QuestDao {
    @Insert
    fun insertAll(quests: List<Quest>)

    @Query(
        """
        SELECT Q.id AS id, Q.title AS title, Q.start_time AS startTime, C.name AS companyName, C.logo_url AS companyLogoUrl, P.description AS grandPrizeDescription
        FROM QUESTS AS Q
        INNER JOIN COMPANIES AS C ON Q.company_id == C.id
        INNER JOIN PRIZES AS P ON Q.id == P.quest_id
        WHERE P.place == 1
        """
    )
    fun getQuestWithGrandPrizeAndCompanyList(): Single<List<QuestShortInfo>>
}