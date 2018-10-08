package com.kdk96.common.di

interface Component

interface HasComponent

typealias ChildComponents = MutableMap<Class<out Component>, Component>

interface ComponentManager {
    val components: ChildComponents
}

inline fun <reified T : Component> HasComponent.getComponent(
        componentManager: ComponentManager,
        componentBuilder: () -> Component
) = componentManager.components.getOrPut(T::class.java, componentBuilder) as T