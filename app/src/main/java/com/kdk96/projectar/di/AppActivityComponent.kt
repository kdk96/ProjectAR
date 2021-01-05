package com.kdk96.projectar.di

//import com.kdk96.projectar.presentation.Launcher
//import com.kdk96.projectar.ui.AppActivity
//import com.kdk96.tanto.ComponentDependencies
//import com.kdk96.tanto.Injector
//import com.kdk96.tanto.InjectorBuilder
//import com.kdk96.tanto.android.findComponentDependencies
//import dagger.Component
//import dagger.Module
//import dagger.Provides
//import dagger.multibindings.ClassKey
//import dagger.multibindings.IntoMap
//import ru.terrakok.cicerone.NavigatorHolder
//import javax.inject.Singleton
//
//@Module
//object AppActivityComponentBuilderModule {
//    @Provides
//    @IntoMap
//    @ClassKey(AppActivity::class)
//    fun builder(): InjectorBuilder<*> = InjectorBuilder<AppActivity> {
//        DaggerAppActivityComponent.builder()
//            .appActivityDeps(it.findComponentDependencies())
//            .build()
//    }
//}
//
//@Singleton
//@Component(dependencies = [AppActivityDeps::class])
//interface AppActivityComponent : Injector<AppActivity> {
//
//}
//
//interface AppActivityDeps : ComponentDependencies {
//    fun launcher(): Launcher
//    fun navHolder(): NavigatorHolder
//}