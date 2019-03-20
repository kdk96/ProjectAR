package com.kdk96.quests.di

import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.quests.data.network.QuestsApi
import com.kdk96.quests.data.repository.QuestsRepositoryImpl
import com.kdk96.quests.domain.QuestsInteractor
import com.kdk96.quests.domain.QuestsInteractorImpl
import com.kdk96.quests.domain.QuestsRepository
import com.kdk96.quests.presenatation.QuestsPresenter
import com.kdk96.quests.presenatation.QuestsReachableScreens
import com.kdk96.quests.ui.QuestsFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit

@PerFragment
@Component(modules = [QuestsModule::class], dependencies = [QuestsDependencies::class])
interface QuestsComponent : DaggerComponent {
    fun inject(questsFragment: QuestsFragment)
}

@Module
abstract class QuestsModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @PerFragment
        fun provideQuestsApi(retrofit: Retrofit) = retrofit.create(QuestsApi::class.java)

        @Provides
        @JvmStatic
        @PerFragment
        fun provideQuestsPresenter(
                router: FlowRouter,
                questsInteractor: QuestsInteractor,
                @Rx.MainThread mainThreadScheduler: Scheduler,
                questsReachableScreens: QuestsReachableScreens
        ) = QuestsPresenter(router, questsInteractor, mainThreadScheduler, questsReachableScreens)
    }

    @Binds
    @PerFragment
    abstract fun provideQuestsRepository(questsRepositoryImpl: QuestsRepositoryImpl): QuestsRepository

    @Binds
    @PerFragment
    abstract fun provideQuestsInteractor(questsInteractorImpl: QuestsInteractorImpl): QuestsInteractor
}