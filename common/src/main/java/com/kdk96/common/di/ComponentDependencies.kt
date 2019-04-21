package com.kdk96.common.di

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

interface ComponentDependencies

typealias ComponentDependenciesProvider = Map<Class<out ComponentDependencies>, @JvmSuppressWildcards ComponentDependencies>

interface HasChildDependencies {
    val dependencies: ComponentDependenciesProvider
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ComponentDependenciesKey(val value: KClass<out ComponentDependencies>)

inline fun <reified T : ComponentDependencies> Fragment.findComponentDependencies(): T {
    var depsProviderFragment = parentFragment
    while (depsProviderFragment !is HasChildDependencies?) {
        depsProviderFragment = depsProviderFragment?.parentFragment
    }
    val depsProvider: HasChildDependencies = depsProviderFragment ?: when {
        activity is HasChildDependencies -> activity as HasChildDependencies
        activity?.application is HasChildDependencies -> activity?.application as HasChildDependencies
        else -> throw IllegalStateException("Can't find suitable ${HasChildDependencies::class.java.simpleName} for $this")
    }
    return depsProvider.dependencies[T::class.java] as T
}