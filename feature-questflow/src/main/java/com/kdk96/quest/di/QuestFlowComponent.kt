package com.kdk96.quest.di

import com.kdk96.common.di.*
import com.kdk96.quest.ui.QuestFlowFragment
import com.kdk96.questinfo.di.QuestInfoDependencies
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@PerFlow
@Component(
        modules = [FlowNavigationModule::class, ChildDependenciesModule::class],
        dependencies = [QuestFlowDependencies::class]
)
interface QuestFlowComponent : DaggerComponent, QuestInfoDependencies {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun id(id: String): Builder
        fun questFlowDependencies(questFlowDependencies: QuestFlowDependencies): Builder
        fun build(): QuestFlowComponent
    }

    fun inject(questFlowFragment: QuestFlowFragment)
}

@Module
interface ChildDependenciesModule {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(QuestInfoDependencies::class)
    fun provideQuestInfoDependencies(questFlowComponent: QuestFlowComponent): ComponentDependencies
}