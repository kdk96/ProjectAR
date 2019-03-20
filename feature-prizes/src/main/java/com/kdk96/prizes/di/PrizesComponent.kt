package com.kdk96.prizes.di

import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.prizes.data.network.PrizesApi
import com.kdk96.prizes.data.repository.PrizesRepositoryImpl
import com.kdk96.prizes.domain.PrizesInteractor
import com.kdk96.prizes.domain.PrizesInteractorImpl
import com.kdk96.prizes.domain.PrizesRepository
import com.kdk96.prizes.presentation.PrizesPresenter
import com.kdk96.prizes.ui.PrizesFragment
import dagger.Binds
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
abstract class PrizesModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @PerFragment
        fun providePrizesApi(retrofit: Retrofit) = retrofit.create(PrizesApi::class.java)

        @Provides
        @JvmStatic
        @PerFragment
        fun providePrizesPresenter(
                router: FlowRouter,
                prizesInteractor: PrizesInteractorImpl,
                @Rx.MainThread mainThreadScheduler: Scheduler
        ) = PrizesPresenter(router, prizesInteractor, mainThreadScheduler)
    }

    @Binds
    @PerFragment
    abstract fun providePrizesRepository(prizesRepositoryImpl: PrizesRepositoryImpl): PrizesRepository

    @Binds
    @PerFragment
    abstract fun providePrizesInteractor(prizesInteractorImpl: PrizesInteractorImpl): PrizesInteractor
}