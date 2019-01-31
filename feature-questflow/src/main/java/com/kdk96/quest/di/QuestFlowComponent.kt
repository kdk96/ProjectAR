package com.kdk96.quest.di

import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.FlowNavigationModule
import com.kdk96.common.di.PerFlow
import com.kdk96.quest.ui.QuestFlowFragment
import dagger.BindsInstance
import dagger.Component

@PerFlow
@Component(
        modules = [FlowNavigationModule::class],
        dependencies = [QuestFlowDependencies::class]
)
interface QuestFlowComponent : DaggerComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun questId(questId: String): Builder
        fun questFlowDependencies(questFlowDependencies: QuestFlowDependencies): Builder
        fun build(): QuestFlowComponent
    }

    fun inject(questFlowFragment: QuestFlowFragment)
}