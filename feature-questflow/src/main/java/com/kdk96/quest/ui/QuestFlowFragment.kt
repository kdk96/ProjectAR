package com.kdk96.quest.ui

import android.os.Bundle
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.FlowFragment
import com.kdk96.quest.di.DaggerQuestFlowComponent
import com.kdk96.quest.di.QuestFlowComponent
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class QuestFlowFragment : FlowFragment() {
    companion object {
        private const val ARG_QUEST_ID = "quest id argument"
        fun newInstance(questId: String) = QuestFlowFragment().apply {
            arguments = Bundle().apply { putString(ARG_QUEST_ID, questId) }
        }
    }

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        componentBuilder = {
            DaggerQuestFlowComponent.builder()
                    .questId(arguments!!.getString(ARG_QUEST_ID)!!)
                    .questFlowDependencies(findComponentDependencies())
                    .build()
        }
        getComponent<QuestFlowComponent>().inject(this)
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {

        }
    }

    override fun onExit() {
        router.exit()
    }
}