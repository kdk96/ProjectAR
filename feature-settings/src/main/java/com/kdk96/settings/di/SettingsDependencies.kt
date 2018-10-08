package com.kdk96.settings.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.settings.data.storage.AvatarFileProcessor
import com.kdk96.settings.domain.AccountInteractor
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

interface SettingsDependencies : ComponentDependencies {
    fun router(): Router
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
    fun avatarFileProcessor(): AvatarFileProcessor
    fun accountInteractor(): AccountInteractor
}