package com.kdk96.prizes.di

import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.prizes.data.network.PrizesApi
import com.kdk96.prizes.data.repository.PrizesRepository
import com.kdk96.prizes.domain.PrizesInteractor
import com.kdk96.prizes.presentation.PrizesPresenter
import com.kdk96.prizes.ui.PrizesFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit

@PerFragment
@Component(modules = [PrizesModule::class], dependencies = [PrizesDependencies::class])
interface PrizesComponent : DaggerComponent {
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
    fun providePrizesPresenter(
            router: FlowRouter,
            prizesInteractor: PrizesInteractor,
            @Rx.MainThread mainThreadScheduler: Scheduler
    ) = PrizesPresenter(router, prizesInteractor, mainThreadScheduler)
}