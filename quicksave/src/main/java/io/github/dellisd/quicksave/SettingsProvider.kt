package io.github.dellisd.quicksave

interface SettingsProvider {
    fun <T : Any> get(setting: Setting<T>): T
    fun <T : Any> set(setting: Setting<T>, value: T)
}

