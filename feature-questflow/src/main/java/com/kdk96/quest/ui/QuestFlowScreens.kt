package com.kdk96.quest.ui

import com.kdk96.questinfo.ui.QuestInfoFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

interface QuestFlowScreens {
    object Info : SupportAppScreen() {
        override fun getFragment() = QuestInfoFragment()
    }
}