package com.kdk96.questinfo.presentation

import com.arellomobile.mvp.MvpView
import com.kdk96.questinfo.domain.entity.QuestInfo

interface QuestInfoView : MvpView {
    fun showInfo(questInfo: QuestInfo)
    fun showStartPoint(latLngPair: Pair<Double, Double>)
}