package com.kdk96.projectar.di

//import android.content.Context
//import com.kdk96.auth.di.auth.AuthFlowDependencies
//import com.kdk96.auth.ui.AuthFlowReachableScreens
//import com.kdk96.common.di.Rx
//import com.kdk96.main.di.MainFlowDependencies
//import com.kdk96.main.ui.MainFlowReachableScreens
//import com.kdk96.projectar.App
//import com.kdk96.projectar.di.account.AccountModule
//import com.kdk96.projectar.di.auth.AuthApiModule
//import com.kdk96.projectar.di.auth.AuthModule
//import com.kdk96.projectar.di.database.DatabaseModule
//import com.kdk96.projectar.di.glide.GlideModule
//import com.kdk96.projectar.di.network.ServerApiModule
//import com.kdk96.projectar.presentation.Launcher
//import com.kdk96.projectar.ui.AppActivity
//import com.kdk96.projectar.ui.RootScreens
//import com.kdk96.quest.di.QuestFlowDependencies
//import com.kdk96.quest.ui.QuestFlowFragment
//import com.kdk96.questinfo.di.QuestInfoDependencies
//import com.kdk96.questinfo.presentation.QuestInfoReachableScreens
//import com.kdk96.questinfo.ui.QuestInfoFragment
//import com.kdk96.settings.domain.AccountInteractor
//import com.kdk96.tanto.ComponentDependencies
//import com.kdk96.tanto.Injector
//import com.kdk96.tanto.InjectorBuilder
//import com.kdk96.tanto.injectorBuilder
//import dagger.*
//import dagger.multibindings.ClassKey
//import dagger.multibindings.IntoMap
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import ru.terrakok.cicerone.Cicerone
//import ru.terrakok.cicerone.Router
//import ru.terrakok.cicerone.android.support.SupportAppScreen
//import javax.inject.Singleton
//
//
//@Module
//object AppComponentBuilderModule {
//    @Provides
//    @IntoMap
//    @ClassKey(App::class)
//    fun provideBuilder() = injectorBuilder<App> {
//        DaggerAppComponent.builder()
//            .context(this)
//            .build()
//    }
//}
//
//
//@Singleton
//@Component(
//    modules = [
//        AppModule::class,
//        SchedulersModule::class,
//        NavigationModule::class,
//        ComponentDependenciesModule::class,
//        AuthApiModule::class,
//        AuthModule::class,
//        GlideModule::class,
//        AccountModule::class,
//        DatabaseModule::class,
//        ServerApiModule::class
//    ]
//)
//interface AppComponent : Injector<App>, AuthFlowDependencies, MainFlowDependencies,
//    QuestInfoDependencies, QuestFlowDependencies, AppActivityDeps {
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun context(context: Context): Builder
//
//        fun build(): AppComponent
//    }
//}
//
//@Module
//object AppModule {
//    @Provides
//    @JvmStatic
//    @Singleton
//    fun provideLauncher(
//        router: Router,
//        accountInteractor: AccountInteractor
//    ) = Launcher(router, accountInteractor)
//}
//
//@Module
//object SchedulersModule {
//    @Provides
//    @JvmStatic
//    @Singleton
//    @Rx.MainThread
//    fun provideMainThreadScheduler() = AndroidSchedulers.mainThread()
//
//    @Provides
//    @JvmStatic
//    @Singleton
//    @Rx.Io
//    fun provideIoScheduler() = Schedulers.io()
//}
//
//@Module
//object NavigationModule {
//    @Provides
//    @JvmStatic
//    @Singleton
//    fun provideCicerone() = Cicerone.create()
//
//    @Provides
//    @JvmStatic
//    @Singleton
//    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router
//
//    @Provides
//    @JvmStatic
//    @Singleton
//    fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.navigatorHolder
//
//    @Provides
//    @JvmStatic
//    @Singleton
//    fun provideAuthFlowReachableScreens() = object : AuthFlowReachableScreens {
//        override fun mainFlow() = RootScreens.MainFlow
//    }
//
//    @Provides
//    @JvmStatic
//    @Singleton
//    fun provideMainFlowReachableScreens() = object : MainFlowReachableScreens {
//        override fun questInfo(questId: String) = object : SupportAppScreen() {
//            override fun getFragment() = QuestInfoFragment.newInstance(questId)
//        }
//
//        override fun authFlow() = RootScreens.AuthFlow
//    }
//
//    @Provides
//    @JvmStatic
//    @Singleton
//    fun provideQuestInfoReachableScreens() = object : QuestInfoReachableScreens {
//        override fun questFlow(questId: String) = object : SupportAppScreen() {
//            override fun getFragment() = QuestFlowFragment.newInstance(questId)
//        }
//    }
//}
//
//@Module
//interface ComponentDependenciesModule {
//
//    @Binds
//    fun provideComponentDeps(appComponent: AppComponent):ComponentDependencies
//
//}