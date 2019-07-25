package io.github.dellisd.quicksave

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Builds a [Setting] with the specified
 */
inline fun SettingsProvider.booleanSetting(builder: SettingsBuilder<Boolean>.() -> Unit): SettingsDelegate<Boolean, BooleanSetting> {
    val settingsBuilder = SettingsBuilder<Boolean>()
    builder(settingsBuilder)

    return SettingsDelegate(BooleanSetting::class, settingsBuilder)
}

inline fun SettingsProvider.intSetting(builder: SettingsBuilder<Int>.() -> Unit): SettingsDelegate<Int, IntSetting> {
    val settingsBuilder = SettingsBuilder<Int>()
    builder(settingsBuilder)

    return SettingsDelegate(IntSetting::class, settingsBuilder)
}

inline fun SettingsProvider.longSetting(builder: SettingsBuilder<Long>.() -> Unit): SettingsDelegate<Long, LongSetting> {
    val settingsBuilder = SettingsBuilder<Long>()
    builder(settingsBuilder)

    return SettingsDelegate(LongSetting::class, settingsBuilder)
}

inline fun SettingsProvider.floatSetting(builder: SettingsBuilder<Float>.() -> Unit): SettingsDelegate<Float, FloatSetting> {
    val settingsBuilder = SettingsBuilder<Float>()
    builder(settingsBuilder)

    return SettingsDelegate(FloatSetting::class, settingsBuilder)
}

inline fun SettingsProvider.stringSetting(builder: SettingsBuilder<String>.() -> Unit): SettingsDelegate<String, StringSetting> {
    val settingsBuilder = SettingsBuilder<String>()
    builder(settingsBuilder)

    return SettingsDelegate(StringSetting::class, settingsBuilder)
}

inline fun SettingsProvider.stringSetSetting(builder: SettingsBuilder<Set<String>>.() -> Unit): SettingsDelegate<Set<String>, StringSetSetting> {
    val settingsBuilder = SettingsBuilder<Set<String>>()
    builder(settingsBuilder)

    return SettingsDelegate(StringSetSetting::class, settingsBuilder)
}

/*inline fun <reified T : Any, reified R : Any> SettingsProvider.adaptedSetting(builder: SettingsBuilder<T, R>.() -> Unit) : SettingsDelegate<T, R> {
    val settingsBuilder = SettingsBuilder<T, R>()
    builder(settingsBuilder)

    return SettingsDelegate(T::class, settingsBuilder)
}*/


class SettingsDelegate<T, S : Setting<out T>>(
    private val settingClass: KClass<S>,
    private val builder: SettingsBuilder<T>
) {
    private var cache: S? = null

    operator fun getValue(thisRef: SettingsProvider, property: KProperty<*>): S {
        if (cache == null) {
            val config = Setting.Configuration(
                builder.key ?: property.name,
                builder.default,
                builder.title,
                builder.caption,
                builder.dependsOn,
                null

            )
            cache = settingClass.java.getConstructor(SettingsProvider::class.java, Setting.Configuration::class.java)
                .newInstance(thisRef, config)
        }

        return cache!!
    }
}

data class SettingsBuilder<T>(
    var key: String? = null,
    var default: T? = null,
    var title: String = "",
    var caption: String = "",
    internal var dependsOn: BooleanSetting? = null
) {
    fun dependsOn(setting: BooleanSetting) {
        dependsOn = setting
    }
}