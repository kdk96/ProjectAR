package com.kdk96.quests.ui

import com.kdk96.common.di.Component
import com.kdk96.common.ui.BaseFragment
import com.kdk96.quests.R

class QuestsFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_quests

    override fun buildComponent(): Component = object : Component {}
    override fun onBackPressed() {}
}