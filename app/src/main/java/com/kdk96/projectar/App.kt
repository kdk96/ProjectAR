package com.kdk96.projectar

import android.app.Application
import com.kdk96.tanto.ComponentDependencies
import com.kdk96.tanto.DependenciesOwner
import javax.inject.Inject

class App : Application(), DependenciesOwner {

    @Inject
    override lateinit var dependencies: ComponentDependencies

    override fun onCreate() {
        super.onCreate()

//        Tanto.initBuilders(DaggerBuildersComponent.create().builders())
//        inject()
    }
}