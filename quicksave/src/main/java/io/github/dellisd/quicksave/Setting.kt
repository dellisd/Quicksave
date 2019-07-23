package io.github.dellisd.quicksave

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class Setting<T : Any> internal constructor(
    private val provider: SettingsProvider,
    val key: String,
    val type: KClass<T>,
    val default: T?,
    val title: String,
    val caption: String,
    val dependsOn: Setting<Boolean>?
) {
    private val listeners = mutableSetOf<(T) -> Unit>()

    var value: T
        get() = provider.get(this)
        set(value) {
            listeners.forEach { it(value) }
            provider.set(this, value)
        }

    fun addListener(listener: (T) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (T) -> Unit) {
        listeners.remove(listener)
    }
}
