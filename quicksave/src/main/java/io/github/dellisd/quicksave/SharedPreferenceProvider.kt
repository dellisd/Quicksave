package io.github.dellisd.quicksave

import android.content.SharedPreferences

class SharedPreferenceProvider(private val sharedPreferences: SharedPreferences) : SettingsProvider {
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(setting: Setting<T>): T {
        return when (setting.type) {
            String::class -> sharedPreferences.getString(setting.key, setting.default as? String)
            Int::class -> sharedPreferences.getInt(setting.key, setting.default as? Int ?: 0)
            Long::class -> sharedPreferences.getLong(setting.key, setting.default as? Long ?: 0)
            Boolean::class -> sharedPreferences.getBoolean(setting.key, setting.default as? Boolean ?: false)
            Float::class -> sharedPreferences.getFloat(setting.key, setting.default as? Float ?: 0f)
            else -> throw IllegalArgumentException("No way to get ${setting.type.java} from SharedPreferences")
        } as T
    }

    override fun <T : Any> set(setting: Setting<T>, value: T) {
        when (setting.type) {
            String::class -> sharedPreferences.edit().putString(setting.key, value as String?).apply()
            Int::class -> sharedPreferences.edit().putInt(setting.key, value as Int).apply()
            Long::class -> sharedPreferences.edit().putLong(setting.key, value as Long).apply()
            Boolean::class -> sharedPreferences.edit().putBoolean(setting.key, value as Boolean).apply()
            Float::class -> sharedPreferences.edit().putFloat(setting.key, value as Float).apply()
            else -> throw IllegalArgumentException("No way to put ${setting.type.java} into SharedPreferences")
        }
    }
}