package com.kdk96.common.di

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.MapKey
import kotlin.reflect.KClass

interface ComponentDependencies

typealias ComponentDependenciesProvider = Map<Class<out ComponentDependencies>, @JvmSuppressWildcards ComponentDependencies>

interface HasComponentDependencies {
    val dependencies: ComponentDependenciesProvider
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ComponentDependenciesKey(val value: KClass<out ComponentDependencies>)

inline fun <reified T : ComponentDependencies> AppCompatActivity.findComponentDependencies() =
        findComponentDependenciesProvider()[T::class.java] as T

fun AppCompatActivity.findComponentDependenciesProvider(): ComponentDependenciesProvider =
        if (application is HasComponentDependencies) (application as HasComponentDependencies).dependencies
        else throw IllegalStateException("Can't find suitable component dependencies provider for $this")

inline fun <reified T : ComponentDependencies> Fragment.findComponentDependencies() =
        findComponentDependenciesProvider()[T::class.java] as T

fun Fragment.findComponentDependenciesProvider(): ComponentDependenciesProvider =
        findForInjection<HasComponentDependencies>().dependencies

inline fun <reified T> Fragment.findForInjection(): T {
    var current = parentFragment
    while (current !is T?)
        current = current?.parentFragment
    return current ?: when {
        activity is Throwable -> activity as T
        activity?.application is T -> activity?.application as T
        else -> throw IllegalStateException("Can't find suitable ${T::class.java.simpleName} for $this")
    }
}