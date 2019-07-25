package io.github.dellisd.quicksave

/**
 * Encapsulates an application setting. The value of the setting can be accessed, updated, and observed from
 * instances of this class. The value can be accessed and updated through the [value] property which will read or write
 * the value to the backing storage method through the [provider].
 *
 * Instances of this class can be obtained through [SettingsProvider.setting] or [SettingsProvider.adaptedSetting].
 *
 * @see SettingsProvider
 *
 * @param T The type of the value of the setting
 * @param R The type to be used in storing the setting value. Some storage methods may not support storing certain types
 * (e.g. [android.content.SharedPreferences] can't store dates), so this allows for types to be adapted to a form that can be stored. T -> R
 *
 * @property provider The provider from which values of this setting will be read from and written to.
 * @see SettingsProvider
 *
 * @property config The details of this setting has been configured.
 * @see Configuration
 *
 * @property value Used to access and update the value of the setting
 */
sealed class Setting<T> constructor(
    private val provider: SettingsProvider,
    val config: Configuration<T>
) {
    private val listeners = mutableSetOf<(T) -> Unit>()

    open var value: T
        get() = provider.get(this)
        set(value) {
            notifyListeners(value)
            provider.set(this, value)
        }

    /**
     * Add a listener that will be called back whenever this setting has been updated.
     *
     * @param listener The callback where the new value will be passed
     */
    fun addListener(listener: (T) -> Unit) {
        listeners.add(listener)
    }

    /**
     * Removes a listener. The listener will no longer be called back when this setting has been updated.
     *
     * @param listener The listener to remove
     */
    fun removeListener(listener: (T) -> Unit) {
        listeners.remove(listener)
    }

    protected fun notifyListeners(value: T) {
        listeners.forEach { it(value) }
    }

    /**
     * Data class that encapsulates how a setting has been configured.
     *
     * @property key The string key that is used to identify this setting internally in storage.
     * @property type Used by a [SettingsProvider] to determine how to internally store or retrieve the setting's value
     * @property adapter An adapter that can convert the setting's value to another type that can be stored, and vice versa.
     * @property default The default value for this setting.
     * @property title The title to display for this setting when displayed in a UI
     * @property caption The caption to display for this setting when displayed in a UI
     * @property dependsOn Another setting with a [Boolean] type that this setting depends on. In a UI, this setting will only be enabled if the other setting has a value of `true`.
     */
    data class Configuration<T> internal constructor(
        val key: String,
        val default: T?,
        val title: String,
        val caption: String,
        val dependsOn: Setting<Boolean>?,
        val adapter: String?
    )
}

class BooleanSetting(provider: SettingsProvider, config: Configuration<Boolean>) : Setting<Boolean>(provider, config)
class IntSetting(provider: SettingsProvider, config: Configuration<Int>) : Setting<Int>(provider, config)
class LongSetting(provider: SettingsProvider, config: Configuration<Long>) : Setting<Long>(provider, config)
class FloatSetting(provider: SettingsProvider, config: Configuration<Float>) : Setting<Float>(provider, config)
class StringSetting(provider: SettingsProvider, config: Configuration<String>) : Setting<String>(provider, config)
class StringSetSetting(provider: SettingsProvider, config: Configuration<Set<String>>) :
    Setting<Set<String>>(provider, config)