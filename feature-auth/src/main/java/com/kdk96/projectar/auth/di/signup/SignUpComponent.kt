package com.kdk96.projectar.auth.di.signup

//import com.kdk96.projectar.auth.di.auth.ChildDependencies
//import com.kdk96.projectar.auth.domain.AuthInteractor
//import com.kdk96.projectar.auth.presentation.signup.SignUpPresenter
//import com.kdk96.projectar.auth.ui.AuthFlowReachableScreens
//import com.kdk96.projectar.auth.ui.SignUpFragment
//import com.kdk96.common.di.Rx
//import com.kdk96.common.presentation.FlowRouter
//import com.kdk96.tanto.Injector
//import com.kdk96.tanto.InjectorBuilder
//import com.kdk96.tanto.android.findComponentDependencies
//import dagger.BindsInstance
//import dagger.Component
//import dagger.Module
//import dagger.Provides
//import dagger.multibindings.ClassKey
//import dagger.multibindings.IntoMap
//import io.reactivex.Scheduler
//import javax.inject.Singleton
//
//@Module
//object SignUpComponentBuilderModule {
//
//    @Provides
//    @IntoMap
//    @ClassKey(SignUpFragment::class)
//    fun provideSignUpComponentBuilder():InjectorBuilder<*> = InjectorBuilder<SignUpFragment> {
//        DaggerSignUpComponent.factory()
//            .create(
//                it.requireArguments().getString(SignUpFragment.ARG_EMAIL)!!,
//                it.findComponentDependencies()
//            )
////        DaggerSignUpComponent.fac()
////            .build()
//    }
//}
//
//@Singleton
//@Component(
//    modules = [SignUpModule::class],
//    dependencies = [ChildDependencies::class]
//)
//interface SignUpComponent : Injector<SignUpFragment> {
//
//    @Component.Factory
//    interface Factory {
//        fun create(
//            @BindsInstance email: String,
//            childDependencies: ChildDependencies
//        ): SignUpComponent
//    }
//
//}
//
//@Module
//object SignUpModule {
//    @Provides
//    @Singleton
//    fun provideSignUpPresenter(
//        email: String,
//        authInteractor: AuthInteractor,
//        flowRouter: FlowRouter,
//        @Rx.MainThread mainThreadScheduler: Scheduler,
//        screens: AuthFlowReachableScreens
//    ) = SignUpPresenter(email, authInteractor, flowRouter, mainThreadScheduler, screens)
//}