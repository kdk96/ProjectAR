package com.kdk96.common.di

interface Component

interface HasComponent

typealias ChildComponents = MutableMap<Class<out HasComponent>, Component>

interface ComponentManager {
    val components: ChildComponents
}

inline fun <reified T : Component> HasComponent.getComponent(
        componentManager: ComponentManager, componentBuilder: () -> Component
) = componentManager.components.getOrPut(this.javaClass, componentBuilder) as T