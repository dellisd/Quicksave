package io.github.dellisd.quicksave

interface SettingsProvider {
    fun <T> get(setting: Setting<T>): T
    fun <T> set(setting: Setting<T>, value: T)
}
