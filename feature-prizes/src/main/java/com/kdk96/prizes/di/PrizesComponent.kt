package com.kdk96.prizes.di

import com.kdk96.common.di.Component
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.prizes.data.network.PrizesApi
import com.kdk96.prizes.data.repository.PrizesRepository
import com.kdk96.prizes.domain.PrizesInteractor
import com.kdk96.prizes.presentation.PrizesPresenter
import com.kdk96.prizes.ui.PrizesFragment
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router

@PerFragment
@dagger.Component(modules = [PrizesModule::class], dependencies = [PrizesDependencies::class])
interface PrizesComponent : Component {
    fun inject(prizesFragment: PrizesFragment)
}

@Module
object PrizesModule {
    @Provides
    @JvmStatic
    @PerFragment
    fun providePrizesApi(retrofit: Retrofit) = retrofit.create(PrizesApi::class.java)

    @Provides
    @JvmStatic
    @PerFragment
    fun providePrizesRepository(prizesApi: PrizesApi, @Rx.Io ioScheduler: Scheduler) =
            PrizesRepository(prizesApi, ioScheduler)

    @Provides
    @JvmStatic
    @PerFragment
    fun providePrizesInteractor(prizesRepository: PrizesRepository) =
            PrizesInteractor(prizesRepository)

    @Provides
    @JvmStatic
    @PerFragment
    fun providePresenter(
            router: Router,
            prizesInteractor: PrizesInteractor,
            @Rx.MainThread mainThreadScheduler: Scheduler
    ) = PrizesPresenter(router, prizesInteractor, mainThreadScheduler)
}