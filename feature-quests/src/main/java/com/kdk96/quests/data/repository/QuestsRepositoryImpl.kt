package com.kdk96.quests.data.repository

import com.kdk96.common.di.Rx
import com.kdk96.database.Database
import com.kdk96.database.entity.Company
import com.kdk96.database.entity.Prize
import com.kdk96.database.entity.Quest
import com.kdk96.quests.data.network.QuestsApi
import com.kdk96.quests.data.network.entity.QuestResponse
import com.kdk96.quests.domain.QuestsRepository
import com.kdk96.quests.domain.entity.QuestShortInfo
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class QuestsRepositoryImpl @Inject constructor(
        private val api: QuestsApi,
        @Rx.Io private val ioScheduler: Scheduler,
        private val database: Database
) : QuestsRepository {
    override fun getQuests() = Observable.concat(
            database.questDao().getQuestWithGrandPrizeAndCompanyList().toObservable(),
            getQuestsFromServer().toObservable()
    ).subscribeOn(ioScheduler)

    private fun getQuestsFromServer() = api.getQuests()
            .doOnSuccess(::saveInDatabase)
            .toObservable()
            .flatMapIterable { it }
            .map {
                with(it) {
                    QuestShortInfo(
                            id,
                            title,
                            startTime,
                            companyName,
                            companyLogoUrl,
                            grandPrizeDescription
                    )
                }
            }.toList()

    private fun saveInDatabase(quests: List<QuestResponse>) {
        val questList = mutableListOf<Quest>()
        val companySet = mutableSetOf<Company>()
        val prizeList = mutableListOf<Prize>()
        quests.forEach {
            with(it) {
                questList.add(Quest(id, title, startTime, companyId))
                companySet.add(Company(companyId, companyName, companyLogoUrl))
                prizeList.add(Prize(grandPrizeId, grandPrizeDescription, 1, id))
            }
        }
        with(database) {
            runInTransaction {
                companyDao().deleteAll()
                companyDao().insertAll(companySet)
                questDao().insertAll(questList)
                prizeDao().insertAll(prizeList)
            }
        }
    }
}