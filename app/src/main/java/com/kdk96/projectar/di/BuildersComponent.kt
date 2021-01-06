package com.kdk96.projectar.di

import com.kdk96.projectar.auth.di.auth.AuthFlowComponentBuilderModule
import com.kdk96.tanto.InjectorBuildersProvider
import dagger.Component

@Component(
    modules = [
        AppComponentBuilderModule::class,
        AppActivityComponentBuilderModule::class,
        AuthFlowComponentBuilderModule::class,
//        MainFlowComponentBuilderModule::class,
//        QuestFlowComponentBuilderModule::class,
//        AppActivityComponentBuilderModule::class
    ]
)
interface BuildersComponent {
    fun builders(): InjectorBuildersProvider
}
