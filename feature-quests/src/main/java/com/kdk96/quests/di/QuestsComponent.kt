package com.kdk96.quests.di

import com.kdk96.common.di.Component
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.database.Database
import com.kdk96.quests.data.network.QuestsApi
import com.kdk96.quests.data.repository.QuestsRepository
import com.kdk96.quests.domain.QuestsInteractor
import com.kdk96.quests.presenatation.QuestsPresenter
import com.kdk96.quests.ui.QuestsFragment
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router

@PerFragment
@dagger.Component(modules = [QuestsModule::class], dependencies = [QuestsDependencies::class])
interface QuestsComponent : Component {
    fun inject(questsFragment: QuestsFragment)
}

@Module
object QuestsModule {
    @Provides
    @JvmStatic
    @PerFragment
    fun provideQuestsApi(retrofit: Retrofit) = retrofit.create(QuestsApi::class.java)

    @Provides
    @JvmStatic
    @PerFragment
    fun provideQuestsRepository(api: QuestsApi, @Rx.Io ioScheduler: Scheduler, database: Database) =
            QuestsRepository(api, ioScheduler, database)

    @Provides
    @JvmStatic
    @PerFragment
    fun provideQuestsInteractor(questsRepository: QuestsRepository) =
            QuestsInteractor(questsRepository)

    @Provides
    @JvmStatic
    @PerFragment
    fun provideQuestsPresenter(
            router: Router,
            questsInteractor: QuestsInteractor,
            @Rx.MainThread mainThreadScheduler: Scheduler
    ) = QuestsPresenter(router, questsInteractor, mainThreadScheduler)
}