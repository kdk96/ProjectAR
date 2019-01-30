package com.kdk96.settings.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.settings.data.storage.AvatarFileProcessor
import com.kdk96.settings.domain.AccountInteractor
import com.kdk96.settings.presentation.SettingsReachableScreens
import io.reactivex.Scheduler

interface SettingsDependencies : ComponentDependencies {
    fun accountInteractor(): AccountInteractor
    fun flowRouter(): FlowRouter
    fun avatarFileProcessor(): AvatarFileProcessor
    fun settingsReachableScreens(): SettingsReachableScreens
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
}