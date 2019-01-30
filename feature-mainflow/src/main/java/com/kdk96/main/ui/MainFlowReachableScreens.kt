package com.kdk96.main.ui

import com.kdk96.prizes.ui.PrizesFragment
import com.kdk96.quests.presenatation.QuestsReachableScreens
import com.kdk96.quests.ui.QuestsFragment
import com.kdk96.settings.presentation.SettingsReachableScreens
import com.kdk96.settings.ui.SettingsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

interface MainFlowReachableScreens : QuestsReachableScreens, SettingsReachableScreens {
    object Quests : SupportAppScreen() {
        override fun getFragment() = QuestsFragment()
    }

    object Prizes : SupportAppScreen() {
        override fun getFragment() = PrizesFragment()
    }

    object Settings : SupportAppScreen() {
        override fun getFragment() = SettingsFragment()
    }
}