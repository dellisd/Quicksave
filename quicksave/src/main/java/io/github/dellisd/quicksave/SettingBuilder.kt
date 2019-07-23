package io.github.dellisd.quicksave

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified T : Any> SettingsProvider.setting(builder: SettingsBuilder<T>.() -> Unit): SettingsLazy<T> {
    val settingsBuilder = SettingsBuilder<T>()
    builder(settingsBuilder)

    return SettingsLazy(T::class, settingsBuilder)
}

class SettingsLazy<T : Any>(private val type: KClass<T>, private val builder: SettingsBuilder<T>) {
    private var cache: Setting<T>? = null

    operator fun getValue(thisRef: SettingsProvider, property: KProperty<*>): Setting<T> {
        if (cache == null) {
            cache = Setting(
                thisRef,
                builder.key ?: property.name,
                type,
                builder.default,
                builder.title,
                builder.caption,
                builder.dependsOn
            )
        }

        return cache!!
    }
}

data class SettingsBuilder<T : Any>(
    var key: String? = null,
    var default: T? = null,
    var title: String = "",
    var caption: String = "",
    internal var dependsOn: Setting<Boolean>? = null
) {
    fun dependsOn(setting: Setting<Boolean>) {
        dependsOn = setting
    }
}