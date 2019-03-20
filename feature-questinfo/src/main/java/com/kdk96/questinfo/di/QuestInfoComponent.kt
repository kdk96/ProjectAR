package com.kdk96.questinfo.di

import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.questinfo.data.network.QuestInfoApi
import com.kdk96.questinfo.data.repository.QuestInfoRepositoryImpl
import com.kdk96.questinfo.domain.QuestInfoInteractor
import com.kdk96.questinfo.domain.QuestInfoInteractorImpl
import com.kdk96.questinfo.domain.QuestInfoRepository
import com.kdk96.questinfo.presentation.QuestInfoPresenter
import com.kdk96.questinfo.presentation.QuestInfoReachableScreens
import com.kdk96.questinfo.ui.QuestInfoFragment
import dagger.*
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router

@PerFragment
@Component(modules = [QuestInfoModule::class], dependencies = [QuestInfoDependencies::class])
interface QuestInfoComponent : DaggerComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun questId(questId: String): Builder
        fun questInfoDependencies(questInfoDependencies: QuestInfoDependencies): Builder
        fun build(): QuestInfoComponent
    }

    fun inject(questInfoFragment: QuestInfoFragment)
}

@Module
abstract class QuestInfoModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @PerFragment
        fun provideApi(retrofit: Retrofit) = retrofit.create(QuestInfoApi::class.java)

        @Provides
        @JvmStatic
        @PerFragment
        fun provideQuestInfoPresenter(
                router: Router,
                questInfoInteractor: QuestInfoInteractor,
                @Rx.MainThread mainThreadScheduler: Scheduler,
                screens: QuestInfoReachableScreens
        ) = QuestInfoPresenter(router, questInfoInteractor, mainThreadScheduler, screens)
    }

    @Binds
    @PerFragment
    abstract fun provideQuestInfoRepository(questInfoRepositoryImpl: QuestInfoRepositoryImpl): QuestInfoRepository

    @Binds
    @PerFragment
    abstract fun provideQuestInfoInteractor(questInfoInteractorImpl: QuestInfoInteractorImpl): QuestInfoInteractor
}