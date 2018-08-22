package com.kdk96.projectar.di

import android.content.Context
import com.kdk96.auth.di.AuthDependencies
import com.kdk96.common.di.ChildComponents
import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.ComponentDependenciesKey
import com.kdk96.common.di.Rx
import com.kdk96.projectar.App
import com.kdk96.projectar.di.auth.AuthApiModule
import com.kdk96.projectar.di.auth.AuthModule
import com.kdk96.projectar.di.main.MainDependencies
import dagger.*
import dagger.multibindings.IntoMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    SchedulersModule::class,
    NavigationModule::class,
    ComponentDependenciesModule::class,
    AuthApiModule::class,
    AuthModule::class
])
interface AppComponent : MainDependencies, AuthDependencies {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}

@Module
object AppModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideChildComponents(): ChildComponents = mutableMapOf()
}

@Module
object SchedulersModule {
    @Provides
    @JvmStatic
    @Singleton
    @Rx.MainThread
    fun provideMainThreadScheduler() = AndroidSchedulers.mainThread()!!

    @Provides
    @JvmStatic
    @Singleton
    @Rx.Io
    fun provideIoScheduler() = Schedulers.io()
}

@Module
object NavigationModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideCicerone() = Cicerone.create()!!

    @Provides
    @JvmStatic
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router!!

    @Provides
    @JvmStatic
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.navigatorHolder!!
}

@Module
interface ComponentDependenciesModule {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(MainDependencies::class)
    fun provideMainDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(AuthDependencies::class)
    fun provideSignInDependencies(appComponent: AppComponent): ComponentDependencies
}