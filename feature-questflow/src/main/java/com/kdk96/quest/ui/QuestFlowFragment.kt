package com.kdk96.quest.ui

import android.os.Bundle
import com.kdk96.common.di.ComponentDependenciesProvider
import com.kdk96.common.di.HasChildDependencies
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.FlowFragment
import com.kdk96.common.ui.setLaunchScreen
import com.kdk96.quest.di.DaggerQuestFlowComponent
import com.kdk96.quest.di.QuestFlowComponent
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class QuestFlowFragment : FlowFragment(), HasChildDependencies {
    companion object {
        private const val ARG_ID = "id argument"
        fun newInstance(id: String) = QuestFlowFragment().apply {
            arguments = Bundle().apply { putString(ARG_ID, id) }
        }
    }

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var router: Router

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        componentBuilder = {
            DaggerQuestFlowComponent.builder()
                    .id(arguments!!.getString(ARG_ID)!!)
                    .questFlowDependencies(findComponentDependencies())
                    .build()
        }
        getComponent<QuestFlowComponent>().inject(this)
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(QuestFlowScreens.Info)
        }
    }

    override fun onExit() {
        router.exit()
    }
}