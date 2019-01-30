package com.kdk96.questinfo.di

import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.questinfo.data.network.QuestInfoApi
import com.kdk96.questinfo.data.repository.QuestInfoRepository
import com.kdk96.questinfo.domain.QuestInfoInteractor
import com.kdk96.questinfo.presentation.QuestInfoPresenter
import com.kdk96.questinfo.ui.QuestInfoFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit

@PerFragment
@Component(modules = [QuestInfoModule::class], dependencies = [QuestInfoDependencies::class])
interface QuestInfoComponent : DaggerComponent {
    fun inject(questInfoFragment: QuestInfoFragment)
}

@Module
object QuestInfoModule {
    @Provides
    @JvmStatic
    @PerFragment
    fun provideApi(retrofit: Retrofit) = retrofit.create(QuestInfoApi::class.java)

    @Provides
    @JvmStatic
    @PerFragment
    fun provideQuestInfoRepository(api: QuestInfoApi, @Rx.Io ioScheduler: Scheduler) =
            QuestInfoRepository(api, ioScheduler)

    @Provides
    @JvmStatic
    @PerFragment
    fun provideQuestInfoInteractor(questId: String, questInfoRepository: QuestInfoRepository) =
            QuestInfoInteractor(questId, questInfoRepository)

    @Provides
    @JvmStatic
    @PerFragment
    fun provideQuestInfoPresenter(
            flowRouter: FlowRouter,
            questInfoInteractor: QuestInfoInteractor,
            @Rx.MainThread mainThreadScheduler: Scheduler
    ) = QuestInfoPresenter(flowRouter, questInfoInteractor, mainThreadScheduler)
}