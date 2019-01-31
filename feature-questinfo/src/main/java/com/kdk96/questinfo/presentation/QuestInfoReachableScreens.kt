package com.kdk96.questinfo.presentation

import ru.terrakok.cicerone.android.support.SupportAppScreen

interface QuestInfoReachableScreens {
    fun questFlow(questId: String): SupportAppScreen
}