package com.kdk96.quests.presenatation

import ru.terrakok.cicerone.android.support.SupportAppScreen

interface QuestsReachableScreens {
    fun questFlow(id: String): SupportAppScreen
}